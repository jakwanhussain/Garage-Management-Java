/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.bookings;

import gmsis.models.bookings.Mechanic;
import gmsis.persistence.bookings.MechanicsRepository;
import gmsis.persistence.hibernate.HibernateRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 *
 * @author jakwan
 */
public class HibernateMechanicsRepository extends HibernateRepository<Mechanic> implements MechanicsRepository {
     public HibernateMechanicsRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
     
    @Override
    public List<Mechanic> findById(Long id) {
        Session s = sessionFactory.openSession();
        List<Mechanic> res = s.createQuery("from Mechanic where id = :mechanicId", Mechanic.class).setParameter("mechanicId",id).list();
        s.close();
        return res;
       
    } 
     
}
