/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package gmsis.persistence.hibernate.specialistRepairs;

import gmsis.models.specialistRepairs.SpecialistRepair;
import gmsis.persistence.hibernate.HibernateRepository;
import gmsis.persistence.specialistRepair.SpecialistRepairsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 *
 * @author Stephen
 */
public class HibernateSpecialistRepairsRepository extends HibernateRepository<SpecialistRepair> implements SpecialistRepairsRepository  {

   public HibernateSpecialistRepairsRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public SpecialistRepair getSpecialistRepair(long id) {
        Session s = sessionFactory.openSession();
        SpecialistRepair res = s.createQuery("from SpecialistRepair where id = :spcID", SpecialistRepair.class).setParameter("spcID", id).getSingleResult();
        s.close();
        return res;
    }

    @Override
    public List<SpecialistRepair>  findByName(String name) {
        Session s = sessionFactory.openSession();
        List<SpecialistRepair>  res = s.createQuery("select sRepair from SpecialistRepair sRepair join sRepair.customer cus where lower(cus.fullName) like lower(:searchedName)", SpecialistRepair.class)
                .setParameter("searchedName", '%' + name + '%')
                .list();
        s.close();
        return res;
    }

    @Override
    public List<SpecialistRepair> findByReg(String reg) {
        Session s = sessionFactory.openSession();
        List<SpecialistRepair>  res = s.createQuery("select sRepair from SpecialistRepair sRepair join sRepair.vehicle vh where lower(vh.registrationNumber) like lower(:searchedReg)", SpecialistRepair.class)
                .setParameter("searchedReg", '%' + reg + '%')
                .list();
        s.close();
        return res;
    }
    public List<SpecialistRepair> findBySPC(String spcName){
        Session s = sessionFactory.openSession();
        List<SpecialistRepair>  res = s.createQuery("select sRepair from SpecialistRepair sRepair join sRepair.SPC spc where lower(spc.name) like lower(:searchedName)", SpecialistRepair.class)
                .setParameter("searchedName", '%' + spcName + '%')
                .list();
        s.close();
        return res;
    }
}
    

