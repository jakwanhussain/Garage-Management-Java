/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.parts;

import gmsis.di.DependencyManager;
import gmsis.models.bookings.Booking;
import gmsis.models.customers.Customer;
import gmsis.models.parts.PartsItem;
import gmsis.models.parts.PartsModel;
import gmsis.models.specialistRepairs.SpecialistRepair;
import gmsis.models.vehicles.Vehicle;
import gmsis.persistence.parts.PartsItemRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Abdullah
 */
public final class PartsItemSeeder {

    private static final Logger LOG = Logger.getLogger(PartsItemSeeder.class.getName());
    
    private static PartsItem piI(String sn, PartsModel m, boolean a, Customer cus, SpecialistRepair sr,LocalDate add,Vehicle veh, Booking booking, LocalDate io, LocalDate wo) {
        return new PartsItem(sn, m, a, cus, sr,add,veh, booking, io, wo);
    }

    public static boolean seedPartsItemIfNecessary(DependencyManager dm) throws Exception {
        List<Booking> booking=dm.getBookingsRepository().all(Booking.class);
        if (needsSeeding(dm)) {
            LOG.info("Seeding parts items...");
            PartsItemRepository partsItemRepo = dm.getPartsItemRepository();
            partsItemRepo.save(piI(
                    "1E3RT8",
                    dm.getPartsRepository().getPModel("Battery"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2017-01-01"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "9E8IO1",
                    dm.getPartsRepository().getPModel("Battery"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2017-01-04"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "7A3RT2",
                    dm.getPartsRepository().getPModel("Battery"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2017-04-10"),
                    null,
                     null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "4D4DE3",
                    dm.getPartsRepository().getPModel("Exhaust"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2016-10-29"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "0E4FG9",
                    dm.getPartsRepository().getPModel("Exhaust"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2016-02-08"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "8R9AE2",
                    dm.getPartsRepository().getPModel("Exhaust"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2016-06-10"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "7U2TC4",
                    dm.getPartsRepository().getPModel("Brakes"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2015-12-11"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "8I1HY0",
                    dm.getPartsRepository().getPModel("Brakes"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2015-07-31"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "2V9FO8",
                    dm.getPartsRepository().getPModel("Brakes"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2017-02-28"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "1F0GO9",
                    dm.getPartsRepository().getPModel("Bulbs"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2016-09-11"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "3R2EP1",
                    dm.getPartsRepository().getPModel("Bulbs"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2011-12-10"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "5T6YU8",
                    dm.getPartsRepository().getPModel("Bulbs"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2012-12-21"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "3E8Y01",
                    dm.getPartsRepository().getPModel("Wiper Blades"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2014-11-01"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "7T6JI8",
                    dm.getPartsRepository().getPModel("Wiper Blades"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2016-08-15"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "8I0MP6",
                    dm.getPartsRepository().getPModel("Wiper Blades"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2017-01-12"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "0I9AC1",
                    dm.getPartsRepository().getPModel("Spark Plugs"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2015-12-09"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "6Y8UZ2",
                    dm.getPartsRepository().getPModel("Spark Plugs"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2016-07-12"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "3R2AJ0",
                    dm.getPartsRepository().getPModel("Spark Plugs"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2017-03-28"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "9U5XA6",
                    dm.getPartsRepository().getPModel("Clutch"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2016-09-10"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "6O3LZ2",
                    dm.getPartsRepository().getPModel("Clutch"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2017-03-28"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "9X1XF3",
                    dm.getPartsRepository().getPModel("Clutch"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2015-05-01"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "2F3SP5",
                    dm.getPartsRepository().getPModel("Tyre"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2015-02-10"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "5T6YU1",
                    dm.getPartsRepository().getPModel("Tyre"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2015-05-01"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "7C2NM2",
                    dm.getPartsRepository().getPModel("Tyre"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2015-11-29"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "8W1QK2",
                    dm.getPartsRepository().getPModel("Steering Wheel"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2017-02-10"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "8U6BN4",
                    dm.getPartsRepository().getPModel("Steering Wheel"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2015-06-10"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "9I8II1",
                    dm.getPartsRepository().getPModel("Tyre"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2016-11-01"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "2R2TY2",
                    dm.getPartsRepository().getPModel("Shock Absorber"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2015-08-02"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "0P9IL2",
                    dm.getPartsRepository().getPModel("Battery"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2016-12-21"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "3A1VA2",
                    dm.getPartsRepository().getPModel("Spark Plug"),
                    true,
                    null,
                    null,
                    LocalDate.parse("1997-06-10"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "0P4FG1",
                    dm.getPartsRepository().getPModel("Clutch"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2016-09-21"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "1R0HY2",
                    dm.getPartsRepository().getPModel("Brakes"),
                    true,
                    null,
                    null,
                    LocalDate.parse("2016-06-11"),
                    null,
                    null,
                    null,
                    null
                    ));
            partsItemRepo.save(piI(
                    "0Y3AV2",
                    dm.getPartsRepository().getPModel("Steering Wheel"),
                    false,
                    dm.getCustomerRepository().findByRegistration("LOL BUG").get(0),
                    null,
                    LocalDate.parse("2016-07-21"),
                    dm.getVehicleRepository().findByRegistration("LOL BUG").get(0),
                    dm.getBookingsRepository().findByRegistration("LOL BUG").get(0),
                    LocalDate.parse("2017-03-17"),
                    LocalDate.parse("2018-03-17")
                    ));
            partsItemRepo.save(piI(
                    "9W1QK4",
                    dm.getPartsRepository().getPModel("Shock Absorber"),
                    false,
                    dm.getCustomerRepository().findByRegistration("LE34 FGH").get(0),
                    null,
                    LocalDate.parse("2015-01-31"),
                    dm.getVehicleRepository().findByRegistration("LE34 FGH").get(0),
                    dm.getBookingsRepository().findByRegistration("LE34 FGH").get(0),
                    LocalDate.parse("2017-03-17"),
                    LocalDate.parse("2018-03-17")
                    ));
            partsItemRepo.save(piI(
                    "1S5DV2",
                    dm.getPartsRepository().getPModel("Shock Absorber"),
                    false,
                    dm.getCustomerRepository().findByRegistration("QP12 SGK").get(0),
                    null,
                    LocalDate.parse("2016-03-20"),
                    dm.getVehicleRepository().findByRegistration("QP12 SGK").get(0),
                    dm.getBookingsRepository().findByRegistration("QP12 SGK").get(0),
                    LocalDate.parse("2017-03-20"),
                    LocalDate.parse("2018-03-20")
                    ));
            partsItemRepo.save(piI(
                    "5P1DE7",
                    dm.getPartsRepository().getPModel("Shock Absorber"),
                    false,
                    dm.getCustomerRepository().findByRegistration("QP12 SGK").get(0),
                    null,
                    LocalDate.parse("2017-02-07"),
                    dm.getVehicleRepository().findByRegistration("QP12 SGK").get(0),
                    dm.getBookingsRepository().findByRegistration("QP12 SGK").get(0),
                    LocalDate.parse("2017-03-20"),
                    LocalDate.parse("2018-03-20")
                    ));
            partsItemRepo.save(piI(
                    "3R5TY7",
                    dm.getPartsRepository().getPModel("Tyre"),
                    false,
                    dm.getCustomerRepository().findByRegistration("GH04 SJF").get(0),
                    null,
                    LocalDate.parse("2015-07-10"),
                    dm.getVehicleRepository().findByRegistration("GH04 SJF").get(0),
                    dm.getBookingsRepository().findByRegistration("GH04 SJF").get(0),
                    LocalDate.parse("2017-03-20"),
                    LocalDate.parse("2018-03-20")
                    ));
            partsItemRepo.save(piI(
                    "1C3AS2",
                    dm.getPartsRepository().getPModel("Clutch"),
                    false,
                    dm.getCustomerRepository().findByRegistration("AS09 APX").get(0),
                    null,
                    LocalDate.parse("2016-09-18"),
                    dm.getVehicleRepository().findByRegistration("AS09 APX").get(0),
                    dm.getBookingsRepository().findByRegistration("AS09 APX").get(0),
                    LocalDate.parse("2017-03-23"),
                    LocalDate.parse("2018-03-23")
                    ));
            partsItemRepo.save(piI(
                    "9N0BP1",
                    dm.getPartsRepository().getPModel("Bulb"),
                    false,
                    dm.getCustomerRepository().findByRegistration("QH19 LFM").get(0),
                    null,
                    LocalDate.parse("2017-03-17"),
                    dm.getVehicleRepository().findByRegistration("QH19 LFM").get(0),
                    dm.getBookingsRepository().findByRegistration("QH19 LFM").get(0),
                    LocalDate.parse("2017-03-23"),
                    LocalDate.parse("2018-03-23")
                    ));
            partsItemRepo.save(piI(
                    "8S1CF5",
                    dm.getPartsRepository().getPModel("Shock Absorber"),
                    false,
                    dm.getCustomerRepository().findByRegistration("PS20 LOL").get(0),
                    null,
                    LocalDate.parse("2016-06-14"),
                    dm.getVehicleRepository().findByRegistration("PS20 LOL").get(0),
                    dm.getBookingsRepository().findByRegistration("PS20 LOL").get(0),
                    LocalDate.parse("2017-03-24"),
                    LocalDate.parse("2018-03-24")
                    ));
            partsItemRepo.save(piI(
                    "4L8MW2",
                    dm.getPartsRepository().getPModel("Steering Wheel"),
                    false,
                    dm.getCustomerRepository().findByRegistration("TU10 ASF").get(0),
                    null,
                    LocalDate.parse("2016-01-11"),
                    dm.getVehicleRepository().findByRegistration("TU10 ASF").get(0),
                    dm.getBookingsRepository().findByRegistration("TU10 ASF").get(0),
                    LocalDate.parse("2017-03-25"),
                    LocalDate.parse("2018-03-25")
                    ));
            partsItemRepo.save(piI(
                    "5V4YH6",
                    dm.getPartsRepository().getPModel("Brakes"),
                    false,
                    dm.getCustomerRepository().findByRegistration("RY93 JFK").get(0),
                    null,
                    LocalDate.parse("2016-05-11"),
                    dm.getVehicleRepository().findByRegistration("RY93 JFK").get(0),
                    dm.getBookingsRepository().findByRegistration("RY93 JFK").get(0),
                    LocalDate.parse("2017-03-27"),
                    LocalDate.parse("2018-03-27")
                    ));
            partsItemRepo.save(piI(
                    "6A2XC3",
                    dm.getPartsRepository().getPModel("Wiper Blades"),
                    false,
                    dm.getCustomerRepository().findByRegistration("LE73 ETS").get(0),
                    null,
                    LocalDate.parse("2016-12-23"),
                    dm.getVehicleRepository().findByRegistration("LE73 ETS").get(0),
                    dm.getBookingsRepository().findByRegistration("LE73 ETS").get(0),
                    LocalDate.parse("2017-03-27"),
                    LocalDate.parse("2018-03-27")
                    ));
            partsItemRepo.save(piI(
                    "1L3PK2",
                    dm.getPartsRepository().getPModel("Battery"),
                    false,
                    dm.getCustomerRepository().findByRegistration("LOL BUG").get(0),
                    null,
                    LocalDate.parse("2017-01-31"),
                    dm.getVehicleRepository().findByRegistration("LOL BUG").get(0),
                    dm.getBookingsRepository().findByRegistration("LOL BUG").get(0),
                    LocalDate.parse("2017-04-10"),
                    LocalDate.parse("2018-04-10")
                    ));
            partsItemRepo.save(piI(
                    "4V5FG0",
                    dm.getPartsRepository().getPModel("Battery"),
                    false,
                    dm.getCustomerRepository().findByRegistration("LE34 FGH").get(0),
                    null,
                    LocalDate.parse("2017-01-13"),
                    dm.getVehicleRepository().findByRegistration("LE34 FGH").get(0),
                    dm.getBookingsRepository().findByRegistration("LE34 FGH").get(0),
                    LocalDate.parse("2017-04-10"),
                    LocalDate.parse("2018-04-10")
                    ));
            partsItemRepo.save(piI(
                    "0A3OS4",
                    dm.getPartsRepository().getPModel("Spark Plug"),
                    false,
                    dm.getCustomerRepository().findByRegistration("LOL BUG").get(0),
                    null,
                    LocalDate.parse("2017-02-01"),
                    dm.getVehicleRepository().findByRegistration("LOL BUG").get(0),
                    dm.getBookingsRepository().findByRegistration("LOL BUG").get(0),
                    LocalDate.parse("2017-03-17"),
                    LocalDate.parse("2018-03-17")
            ));            
            LOG.info("Seeded parts item");
            return true;
        }
        return false;
    }

    private static boolean needsSeeding(DependencyManager dm) throws Exception {
        return dm.getPartsItemRepository().all(PartsItem.class).size() == 0;
    }
}