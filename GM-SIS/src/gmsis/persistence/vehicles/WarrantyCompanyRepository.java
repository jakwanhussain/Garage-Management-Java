/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.persistence.vehicles;

import gmsis.models.vehicles.WarrantyCompany;
import gmsis.persistence.Repository;

import java.util.List;

/**
 * Created by sabrinasaytac on 02/03/2017.
 */
public interface WarrantyCompanyRepository extends Repository<WarrantyCompany> {

    List<WarrantyCompany> findByName(String companyName);

}
