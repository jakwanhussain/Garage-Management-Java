/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.specialistRepairs;

import gmsis.models.specialistRepairs.SpecialistRepairCentre;
import gmsis.persistence.hibernate.HibernateRepository;
import gmsis.persistence.specialistRepair.SpecialistRepairCentreRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Stephen
 */
public class HibernateSpecialistRepairCentreRepository extends HibernateRepository<SpecialistRepairCentre> implements SpecialistRepairCentreRepository  {
     

   public HibernateSpecialistRepairCentreRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
   
   @Override
   public SpecialistRepairCentre findByName(String search)
   {
        Session s = sessionFactory.openSession();
        SpecialistRepairCentre res = s.createQuery("from SpecialistRepairCentre where lower(name) like lower(:spcName)", SpecialistRepairCentre.class).setParameter("spcName", '%' + search + '%').getSingleResult();
        s.close();
        return res;
    }
 }
