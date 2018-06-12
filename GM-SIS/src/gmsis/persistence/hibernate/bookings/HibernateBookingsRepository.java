/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.bookings;

import gmsis.models.bookings.Booking;
import gmsis.persistence.bookings.BookingsRepository;
import gmsis.persistence.hibernate.HibernateRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 *
 * @author jakwan
 */
public class HibernateBookingsRepository extends HibernateRepository<Booking> implements BookingsRepository {

    public HibernateBookingsRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    @Override
    public Booking findById(Long bId){
        Session s = sessionFactory.openSession();
        Booking res = s.createQuery("from Booking where id = :bId", Booking.class).setParameter("bId",bId).list().get(0);
        s.close();
        return res;
    }
    
    @Override
    public List<Booking> findByRegistration(String reg){
        Session s = sessionFactory.openSession();
        List<Booking> res = s.createQuery("select id from Booking id join id.vehicle nm where lower(nm.registrationNumber) like lower(:reg)", Booking.class).setParameter("reg", '%' + reg + '%').list();  
        s.close();
        return res;
    }
    
    @Override
    public List<Booking> findByName(String name){
        Session s = sessionFactory.openSession();
        List<Booking> res = s.createQuery("select id from Booking id join id.customer nm where lower(nm.fullName) like lower(:full)", Booking.class).setParameter("full", '%' + name + '%').list();  
        s.close();
        return res;
    }
    
    @Override
    public List<Booking> findByManufacturer(String manu) {
        Session s = sessionFactory.openSession();
        List<Booking> res = s.createQuery("select id from Booking id join id.vehicle nm where lower(nm.make) like lower(:ma)", Booking.class).setParameter("ma", '%' + manu + '%').list();
        s.close();
        return res;
    }
    
    public List<Booking> getNotCompleted(){
        Session s = sessionFactory.openSession();
        List<Booking> res = s.createQuery("from Booking where done = 'No'", Booking.class).list();  
        s.close();
        return res;
    }
    
}
