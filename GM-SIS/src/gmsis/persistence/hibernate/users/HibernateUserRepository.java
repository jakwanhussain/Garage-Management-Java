/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.persistence.hibernate.users;

import gmsis.models.authentication.User;
import gmsis.persistence.hibernate.HibernateRepository;
import gmsis.persistence.users.UserRepository;
import org.hibernate.SessionFactory;

/**
 *
 * @author filip
 */
public class HibernateUserRepository extends HibernateRepository<User> implements UserRepository {
    public HibernateUserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
