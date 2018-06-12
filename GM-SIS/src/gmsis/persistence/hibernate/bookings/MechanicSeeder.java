/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.bookings;

import gmsis.di.DependencyManager;
import gmsis.models.bookings.Mechanic;
import gmsis.persistence.bookings.MechanicsRepository;

import java.util.logging.Logger;

/**
 * @author jakwan
 */
public class MechanicSeeder {
    private static final Logger LOG = Logger.getLogger(MechanicSeeder.class.getName());

    private static Mechanic m1(double hourlyRate) {
        return new Mechanic(hourlyRate);
    }

    public static boolean seedMechanicsIfNecessary(DependencyManager dm) throws Exception {
        if (needsSeeding(dm)) {
            LOG.info("Seeding mechanic models...");
            MechanicsRepository mechanicRepo = dm.getMechanicsRepository();
            mechanicRepo.save(m1(
                    40.50
            ));
            mechanicRepo.save(m1(
                    50.50
            ));
            mechanicRepo.save(m1(
                    60.50
            ));
            mechanicRepo.save(m1(
                    75.00
            ));
             mechanicRepo.save(m1(
                    80.00
            ));
            LOG.info("Mehcanic seeder completed");
            return true;
        }
        return false;
    }

    private static boolean needsSeeding(DependencyManager dm) throws Exception {
        return dm.getMechanicsRepository().all(Mechanic.class).size() == 0;
    }
}
       
