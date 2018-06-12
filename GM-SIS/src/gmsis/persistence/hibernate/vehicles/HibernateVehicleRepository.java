/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.persistence.hibernate.vehicles;

import gmsis.models.vehicles.Vehicle;
import gmsis.persistence.hibernate.HibernateRepository;
import gmsis.persistence.vehicles.VehicleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author sabrinasaytac
 */
public class HibernateVehicleRepository extends HibernateRepository<Vehicle> implements VehicleRepository {

    public HibernateVehicleRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Vehicle> findByRegistration(String search) {

        Session session = sessionFactory.openSession();
        List<Vehicle> result = session.createQuery("from Vehicle where lower(registration_number) like lower(:reg)", Vehicle.class).setParameter("reg", '%' + search + '%').list();
        session.close();
        return result;

    }

    @Override
    public List<Vehicle> findByManufacturer(String search) {

        Session session = sessionFactory.openSession();
        List<Vehicle> result = session.createQuery("from Vehicle where lower(make) like lower(:make)", Vehicle.class).setParameter("make", '%' + search + '%').list();
        session.close();
        return result;
    }
    
    @Override
    public List<Vehicle> findByCustomer(String search) {

        Session session = sessionFactory.openSession();
        List<Vehicle> result = session.createQuery("select vh from Vehicle vh join vh.owner cus where(cus.fullName) like(:fullName)", Vehicle.class)
                              .setParameter("fullName",search).list();
        session.close();
        return result;
    }
}
