/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.di;

import gmsis.persistence.bookings.BookingsRepository;
import gmsis.persistence.bookings.MechanicsRepository;
import gmsis.persistence.customers.BillRepository;
import gmsis.persistence.customers.CustomerRepository;
import gmsis.persistence.hibernate.bookings.HibernateBookingsRepository;
import gmsis.persistence.hibernate.bookings.HibernateMechanicsRepository;
import gmsis.persistence.hibernate.customers.HibernateBillRepository;
import gmsis.persistence.hibernate.customers.HibernateCustomerRepository;
import gmsis.persistence.hibernate.parts.HibernatePartItemsRepository;
import gmsis.persistence.hibernate.parts.HibernatePartsRepository;
import gmsis.persistence.hibernate.specialistRepairs.HibernateSpecialistRepairCentreRepository;
import gmsis.persistence.hibernate.specialistRepairs.HibernateSpecialistRepairsRepository;
import gmsis.persistence.hibernate.users.HibernateUserRepository;
import gmsis.persistence.hibernate.vehicles.HibernateVehicleRepository;
import gmsis.persistence.hibernate.vehicles.HibernateWarrantyCompanyRepository;
import gmsis.persistence.parts.PartsItemRepository;
import gmsis.persistence.parts.PartsRepository;
import gmsis.persistence.specialistRepair.SpecialistRepairCentreRepository;
import gmsis.persistence.specialistRepair.SpecialistRepairsRepository;
import gmsis.persistence.users.UserRepository;
import gmsis.persistence.vehicles.VehicleRepository;
import gmsis.persistence.vehicles.WarrantyCompanyRepository;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author filip
 */
public class ApplicationDependencyManager implements DependencyManager {
    private static DependencyManager instance = null;

    private SessionFactory sessionFactory = null;

    public static DependencyManager getInstance() {
        if (instance == null) instance = new ApplicationDependencyManager();
        return instance;
    }

    @Override
    public UserRepository getUserRepository() {
        return new HibernateUserRepository(getSessionFactory());
    }

    @Override
    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
		.configure("hibernate.cfg.xml")
		.build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        return sessionFactory;
    }

    @Override
    public CustomerRepository getCustomerRepository() {
        return new HibernateCustomerRepository(getSessionFactory());
    }

    @Override
    public VehicleRepository getVehicleRepository() {
        return new HibernateVehicleRepository(getSessionFactory());
    }

    @Override
    public WarrantyCompanyRepository getWarrantyCompanyRepository() {
        return new HibernateWarrantyCompanyRepository(getSessionFactory());
    }

    @Override
    public BillRepository getBillRepository() {
        return new HibernateBillRepository(getSessionFactory());
    }

    @Override
    public BookingsRepository getBookingsRepository(){
        return new HibernateBookingsRepository(getSessionFactory());
    }

    @Override
    public MechanicsRepository getMechanicsRepository(){
        return new HibernateMechanicsRepository(getSessionFactory());
    }

    @Override
    public PartsRepository getPartsRepository(){
        return new HibernatePartsRepository(getSessionFactory());
    }

    @Override
    public SpecialistRepairsRepository getSpecialistRepairsRepository(){
        return new HibernateSpecialistRepairsRepository(getSessionFactory());
    }
    @Override
    public SpecialistRepairCentreRepository getSpecialistRepairCentreRepository(){
        return new HibernateSpecialistRepairCentreRepository(getSessionFactory());
    }
    public PartsItemRepository getPartsItemRepository(){
        return new HibernatePartItemsRepository(getSessionFactory());
    }
}
