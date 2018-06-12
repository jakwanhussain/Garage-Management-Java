package gmsis.persistence.hibernate.vehicles;

import gmsis.di.DependencyManager;
import gmsis.models.vehicles.WarrantyCompany;
import gmsis.persistence.vehicles.WarrantyCompanyRepository;

import java.util.logging.Logger;

/**
 * Created by sabrinasaytac on 22/03/2017.
 */
public class WarrantyCompanySeeder {

    private static final Logger LOG = Logger.getLogger(VehicleSeeder.class.getName());

    private static final String[] companyName = {"Aviva", "AA", "1st Central", "Direct Line"};
    private static final String[] companyAddress = {"E1 4SR", "E2 6PE", "W1 9BF", "M27 9LS"};

    private static WarrantyCompany warrantyCompany(String companyName, String companyAddress) {

        return new WarrantyCompany(companyName, companyAddress);

    }

    public static boolean seedWarrantyCompanyIfNecessary(DependencyManager dm) throws Exception {

        if (needsSeeding(dm)) {
            LOG.info("Seeding warranty companies...");
            WarrantyCompanyRepository companyRepo = dm.getWarrantyCompanyRepository();

            for (int i = 0; i < companyName.length; i++) {

                companyRepo.save(warrantyCompany(companyName[i], companyAddress[i]));

            }

            LOG.info("Seeded warranty companies");
            return true;

        }
        return false;

    }

    private static boolean needsSeeding(DependencyManager dm) throws Exception {
        return dm.getWarrantyCompanyRepository().all(WarrantyCompany.class).size() == 0;
    }


}
