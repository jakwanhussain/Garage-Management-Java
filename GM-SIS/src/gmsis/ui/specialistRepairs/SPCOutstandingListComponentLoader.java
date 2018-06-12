/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.specialistRepairs;

import gmsis.controllers.specialistRepairs.SPCOutstandingListController;
import gmsis.di.DependencyManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 *
 * @author Stephen
 */
public class SPCOutstandingListComponentLoader {
        
    private DependencyManager dm;

    public SPCOutstandingListComponentLoader(DependencyManager dm){
        this.dm = dm;
    }
    public Node load() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/specialistRepairs/SPCOutstandingList.fxml"));
        Node node = loader.load();
        SPCOutstandingListController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);  
        return node;
    }
}