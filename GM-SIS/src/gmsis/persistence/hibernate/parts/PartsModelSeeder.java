/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.parts;

import gmsis.di.DependencyManager;
import gmsis.models.parts.PartsItem;
import gmsis.models.parts.PartsModel;
import gmsis.persistence.parts.PartsRepository;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Abdullah
 */
public final class PartsModelSeeder {

    private static final Logger LOG = Logger.getLogger(PartsModelSeeder.class.getName());
    
    private static PartsModel pmI(int id, String des, String name,  double cost, List<PartsItem> item) {
        return new PartsModel(id, des, name,  cost, item);
    }

    public static boolean seedPartsModelIfNecessary(DependencyManager dm) throws Exception {
        if (needsSeeding(dm)) {
            LOG.info("Seeding parts models...");
            PartsRepository partsRepo = dm.getPartsRepository();
            partsRepo.save(pmI(
                    1,
                    "Provides power to car",
                    "Battery",
                    50.95,
                    null
                    ));
            partsRepo.save(pmI(
                    2,
                    "Lets the fumes out",
                    "Exhaust",
                    14.59,
                    null
                    ));
            partsRepo.save(pmI(
                    3,
                    "Stops the car",
                    "Brakes",
                    30.59,
                    null
                    ));
            partsRepo.save(pmI(
                    4,
                    "Provides light",
                    "Bulbs",
                    5.99,
                    null
                    ));
            partsRepo.save(pmI(
                    5,
                    "Wipes the windscreen",
                    "Wiper Blades",
                    9.95,
                    null
                    ));
            partsRepo.save(pmI(
                    6,
                    "Electric current to ignition",
                    "Spark Plugs",
                    10.69,
                    null
                    ));
            partsRepo.save(pmI(
                    7,
                    "Lets you change gears",
                    "Clutch",
                    23.79,
                    null
                    ));
            partsRepo.save(pmI(
                    8,
                    "Provides mobility",
                    "Tyre",
                    22.89,
                    null
                    ));
            partsRepo.save(pmI(
                    9,
                    "Lets you turn the car in a direction",
                    "Steering wheel",
                    5.01,
                    null
                    ));
            partsRepo.save(pmI(
                    10,
                    "Reduces impact",
                    "Shock Absorber",
                    12.19,
                    null
                    ));   
            LOG.info("Seeded parts model");
            return true;
        }
        return false;
    }

    private static boolean needsSeeding(DependencyManager dm) throws Exception {
        return dm.getPartsRepository().all(PartsModel.class).size() == 0;
    }
}