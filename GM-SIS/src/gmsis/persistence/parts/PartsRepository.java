/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.parts;

import gmsis.models.parts.PartsModel;
import gmsis.persistence.Repository;

import java.util.List;

/**
 *
 * @author Abdullah
 */
public interface PartsRepository extends Repository<PartsModel> {

    /**
     *
     * @param search
     * @return
     */
    public PartsModel getPModel(String search);
    public List<PartsModel> allModels();

}
