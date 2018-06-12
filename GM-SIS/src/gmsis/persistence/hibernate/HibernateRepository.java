/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.persistence.hibernate;

import gmsis.persistence.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * @author filip
 */
public class HibernateRepository<T> implements Repository<T> {
    protected final SessionFactory sessionFactory;

    public HibernateRepository(SessionFactory sessionFactory) {
        super();
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T save(T object) throws Exception {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        s.saveOrUpdate(object);
        s.getTransaction().commit();
        s.close();
        return object;
    }

    @Override
    public List<T> all(Class<T> class_) throws Exception {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        List<T> results = s.createQuery("from " + class_.getSimpleName(), class_).getResultList();
        s.getTransaction().commit();
        s.close();
        return results;
    }

    @Override
    public T get(Class<T> class_, Serializable id) throws Exception {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        T result = s.get(class_, id);
        s.getTransaction().commit();
        s.close();
        return result;
    }

    @Override
    public void delete(T object) throws Exception {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        s.delete(object);
        s.getTransaction().commit();
        s.close();
    }
}
