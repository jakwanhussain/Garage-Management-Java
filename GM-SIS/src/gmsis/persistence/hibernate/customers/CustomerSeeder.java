/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.hibernate.customers;

import gmsis.di.DependencyManager;
import gmsis.models.customers.Customer;
import gmsis.models.customers.CustomerType;
import gmsis.persistence.customers.CustomerRepository;

import java.util.logging.Logger;

/**
 *
 * @author filip
 */
public final class CustomerSeeder {

    private static final Logger LOG = Logger.getLogger(CustomerSeeder.class.getName());

    /**
     * Shortcut to new Customer(..., INDIVIDUAL);
     */
    private static Customer cI(String name, String addr, String post, String phone, String email) {
        return new Customer(name, addr, post, phone, email, CustomerType.INDIVIDUAL);
    }

    /**
     * Shortcut to new Customer(..., BUSINESS);
     */
    private static Customer cB(String name, String addr, String post, String phone, String email) {
        return new Customer(name, addr, post, phone, email, CustomerType.BUSINESS);
    }

    public static boolean seedCustomersIfNecessary(DependencyManager dm) throws Exception {
        if (needsSeeding(dm)) {
            LOG.info("Seeding customers...");
            CustomerRepository customerRepo = dm.getCustomerRepository();
            Customer mustafa = cI(
                    "Mustafa Bozkurt",
                    "CS 419A, Peter Landin Building, Queen Mary University of London, Mile End Rd, London",
                    "E1 4NS",
                    "+442078826455",
                    "m.bozkurt@qmul.ac.uk"
            );
            customerRepo.save(mustafa);
//            Bill nye /* the science guy */ = new Bill();
//            nye.setStatus(InvoiceStatus.OUTSTANDING);
//            nye.setAmount(9000.01);
//            nye.setCustomer(mustafa);
//            nye.setBooking(bk);
//            dm.getBillRepository().save(nye);
            customerRepo.save(cI(
                    "David Edwards",
                    "23 Stone St, CRAPSTONE",
                    "PL20 2PZ",
                    "+447086056318",
                    "DavidAEdwards@rhyta.com"
            ));
            customerRepo.save(cI(
                    "Lorraine J. Tomlinson",
                    "23 Telford Street, BANKNOCK",
                    "FK4 4EW",
                    "+447777960107",
                    "LorraineJTomlinson@teleworm.us"
            ));
            customerRepo.save(cI(
                    "Steven Winn",
                    "64 Warner Close, HAUGHTON LE SKERNE",
                    "DL1 3QU",
                    "+447931981906",
                    "StevenSWinn@teleworm.us"
            ));
            customerRepo.save(cI(
                    "Maja McKenzie",
                    "70 West Lane DARESBURY",
                    "WA4 8AF",
                    "+447035908136",
                    "MajaMcKenzie@dayrep.com"
            ));
            customerRepo.save(cI(
                    "Stefnir Einarsson",
                    "60 Helland Bridge ULTING",
                    "CM9 6ED",
                    "+447925677162",
                    "StefnirEinarsson@rhyta.com"
            ));
            customerRepo.save(cB(
                    "Kattan Plaster Ltd",
                    "75 Wood Lane BADNABAY",
                    "IV27 9ZT",
                    "+447086738823",
                    "NazimMahbubKattan@armyspy.com"
            ));
            customerRepo.save(cI(
                    "Fre-weini Kidane",
                    "72 Sea Road LANE HEAD",
                    "WA3 8FS",
                    "+447834765560",
                    "Fre-weiniKidane@teleworm.us"
            ));
            customerRepo.save(cB(
                    "Marina Silva Solicitors",
                    "58 Well Lane PAXHILL PARK",
                    "RH16 2ZQ",
                    "+447741447071",
                    "MarinaAzevedoSilva@dayrep.com"
            ));
            customerRepo.save(cI(
                    "Angelique Ruest",
                    "51 Fulford Road PENSARN",
                    "LL22 5FP",
                    "+447745592512",
                    "AngeliqueRuest@jourrapide.com"
            ));
            LOG.info("Seeded customers");
            return true;
        }
        return false;
    }

    private static boolean needsSeeding(DependencyManager dm) throws Exception {
        return dm.getCustomerRepository().all(Customer.class).size() == 0;
    }
}
