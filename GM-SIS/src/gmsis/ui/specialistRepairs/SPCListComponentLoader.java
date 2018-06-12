/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.specialistRepairs;

import gmsis.controllers.specialistRepairs.SPCListController;
import gmsis.di.DependencyManager;
import gmsis.models.authentication.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 *
 * @author Stephen
 */
public class SPCListComponentLoader {
    
        private DependencyManager dm;

    public SPCListComponentLoader(DependencyManager dm){
        this.dm = dm;
    }
    public Node load(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/specialistRepairs/SPCList.fxml"));
        Node node = loader.load();
        SPCListController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        ctrl.setUser(user);
        return node;
    }  
}

