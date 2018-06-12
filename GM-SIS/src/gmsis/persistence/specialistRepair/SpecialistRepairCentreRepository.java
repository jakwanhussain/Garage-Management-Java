/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.specialistRepair;

import gmsis.models.specialistRepairs.SpecialistRepairCentre;
import gmsis.persistence.Repository;

/**
 *
 * @author Stephen
 */
public interface SpecialistRepairCentreRepository extends Repository<SpecialistRepairCentre>{

    public SpecialistRepairCentre findByName(String search);
    
}
