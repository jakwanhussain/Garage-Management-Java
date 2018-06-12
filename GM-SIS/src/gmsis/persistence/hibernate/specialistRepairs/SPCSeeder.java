/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.specialistRepairs;

import gmsis.di.DependencyManager;
import gmsis.models.specialistRepairs.SpecialistRepairCentre;
import gmsis.persistence.specialistRepair.SpecialistRepairCentreRepository;

import java.util.logging.Logger;


/**
 *
 * @author Stephen
 */
public class SPCSeeder {
       private static final Logger LOG = Logger.getLogger(SPCSeeder.class.getName());
       
       private static SpecialistRepairCentre spcI(String name, String address, String phoneNumber, String email) {
        return new SpecialistRepairCentre(name, address, phoneNumber, email);
       }
       
    public static boolean seedSPCIfNecessary(DependencyManager dm) throws Exception {
        if (needsSeeding(dm)) {
            LOG.info("Seeding Specialist Repair Centres...");
            SpecialistRepairCentreRepository spcRepo = dm.getSpecialistRepairCentreRepository();
            spcRepo.save(spcI(
                    "Ford",
                    "Dunton Ford Link, Dunton Technical Centre, Basildon SS15 6GB",
                    "01268 522744",
                    "fordDunton@ford.com"
                    ));
            spcRepo.save(spcI(
                    "Vaxhaull",
                    "4 Service House, W Mayne, Basildon SS15 6RW",
                    "01268 205472",
                    "toomyVaxhaull@vaxhaull.com"
                    ));
            spcRepo.save(spcI(
                    "Renault",
                    "Service House, West Mayne, W Mayne Basildon SS15 6RW",
                    "01268 209273",
                    "toomyRenault@renault.com"
                    ));
            spcRepo.save(spcI(
                    "Mercedes-Benz",
                    "Gallions Park, 101 Woolwich Manor Way, London E6 6EY",
                    "020 3792 0853",
                    "mercades@mercades.com"
                    ));
            spcRepo.save(spcI(
                    "Peugeot",
                    "Yardley Buisness Park, Miles Gray Rd, Basildon SS14 3GN",
                    "0845 313 1488",
                    "peugeot@peugeot.com"
                    ));
            spcRepo.save(spcI(
                    "Citroen",
                    "Parkwood Industrial Estate, Target Buisness Centre, Bircholt Rd, Maidstone ME15 9YY",
                    "01622 759599",
                    "citroen@ferrari.com"
                    ));
            spcRepo.save(spcI(
                    "Audi",
                    "Claydons Lane, Rayleigh SS6 7UQ",
                    "01268 740988",
                    "southendAudi@audi.com"
                    ));
            spcRepo.save(spcI(
                    "Volkswagen",
                    "Prince Ave, Southend-On-Sea SS0 0JA",
                    "01702 801995",
                    "vw@volkswagen.com"
                    ));
            spcRepo.save(spcI(
                    "Toyota",
                    "Arterial Rd, Rayleigh SS6 7UQ",
                    "01268 748990",
                    "stevenEagellToyota@toyota.com"
                    ));
            spcRepo.save(spcI(
                    "Nissan",
                    "1021 Romford Rd, London E12 5LH",
                    "020 8988 5000",
                    "nissanRomford@nissan.com"
                    ));
            LOG.info("Seeded Specialist Repair Centres");
            return true;
        }
        return false;
    }       
    
    
    
    
    private static boolean needsSeeding(DependencyManager dm) throws Exception {
        return dm.getSpecialistRepairCentreRepository().all(SpecialistRepairCentre.class).size() == 0;
    }
}

