/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.customers;

import gmsis.models.customers.Customer;
import gmsis.persistence.customers.CustomerRepository;
import gmsis.persistence.hibernate.HibernateRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 *
 * @author filip
 */
public class HibernateCustomerRepository extends HibernateRepository<Customer> implements CustomerRepository {

    public HibernateCustomerRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void delete(Customer object) throws Exception {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        s.createQuery("delete from PartsItem where customer.id = :id").setParameter("id", object.getId()).executeUpdate();
        s.createQuery("delete from SpecialistRepair where customer.id = :id").setParameter("id", object.getId()).executeUpdate();
        s.createQuery("delete from Booking where customer.id = :id").setParameter("id", object.getId()).executeUpdate();
        s.createQuery("delete from Vehicle where owner.id = :id").setParameter("id", object.getId()).executeUpdate();
        s.createQuery("delete from Customer where id = :id").setParameter("id", object.getId()).executeUpdate();
        s.getTransaction().commit();
        s.close();
    }

    @Override
    public List<Customer> findByName(String search) {
        Session s = sessionFactory.openSession();
        List<Customer> res = s.createQuery("from Customer where lower(fullName) like lower(:name)", Customer.class).setParameter("name", '%' + search + '%').list();
        s.close();
        return res;
    }

    @Override
    public List<Customer> findByRegistration(String search) {
        Session s = sessionFactory.openSession();
        List<Customer> res = s.createQuery("select cst from Customer cst join cst.vehicles vh where lower(vh.registrationNumber) like lower(:reg)", Customer.class)
                .setParameter("reg", '%' + search + '%')
                .list();
        s.close();
        return res;
    }
}
