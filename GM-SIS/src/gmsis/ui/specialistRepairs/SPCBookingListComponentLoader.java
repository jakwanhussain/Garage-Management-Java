/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.specialistRepairs;

import gmsis.controllers.specialistRepairs.SPCBookingListController;
import gmsis.di.DependencyManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 *
 * @author Stephen
 */
public class SPCBookingListComponentLoader {
    
    private DependencyManager dm;

    public SPCBookingListComponentLoader(DependencyManager instance) {
        this.dm = instance;
    }

    public Node load() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/specialistRepairs/SPCBookingList.fxml"));
        Node node = loader.load();
        SPCBookingListController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        return node;
    }
}
