/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.parts;

import gmsis.models.parts.PartsModel;
import gmsis.persistence.hibernate.HibernateRepository;
import gmsis.persistence.parts.PartsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 *
 * @author Abdullah
 */
public class HibernatePartsRepository extends HibernateRepository<PartsModel> implements PartsRepository {

    public HibernatePartsRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public PartsModel getPModel(String search)
    {   Session s = sessionFactory.openSession();
        PartsModel pm;
        pm = s.createQuery("from PartsModel where lower(name) like lower(:name)", PartsModel.class).setParameter("name", '%' + search + '%').list().get(0);
        s.close();
        return pm;
    }
 
    public List<PartsModel> allModels() {
        Session s = sessionFactory.openSession();
        List<PartsModel> pm;
        pm = s.createQuery("from PartsModel", PartsModel.class).list();
        s.close();
        return pm;
    }
    }
