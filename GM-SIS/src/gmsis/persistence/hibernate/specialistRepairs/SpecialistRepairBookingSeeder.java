/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.specialistRepairs;

import gmsis.di.DependencyManager;
import gmsis.models.bookings.Booking;
import gmsis.models.customers.Customer;
import gmsis.models.parts.PartsItem;
import gmsis.models.specialistRepairs.SpecialistRepair;
import gmsis.models.specialistRepairs.SpecialistRepairCentre;
import gmsis.models.vehicles.Vehicle;
import gmsis.persistence.specialistRepair.SpecialistRepairsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Stephen
 */
public class SpecialistRepairBookingSeeder {
    private static final Logger LOG = Logger.getLogger(SpecialistRepairBookingSeeder.class.getName());

    private static SpecialistRepair spcBI(double cost, LocalDate deliveryDate, LocalDate returnDate, String completed, SpecialistRepairCentre spc, Vehicle vehicle, Customer customer, PartsItem partItem, Booking booking) {
        return new SpecialistRepair(cost, deliveryDate, returnDate, completed, spc, vehicle, customer, partItem, booking);
    }

    public static boolean seedSPCIfNecessary(DependencyManager dm) throws Exception {
        if (needsSeeding(dm)) {
            LOG.info("Seeding Specialist Repair Bookings...");
            SpecialistRepairsRepository specialistRepairRepo = dm.getSpecialistRepairsRepository();

            List<PartsItem> partItems = dm.getPartsItemRepository().all(PartsItem.class);
            List<Booking> bookings = dm.getBookingsRepository().all(Booking.class);

            //10 past bookings
            specialistRepairRepo.save(spcBI(
                    150.0,
                    LocalDate.parse("2017-01-01"),
                    LocalDate.parse("2017-02-01"),
                    "Yes",
                    dm.getSpecialistRepairCentreRepository().findByName("Vaxhaull"),
                    bookings.get(3).getVehicle(),
                    bookings.get(3).getVehicle().getOwner(),
                    partItems.get(0),
                    bookings.get(3)
            ));
            specialistRepairRepo.save(spcBI(
                    160.0,
                    LocalDate.parse("2017-01-02"),
                    LocalDate.parse("2017-02-02"),
                    "Yes",
                    dm.getSpecialistRepairCentreRepository().findByName("Vaxhaull"),
                    bookings.get(3).getVehicle(),
                    bookings.get(3).getVehicle().getOwner(),
                    partItems.get(1),
                    bookings.get(3)
            ));
            specialistRepairRepo.save(spcBI(
                    170.0,
                    LocalDate.parse("2017-01-03"),
                    LocalDate.parse("2017-02-03"),
                    "Yes",
                    dm.getSpecialistRepairCentreRepository().findByName("Citroen"),
                    bookings.get(4).getVehicle(),
                    bookings.get(4).getVehicle().getOwner(),
                    partItems.get(2),
                    bookings.get(4)
            ));
            specialistRepairRepo.save(spcBI(
                    110.0,
                    LocalDate.parse("2017-01-04"),
                    LocalDate.parse("2017-02-04"),
                    "Yes",
                    dm.getSpecialistRepairCentreRepository().findByName("Vaxhaull"),
                    bookings.get(5).getVehicle(),
                    bookings.get(5).getVehicle().getOwner(),
                    partItems.get(3),
                    bookings.get(5)
            ));
            specialistRepairRepo.save(spcBI(
                    125.0,
                    LocalDate.parse("2017-02-02"),
                    LocalDate.parse("2017-03-02"),
                    "Yes",
                    dm.getSpecialistRepairCentreRepository().findByName("Citroen"),
                    bookings.get(4).getVehicle(),
                    bookings.get(4).getVehicle().getOwner(),
                    partItems.get(4),
                    bookings.get(4)
            ));
            specialistRepairRepo.save(spcBI(
                    90.0,
                    LocalDate.parse("2017-03-01"),
                    LocalDate.parse("2017-03-05"),
                    "Yes",
                    dm.getSpecialistRepairCentreRepository().findByName("Renault"),
                    bookings.get(6).getVehicle(),
                    bookings.get(6).getVehicle().getOwner(),
                    null,
                    bookings.get(6)
            ));
            specialistRepairRepo.save(spcBI(
                    100.0,
                    LocalDate.parse("2017-03-03"),
                    LocalDate.parse("2017-03-04"),
                    "Yes",
                    dm.getSpecialistRepairCentreRepository().findByName("Renault"),
                    bookings.get(6).getVehicle(),
                    bookings.get(6).getVehicle().getOwner(),
                    partItems.get(6),
                    bookings.get(6)
            ));
            specialistRepairRepo.save(spcBI(
                    155.0,
                    LocalDate.parse("2016-11-02"),
                    LocalDate.parse("2016-12-02"),
                    "Yes",
                    dm.getSpecialistRepairCentreRepository().findByName("Mercedes-Benz"),
                    bookings.get(7).getVehicle(),
                    bookings.get(7).getVehicle().getOwner(),
                    null,
                    bookings.get(7)
            ));
            specialistRepairRepo.save(spcBI(
                    130.0,
                    LocalDate.parse("2016-01-02"),
                    LocalDate.parse("2016-02-02"),
                    "Yes",
                    dm.getSpecialistRepairCentreRepository().findByName("Ford"),
                    bookings.get(9).getVehicle(),
                    bookings.get(9).getVehicle().getOwner(),
                    null,
                    bookings.get(9)
            ));
            specialistRepairRepo.save(spcBI(
                    250.0,
                    LocalDate.parse("2016-12-02"),
                    LocalDate.parse("2017-01-02"),
                    "Yes",
                    dm.getSpecialistRepairCentreRepository().findByName("Mercedes-Benz"),
                    bookings.get(7).getVehicle(),
                    bookings.get(7).getVehicle().getOwner(),
                    partItems.get(9),
                    bookings.get(7)
            ));
            //10 Future Bookings
            specialistRepairRepo.save(spcBI(
                    150.0,
                    LocalDate.parse("2017-05-02"),
                    LocalDate.parse("2017-06-02"),
                    "No",
                    dm.getSpecialistRepairCentreRepository().findByName("Volkswagen"),
                    bookings.get(10).getVehicle(),
                    bookings.get(10).getVehicle().getOwner(),
                    partItems.get(10),
                    bookings.get(10)
            ));
            specialistRepairRepo.save(spcBI(
                    130.0,
                    LocalDate.parse("2017-05-15"),
                    LocalDate.parse("2017-06-17"),
                    "No",
                    dm.getSpecialistRepairCentreRepository().findByName("Vaxhaull"),
                    bookings.get(13).getVehicle(),
                    bookings.get(13).getVehicle().getOwner(),
                    partItems.get(11),
                    bookings.get(13)
            ));
            specialistRepairRepo.save(spcBI(
                    175.0,
                    LocalDate.parse("2017-06-14"),
                    LocalDate.parse("2017-06-28"),
                    "No",
                    dm.getSpecialistRepairCentreRepository().findByName("Vaxhaull"),
                    bookings.get(13).getVehicle(),
                    bookings.get(13).getVehicle().getOwner(),
                    partItems.get(12),
                    bookings.get(13)
            ));
            specialistRepairRepo.save(spcBI(
                    230.0,
                    LocalDate.parse("2017-04-30"),
                    LocalDate.parse("2017-05-15"),
                    "No",
                    dm.getSpecialistRepairCentreRepository().findByName("Citroen"),
                    bookings.get(14).getVehicle(),
                    bookings.get(14).getVehicle().getOwner(),
                    null,
                    bookings.get(14)
            ));
            specialistRepairRepo.save(spcBI(
                    105.0,
                    LocalDate.parse("2017-05-15"),
                    LocalDate.parse("2017-06-30"),
                    "No",
                    dm.getSpecialistRepairCentreRepository().findByName("Renault"),
                    bookings.get(16).getVehicle(),
                    bookings.get(16).getVehicle().getOwner(),
                    partItems.get(14),
                    bookings.get(16)
            ));
            specialistRepairRepo.save(spcBI(
                    100.0,
                    LocalDate.parse("2017-05-02"),
                    LocalDate.parse("2017-06-02"),
                    "No",
                    dm.getSpecialistRepairCentreRepository().findByName("Mercedes-Benz"),
                    bookings.get(17).getVehicle(),
                    bookings.get(17).getVehicle().getOwner(),
                    null,
                    bookings.get(17)
            ));
            specialistRepairRepo.save(spcBI(
                    110.0,
                    LocalDate.parse("2017-07-02"),
                    LocalDate.parse("2017-09-17"),
                    "No",
                    dm.getSpecialistRepairCentreRepository().findByName("Ford"),
                    bookings.get(19).getVehicle(),
                    bookings.get(19).getVehicle().getOwner(),
                    null,
                    bookings.get(19)
            ));
            specialistRepairRepo.save(spcBI(
                    150.0,
                    LocalDate.parse("2017-06-01"),
                    LocalDate.parse("2017-06-30"),
                    "No",
                    dm.getSpecialistRepairCentreRepository().findByName("Vaxhaull"),
                    bookings.get(13).getVehicle(),
                    bookings.get(13).getVehicle().getOwner(),
                    partItems.get(17),
                    bookings.get(13)
            ));
            specialistRepairRepo.save(spcBI(
                    200.0,
                    LocalDate.parse("2017-05-22"),
                    LocalDate.parse("2017-06-30"),
                    "No",
                    dm.getSpecialistRepairCentreRepository().findByName("Volkswagen"),
                    bookings.get(10).getVehicle(),
                    bookings.get(10).getVehicle().getOwner(),
                    partItems.get(18),
                    bookings.get(10)
            ));
            specialistRepairRepo.save(spcBI(
                    155.0,
                    LocalDate.parse("2017-05-09"),
                    LocalDate.parse("2017-06-14"),
                    "No",
                    dm.getSpecialistRepairCentreRepository().findByName("Citroen"),
                    bookings.get(14).getVehicle(),
                    bookings.get(14).getVehicle().getOwner(),
                    partItems.get(19),
                    bookings.get(14)
            ));

            List<SpecialistRepair> sr = dm.getSpecialistRepairsRepository().all(SpecialistRepair.class);
            for (int i = 0; i < 20; i++) {
                partItems.get(i).setAvailable(false);
                partItems.get(i).setInstalledOn(sr.get(i).getDeliveryDate());
                partItems.get(i).setWarrantyEnd(sr.get(i).getDeliveryDate().plusYears(1));
                partItems.get(i).setCustomer(sr.get(i).getCustomer());
                partItems.get(i).setVehicle(sr.get(i).getVehicle());
                partItems.get(i).setRepair(sr.get(i));
                dm.getPartsItemRepository().save(partItems.get(i));
                if(sr.get(i).getBooking().getDone().equalsIgnoreCase("yes")){
                dm.getBillRepository().getOrCreateBill(sr.get(i).getBooking());
                }
            }
            LOG.info("Seeded Specialist Repair Bookings");

            return true;
        }
        return false;
    }


    private static boolean needsSeeding(DependencyManager dm) throws Exception {
        return dm.getSpecialistRepairsRepository().all(SpecialistRepair.class).size() == 0;
    }
}
