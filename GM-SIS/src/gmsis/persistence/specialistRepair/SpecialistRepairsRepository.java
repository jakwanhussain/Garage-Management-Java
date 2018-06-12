/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package gmsis.persistence.specialistRepair;

import gmsis.models.specialistRepairs.SpecialistRepair;
import gmsis.persistence.Repository;

import java.util.List;

/**
 *
 * @author Stephen
 */
public interface SpecialistRepairsRepository extends Repository<SpecialistRepair> {

    public SpecialistRepair getSpecialistRepair(long id);
    public List<SpecialistRepair>  findByName(String name);
    public List<SpecialistRepair>  findByReg(String reg);
    public List<SpecialistRepair> findBySPC(String spcId);
}
