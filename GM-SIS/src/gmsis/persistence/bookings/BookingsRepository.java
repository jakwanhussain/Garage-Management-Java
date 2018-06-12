/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.bookings;

import gmsis.models.bookings.Booking;
import gmsis.models.vehicles.Vehicle;
import gmsis.persistence.Repository;

import java.util.List;
/**
 *
 * @author jakwan
 */
public interface BookingsRepository extends Repository<Booking>{
    public Booking findById(Long id);
    public List<Booking> findByRegistration(String reg);
    public List<Booking> findByName(String name);
    public List<Booking> findByManufacturer(String manu);
    public List<Booking> getNotCompleted();    
}
