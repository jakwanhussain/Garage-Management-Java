/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate;

import gmsis.di.DependencyManager;
import gmsis.models.authentication.User;

/**
 *
 * @author filip
 */
public class UserSeeder {
    public static boolean seedUsersIfNecessary(DependencyManager dm) throws Exception {
        if (needsSeeding(dm)) {
            User admin = new User();
            admin.setFirstName("Adam");
            admin.setLastName("Admin");
            admin.setPassword("admin");
            admin.setIsAdmin(true);
            dm.getUserRepository().save(admin);
            System.out.println("*** WARNING ***");
            System.out.println("Created a new database with default admin user: " + admin);
            
            User standardUser = new User();
            standardUser.setFirstName("ursula");
            standardUser.setLastName("User");
            standardUser.setPassword("user");
            standardUser.setIsAdmin(false);
            dm.getUserRepository().save(standardUser);
            System.out.println("added a user: " + standardUser);
            System.out.println("user id: "+standardUser.getId());
            return true;
        }
        return false;
    }

    private static boolean needsSeeding(DependencyManager dm) throws Exception {
        return dm.getUserRepository().all(User.class).size() == 0;
    }
}
