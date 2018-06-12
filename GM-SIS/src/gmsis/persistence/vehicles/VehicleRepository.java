/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.persistence.vehicles;

import gmsis.models.vehicles.Vehicle;
import gmsis.persistence.Repository;

import java.util.List;

/**
 * @author sabrinasaytac
 */
public interface VehicleRepository extends Repository<Vehicle> {

    public List<Vehicle> findByRegistration(String search);

    public List<Vehicle> findByManufacturer(String search);
    
    public List<Vehicle> findByCustomer(String search);
}
