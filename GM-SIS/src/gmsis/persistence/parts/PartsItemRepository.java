/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.parts;

import gmsis.models.parts.PartsItem;
import gmsis.models.vehicles.Vehicle;
import gmsis.persistence.Repository;

import java.util.List;

/**
 *
 * @author Abdullah
 */
public interface PartsItemRepository extends Repository<PartsItem> {

    /**
     *
     * @param search
     * @return
     */
    public PartsItem getPartItem(String search);
    public List<PartsItem> getAvailablePartsItem(String search);
    public List<PartsItem> searchCustomerui(String search);
    public List<PartsItem> searchRegistrationui(String search);
    public List<PartsItem> getAvailable();
    public List<PartsItem> inRepairs();
    public List<Vehicle> getByReg(String search);
    public List<PartsItem> getByName(String search);
    public List<PartsItem> currentDR();
}
