/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.bookings;

import gmsis.di.DependencyManager;
import gmsis.models.bookings.Booking;
import gmsis.models.bookings.Mechanic;
import gmsis.models.customers.Bill;
import gmsis.models.customers.Customer;
import gmsis.models.customers.InvoiceStatus;
import gmsis.models.parts.PartsItem;
import gmsis.models.vehicles.Vehicle;
import gmsis.persistence.bookings.BookingsRepository;
import java.time.LocalDate;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author jakwan
 */
public class BookingSeeder {
    private static final Logger LOG = Logger.getLogger(BookingSeeder.class.getName());
 
    private static Booking b1(Customer customer, LocalDate bookingDate, String bookingTime, double duration, int mileage, double cost, String detail,Vehicle vehicle, String bookingType,  String bookingStatus, Mechanic mechanic, Bill bill) {
        return new Booking(customer, bookingDate, bookingTime, duration, mileage, cost, detail, vehicle, bookingType, bookingStatus, mechanic, bill);
    }
    
    public static boolean seedBookingNecessary(DependencyManager dm) throws Exception {  
        if (needsSeeding(dm)) {
            LOG.info("Seeding Booking seeder.....");
            
            BookingsRepository bookingRepo = dm.getBookingsRepository();
            List<Customer> customer= dm.getCustomerRepository().all(Customer.class);
            List<Vehicle> vehicles = dm.getVehicleRepository().all(Vehicle.class);
            List<Mechanic> mechanics = dm.getMechanicsRepository().all(Mechanic.class);
            List<Bill> bill = dm.getBillRepository().all(Bill.class);
            
            //These bookings are fully integrated with other modules like Mechanics, parts, customer and vehicle
            /**********************Past booking starts here**********************/
            bookingRepo.save(b1(
                dm.getCustomerRepository().findByName("Mustafa").get(0),
                LocalDate.parse("2017-03-17"),
                "12:00",
                04.00,
                31337,
                320.0,
                "need new steering wheel also need spark plug",
                dm.getVehicleRepository().findByRegistration("LOL BUG").get(0),
                "Diagnosis & Repairs",
                "Yes", 
                mechanics.get(4),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("David").get(0),
                LocalDate.parse("2017-03-17"),
                "14:00",
                03.00,
                15670,
                121.0,
                "Car needs a shock absorber",
                dm.getVehicleRepository().findByRegistration("LE34 FGH").get(0),
                "Diagnosis & Repairs",
                "Yes", 
                mechanics.get(0),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Steven").get(0),
                LocalDate.parse("2017-03-20"),
                "14:00",
                02.00,
                36124,
                150.0,
                "Car need shock absorber",
                dm.getVehicleRepository().findByRegistration("QP12 SGK").get(0),
                "Diagnosis & Repairs",
                "Yes", 
                mechanics.get(3),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Lorrai").get(0),
                LocalDate.parse("2017-03-20"),
                "13:00",
                03.00,
                25795,
                121.0,
                "Car needs a service also engine has issue. Makes weird sound"
                + "\nAlso need a new tyre",
                dm.getVehicleRepository().findByRegistration("GH04 SJF").get(0),
                "Specialist Repair",
                "Yes", 
                mechanics.get(0),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Maja").get(0),
                LocalDate.parse("2017-03-23"),
                "02:30",
                03.00,
                89012,
                225.0,
                "Need new clutch also customer find gearbox needs a good check",
                dm.getVehicleRepository().findByRegistration("AS09 APX").get(0),
                "Specialist Repair",
                "Yes", 
                mechanics.get(3),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Kattan").get(0),
                LocalDate.parse("2017-03-23"),
                "14:00",
                03.00,
                23568,
                151.0,
                "Car needs a service also engine has issue. Makes weird sound",
                dm.getVehicleRepository().findByRegistration("QH19 LFM").get(0),
                "Specialist Repair",
                "Yes", 
                mechanics.get(1),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Fre-weini").get(0),
                LocalDate.parse("2017-03-24"),
                "14:00",
                03.00,
                6702,
                225.0,
                "Need a good service also new shock absorber",
                dm.getVehicleRepository().findByRegistration("PS20 LOL").get(0),
                "Specialist Repair",
                "Yes", 
                mechanics.get(3),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Fre-weini").get(0),
                LocalDate.parse("2017-03-25"),
                "11:00",
                04.00,
                12400,
                320.00,
                "Engine failure also need new steering wheel",
                dm.getVehicleRepository().findByRegistration("TU10 ASF").get(0),
                "Specialist Repair",
                "Yes", 
                mechanics.get(4),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Marina Silva").get(0),
                LocalDate.parse("2017-03-27"),
                "16:00",
                00.50,
                23573,
                20.25,
                "Need new breaks",
                dm.getVehicleRepository().findByRegistration("RY93 JFK").get(0),
                "Diagnosis & Repairs",
                "Yes", 
                mechanics.get(0),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Angelique").get(0),
                LocalDate.parse("2017-03-27"),
                "14:00",
                03.00,
                92300,
                240.0,
                "Car needs a service also engine has issue."
                        + "\nNeed new wiper blades",
                dm.getVehicleRepository().findByRegistration("LE73 ETS").get(0),
                "Specialist Repair",
                "Yes", 
                mechanics.get(4),
                null
            ));
            
            /**********************Future booking starts here**********************/ 
            
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Mustafa").get(0),
                LocalDate.parse("2017-04-10"),
                "11:00",
                00.00,
                31500,
                00.00,
                "Car has timing belt issue and need a new battery",
                dm.getVehicleRepository().findByRegistration("LOL BUG").get(0),
                "Specialist Repair",
                "No", 
                mechanics.get(4),
                null
                //dm.getBillRepository().all(Bill.class).setText("Bill"+booking.getBill().getId())
                //dm.getBillRepository().save(dm.getBookingsRepository().findById(Long.valueOf(52)).getBill())
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("David").get(0),
                LocalDate.parse("2017-04-10"),
                "09:00",
                00.00,
                15900,
                00.00,
                "Low power may be need a new battery",
                dm.getVehicleRepository().findByRegistration("LE34 FGH").get(0),
                "Diagnosis & Repairs",
                "No", 
                mechanics.get(0),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Steven").get(0),
                LocalDate.parse("2017-04-10"),
                "11:00",
                00.00,
                36600,
                00.00,
                "Bush needed",
                dm.getVehicleRepository().findByRegistration("QP12 SGK").get(0),
                "Diagnosis & Repairs",
                "No", 
                mechanics.get(3),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Maja").get(0),
                LocalDate.parse("2017-04-10"),
                "12:00",
                00.00,
                25900,
                00.00,
                "Smell comes from engine again.",
                dm.getVehicleRepository().findByRegistration("GH04 SJF").get(0),
                "Specialist Repair",
                "No", 
                mechanics.get(0),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Stefnir").get(0),
                LocalDate.parse("2017-04-11"),
                "12:00",
                00.00,
                89300,
                00.00,
                "Break issue",
                dm.getVehicleRepository().findByRegistration("AS09 APX").get(0),
                "Specialist Repair",
                "No", 
                mechanics.get(3),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Kattan").get(0),
                LocalDate.parse("2017-04-12"),
                "13:00",
                00.00,
                23700,
                00.00,
                "Need new bulbs all around",
                dm.getVehicleRepository().findByRegistration("QH19").get(0),
                "Diagnosis & Repairs",
                "No", 
                mechanics.get(1),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Fre-weini").get(0),
                LocalDate.parse("2017-04-14"),
                "16:00",
                00.00,
                6900,
                00.00,
                "Bonnet issue also knock from bottom",
                dm.getVehicleRepository().findByRegistration("PS20").get(0),
                "Specialist Repair",
                "No", 
                mechanics.get(3),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Fre-weini").get(0),
                LocalDate.parse("2017-04-13"),
                "11:00",
                00.00,
                12600,
                00.00,
                "Engine failure again.",
                dm.getVehicleRepository().findByRegistration("TU10").get(0),
                "Specialist Repair",
                "No", 
                mechanics.get(1),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Marina Silva").get(0),
                LocalDate.parse("2017-04-13"),
                "16:00",
                00.00,
                23700,
                00.00,
                "Need new bush and clutch",
                dm.getVehicleRepository().findByRegistration("RY93 JFK").get(0),
                "Diagnosis & Repairs",
                "No", 
                mechanics.get(2),
                null
            ));
            bookingRepo.save(b1(dm.getCustomerRepository().findByName("Angelique").get(0),
                LocalDate.parse("2017-04-13"),
                "13:00",
                00.00,
                92600,
                00.00,
                "Clutch is gone, need a new clutch",
                dm.getVehicleRepository().findByRegistration("LE73").get(0),
                "Specialist Repair",
                "No", 
                mechanics.get(4),
                null
            ));
            LOG.info("seeded Bookings");
            List<Booking> bookings = dm.getBookingsRepository().all(Booking.class);
            for(int i = 0; i< bookings.size();i++)
            {
                if(bookings.get(i).getDone().equalsIgnoreCase("yes")){
                    dm.getBillRepository().getOrCreateBill(bookings.get(i));
                }
            }
            return true;
        }
        return false;
    }
        
    private static boolean needsSeeding(DependencyManager dm) throws Exception {
        return dm.getBookingsRepository().all(Booking.class).size() == 0;
    }
}
