/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.parts;

import gmsis.models.parts.PartsItem;
import gmsis.models.vehicles.Vehicle;
import gmsis.persistence.hibernate.HibernateRepository;
import gmsis.persistence.parts.PartsItemRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 *
 * @author Abdullah
 */
public class HibernatePartItemsRepository extends HibernateRepository<PartsItem> implements PartsItemRepository {

    public HibernatePartItemsRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public PartsItem getPartItem(String search){
        Session s = sessionFactory.openSession();
        PartsItem pi;
        pi = s.createQuery("from PartsItem where lower(serialNumber) like lower(:serialNumber)", PartsItem.class).setParameter("serialNumber", '%' + search + '%').list().get(0);
        s.close();
        return pi;
    }

     public List<PartsItem> getAvailablePartsItem(String search){//AVAILABLE
        Session s = sessionFactory.openSession();
        List<PartsItem> pi;//ADD AVAILABLE = TRUE
        pi = s.createQuery("from PartsItem where lower(serialNumber) like lower(:serialNumber) and available = true", PartsItem.class).setParameter("serialNumber",'%' + search + '%').list();
        s.close();
        return pi;
    }

    public List<PartsItem> searchRegistrationui(String search) {
        Session s = sessionFactory.openSession();
        List<PartsItem> pi;
        pi = s.createQuery("select pItems from PartsItem pItems join pItems.vehicle vh where lower(vh.registrationNumber) like lower(:registrationNumber)", PartsItem.class).setParameter("registrationNumber", '%' + search + '%').list();
        s.close();
        return pi;
    }
    
    
    public List<PartsItem> getAvailable(){
        Session s = sessionFactory.openSession();
        List<PartsItem> pi;
        pi = s.createQuery("from PartsItem where available = true", PartsItem.class).list();
        s.close();
        return pi;       
    }
    
    public List<PartsItem> currentDR(){
        Session s = sessionFactory.openSession();
        List<PartsItem> pi;
        pi = s.createQuery("from PartsItem where bookings is not null", PartsItem.class).list();
        s.close();
        return pi; 
    }
    
    public List<PartsItem> inRepairs(){//CHECK IF IT WORKS .... update IT WORKS! lol 
        Session s = sessionFactory.openSession();
        List<PartsItem> pi;
        pi = s.createQuery("select pItems from PartsItem pItems join pItems.repair rep where rep.partItem is not null", PartsItem.class).list();
        s.close();
        return pi;
    }
    
    public List<PartsItem> searchCustomerui(String search){
        Session s = sessionFactory.openSession();
        List<PartsItem> pi;
        pi = s.createQuery("select pItems from PartsItem pItems join pItems.customer cus where lower(cus.fullName) like lower(:fullName)", PartsItem.class).setParameter("fullName", '%' + search + '%').list();
        s.close();
        return pi;
    } 
    
    public List<Vehicle> getByReg(String search){
        Session s = sessionFactory.openSession();
        List<Vehicle> veh;//ADD AVAILABLE = TRUE
        veh = s.createQuery("from Vehicle where lower(registrationNumber) like lower(:registrationNumber)", Vehicle.class).setParameter("registrationNumber",'%' + search + '%').list();
        s.close();
        return veh;
    }
    
    public List<PartsItem> getByName(String search){
        Session s = sessionFactory.openSession();
        List<PartsItem> pi;
        pi = s.createQuery("select pItems from PartsItem pItems join pItems.model mod where lower(mod.name) like lower(:name) and pItems.available = true", PartsItem.class).setParameter("name", '%' + search + '%').list();
        s.close();
        return pi;
    }
}