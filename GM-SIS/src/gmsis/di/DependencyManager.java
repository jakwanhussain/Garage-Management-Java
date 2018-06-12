/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.di;

import gmsis.persistence.users.UserRepository;
import gmsis.persistence.vehicles.VehicleRepository;
import gmsis.persistence.vehicles.WarrantyCompanyRepository;
import gmsis.persistence.bookings.BookingsRepository;
import gmsis.persistence.bookings.MechanicsRepository;
import gmsis.persistence.customers.BillRepository;
import gmsis.persistence.customers.CustomerRepository;
import gmsis.persistence.parts.PartsItemRepository;
import gmsis.persistence.parts.PartsRepository;
import gmsis.persistence.specialistRepair.SpecialistRepairCentreRepository;
import gmsis.persistence.specialistRepair.SpecialistRepairsRepository;
import org.hibernate.SessionFactory;

/**
 *
 * @author filip
 */
public interface DependencyManager {
    public UserRepository getUserRepository();

    public CustomerRepository getCustomerRepository();

    public VehicleRepository getVehicleRepository();

    public WarrantyCompanyRepository getWarrantyCompanyRepository();

    public BillRepository getBillRepository();

    public SessionFactory getSessionFactory();

    public BookingsRepository getBookingsRepository();

    public MechanicsRepository getMechanicsRepository();

    public SpecialistRepairsRepository getSpecialistRepairsRepository();

    public SpecialistRepairCentreRepository getSpecialistRepairCentreRepository();

    public PartsRepository getPartsRepository();

    public PartsItemRepository getPartsItemRepository();


}
