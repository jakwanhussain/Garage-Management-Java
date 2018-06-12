/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.parts;

import gmsis.controllers.parts.PartsController;
import gmsis.di.DependencyManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 *
 * @author Abdullah
 */
public class PartsComponentLoader {
    private DependencyManager dm;

    public PartsComponentLoader(DependencyManager dm) {
        this.dm = dm;
    }
    
    public Node load() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/parts/Parts.fxml"));
        Node node = loader.load();
        PartsController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        return node;
    }
}
