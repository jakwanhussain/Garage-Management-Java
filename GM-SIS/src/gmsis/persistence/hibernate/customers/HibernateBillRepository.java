/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.customers;

import gmsis.models.bookings.Booking;
import gmsis.models.customers.Bill;
import gmsis.models.customers.InvoiceStatus;
import gmsis.models.specialistRepairs.SpecialistRepair;
import gmsis.persistence.customers.BillRepository;
import gmsis.persistence.hibernate.HibernateRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 *
 * @author filip
 */
public class HibernateBillRepository extends HibernateRepository<Bill> implements BillRepository {

    public HibernateBillRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    private Bill createBill(Booking booking) {
        Session s = sessionFactory.openSession();
        List<SpecialistRepair> specialistRepairs = s.createQuery("select sr from SpecialistRepair sr where sr.booking.id = :bid", SpecialistRepair.class).setParameter("bid", booking.getId()).list();
        s.close();

        double spcCost = specialistRepairs.stream().mapToDouble(sr -> sr.getCost() + (sr.getPartItem() != null ? sr.getPartItem().getPartsModel().getCost() : 0)).sum();

        Bill bill = new Bill();
        bill.setBooking(booking);
        bill.setCustomer(booking.getCustomer());
        bill.setStatus(InvoiceStatus.OUTSTANDING);
        bill.setAmount(spcCost + booking.getCost());

        return bill;
    }

    @Override
    public Bill getOrCreateBill(Booking booking) throws Exception {
        Session s = sessionFactory.openSession();
        List<Bill> bills = s.createQuery("select bi from Bill bi where bi.booking.id = :bid", Bill.class).setParameter("bid", booking.getId()).list();
        s.close();

        return bills.isEmpty() ? save(createBill(booking)) : bills.get(0);
    }
    
}
