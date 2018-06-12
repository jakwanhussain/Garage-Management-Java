/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.specialistRepairs;

import gmsis.controllers.specialistRepairs.SPCFormController;
import gmsis.di.DependencyManager;
import gmsis.models.specialistRepairs.SpecialistRepairCentre;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 *
 * @author stephen
 */
public class SPCFormComponentLoader {

    private DependencyManager dm;

    public SPCFormComponentLoader(DependencyManager instance) {
        this.dm = instance;
    }

    public Node load(SpecialistRepairCentre spc) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/specialistRepairs/SPCForm.fxml"));
        Node node = loader.load();
        SPCFormController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        ctrl.setSPC(spc);
        return node;
    }

}