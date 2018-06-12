/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.ui.vehicles;

import gmsis.controllers.vehicle.VehicleFormController;
import gmsis.di.DependencyManager;
import gmsis.models.vehicles.Vehicle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * Created by sabrinasaytac on 23/02/2017.
 */

public class VehicleFormComponentLoader {

    private DependencyManager dm;

    public VehicleFormComponentLoader(DependencyManager dm) {
        this.dm = dm;
    }

    public Node load(Vehicle vehicle) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/vehicles/VehicleForm.fxml"));
        Node node = loader.load();
        VehicleFormController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        ctrl.setVehicle(vehicle);
        return node;
    }
}
