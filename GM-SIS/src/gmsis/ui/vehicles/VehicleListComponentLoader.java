/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.ui.vehicles;

import gmsis.controllers.vehicle.VehicleListController;
import gmsis.di.DependencyManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Created by sabrinasaytac on 23/02/2017.
 */
public class VehicleListComponentLoader {

    private DependencyManager dm;

    public VehicleListComponentLoader(DependencyManager dm) {
        this.dm = dm;
    }

    public Node load() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/vehicles/VehicleList.fxml"));
        Node node = loader.load();
        VehicleListController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        try {
            ctrl.shown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return node;
    }
}
