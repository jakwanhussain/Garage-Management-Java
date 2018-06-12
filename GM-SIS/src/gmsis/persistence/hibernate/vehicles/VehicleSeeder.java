package gmsis.persistence.hibernate.vehicles;

import gmsis.di.DependencyManager;
import gmsis.models.customers.Customer;
import gmsis.models.specialistRepairs.SpecialistRepair;
import gmsis.models.vehicles.Vehicle;
import gmsis.models.vehicles.VehicleType;
import gmsis.models.vehicles.WarrantyCompany;
import gmsis.persistence.vehicles.VehicleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sabrinasaytac on 22/03/2017.
 */
public class VehicleSeeder {

    private static final Logger LOG = Logger.getLogger(VehicleSeeder.class.getName());

    private static final String[] vehicleRegNo = {"MA57 ERP", "LOL BUG", "LE34 FGH", "GH04 SJF", "QP12 SGK", "OA23 QWF", "MA85 EFP", "PA63 SKJ", "AS09 APX", "GH06 SJF", "QH19 LFM", "PS20 LOL", "TU10 ASF", "RY93 JFK", "LE73 ETS"};
    private static final String[] vehicleMake = {"Vauxhall", "Volkswagen", "Nissan", "Vauxhall", "Audi", "Ford", "Rover", "Nissan", "Citroen", "Toyota", "Vauxhall", "Renault", "Mercedes-Benz", "Peugeot", "Ford"};
    private static final String[] vehicleModel = {"Corsa", "Beetle", "NT400", "Vivaro", "A4", "Focus", "75", "NT400", "Berlingo", "Hilux", "Astra", "Megane", "Antos", "Partner", "Transit"};
    private static final double[] vehicleEngineSize = {1.2, 1.4, 5.9, 7.4, 3.0, 1.0, 2.0, 6.6, 4.3, 7.6, 1.4, 1.6, 6.6, 5.7, 1.2};
    private static final String[] vehicleFuelType = {"Petrol", "Diesel", "LPG", "Diesel", "Diesel", "Petrol", "Petrol", "Diesel", "LPG", "Diesel", "Diesel", "Petrol", "LPG", "Diesel", "Diesel"};
    private static final String[] vehicleLastService = {("2017-01-01"), ("2016-03-01"), ("2016-06-23"), ("2016-07-31"),
            ("2016-09-27"), ("2016-12-12"), ("2017-01-16"), ("2016-12-13"), ("2015-05-23"), ("2017-02-03"), ("2017-02-28"), ("2017-03-01"), ("2017-02-01"), ("2016-10-19"), ("2016-09-06")};
    private static final String[] vehicleColour = {"Blue", "Silver", "White", "Blue", "Black", "Red", "White", "Silver", "Black", "Black", "Silver", "Silver", "White", "Black", "Multi"};
    private static final String[] motRenewal = {("2018-01-01"), ("2018-03-01"), ("2017-06-23"), ("2017-07-31"), ("2017-09-27"),
            ("2017-12-12"), ("2018-01-16"), ("2017-12-13"), ("2017-05-23"), ("2018-02-03"), ("2018-02-28"), ("2018-03-01"), ("2018-02-01"), ("2017-10-19"), ("2017-09-06")};
    private static final String[] warrantyStartDate = {("2017-01-01"), ("2016-03-01"), null, ("2016-07-31"), ("2010-09-27"),
            ("2008-12-12"), ("2017-01-16"), null, ("2009-05-23"), ("2015-02-03"), null, null, null, ("2016-10-19"), ("2014-09-06")};
    private static final String[] warrantyCompany = {"Aviva", "Aviva", null, "AA", "1st Central", "1st Central", "1st Central", null, "Direct Line", "AA", null, null, null, "Aviva", "Direct Line"};
    private static final Integer[] vehicleMileage = {10001, 31337, 15670, 468, 36124, 59201, 34602, 134, 25795, 89012, 23568, 6702, 12400, 23573, 92013};
    private static final VehicleType[] vehicleType = {VehicleType.CAR, VehicleType.CAR, VehicleType.TRUCK, VehicleType.VAN, VehicleType.CAR, VehicleType.CAR, VehicleType.CAR, VehicleType.TRUCK, VehicleType.VAN, VehicleType.TRUCK, VehicleType.CAR, VehicleType.CAR, VehicleType.TRUCK, VehicleType.VAN, VehicleType.CAR};
    private static final String[] vehicleOwner = {"Mustafa Bozkurt", "Mustafa Bozkurt", "David Edwards", "Lorraine J. Tomlinson", "Steven Winn", "Steven Winn", "Steven Winn", "Maja McKenzie", "Maja McKenzie", "Stefnir Einarsson", "Kattan Plaster Ltd", "Fre-weini Kidane", "Fre-weini Kidane", "Marina Silva Solicitors", "Angelique Ruest"};


    private static Vehicle vehicle(String registrationNumber, String make, String model, double engineSize, String fuelType, LocalDate lastService, String colour, LocalDate motRenewal,
                                   LocalDate warrantyStartDate, LocalDate warrantyEndDate, WarrantyCompany warrantyCompany, Integer mileage, VehicleType vehicleType, Customer owner, List<SpecialistRepair> specialistRepair) {

        return new Vehicle(registrationNumber, make, model, engineSize, fuelType, lastService, colour, motRenewal, warrantyStartDate, warrantyEndDate, warrantyCompany, mileage, vehicleType, owner, specialistRepair);


    }

    public static boolean seedVehicleIfNecessary(DependencyManager dm) throws Exception {

        WarrantyCompanySeeder.seedWarrantyCompanyIfNecessary(dm);

        if (needsSeeding(dm)) {
            LOG.info("Seeding vehicles...");
            VehicleRepository vehicleRepo = dm.getVehicleRepository();
            Random random = new Random();
            int max = 7, min = 1;

            for (int i = 0; i < 15; i++) {

                int randomNumber = random.nextInt(max + 1 - min) + min;

                try {
                    vehicleRepo.save(vehicle(
                            vehicleRegNo[i],
                            vehicleMake[i],
                            vehicleModel[i],
                            vehicleEngineSize[i],
                            vehicleFuelType[i],
                            LocalDate.parse(vehicleLastService[i]),
                            vehicleColour[i],
                            LocalDate.parse(motRenewal[i]),
                            (warrantyStartDate[i] != null) ? LocalDate.parse(warrantyStartDate[i]) : null,
                            (warrantyStartDate[i] == null) ? null : LocalDate.parse(warrantyStartDate[i]).plusYears(randomNumber),
                            warrantyCompany[i] != null ? dm.getWarrantyCompanyRepository().findByName(warrantyCompany[i]).get(0) : null,
                            vehicleMileage[i],
                            vehicleType[i],
                            dm.getCustomerRepository().findByName(vehicleOwner[i]).get(0),
                            null));
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, "Could not save vehicle", ex);
                }

            }

            LOG.info("Seeded vehicles");
            return true;

        }
        return false;

    }

    private static boolean needsSeeding(DependencyManager dm) throws Exception {
        return dm.getVehicleRepository().all(Vehicle.class).size() == 0;
    }


}
