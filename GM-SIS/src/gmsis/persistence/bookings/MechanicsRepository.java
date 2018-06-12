/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.bookings;

import gmsis.models.bookings.Mechanic;
import gmsis.persistence.Repository;

import java.util.List;
/**
 *
 * @author jakwan
 */
public interface MechanicsRepository extends Repository<Mechanic> {
    //public List<Mechanics> findById(String search);
    public List<Mechanic> findById(Long id);
}
