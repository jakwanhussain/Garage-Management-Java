/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.ui.vehicles;

import gmsis.controllers.vehicle.VehicleEditFinishedEvent;
import gmsis.controllers.vehicle.VehicleEditRequestEvent;
import gmsis.controllers.vehicle.VehicleListController;
import gmsis.di.DependencyManager;
import gmsis.ui.GmsisApp;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sabrinasaytac
 */

public class VehiclesUI {

    private StackPane root;
    private Node vehicleListScreen;
    private Node vehicleFormScreen;

    private DependencyManager dm;

    public VehiclesUI(DependencyManager dm) throws IOException {

        this.dm = dm;
        root = new StackPane();
        vehicleListScreen = new VehicleListComponentLoader(dm).load();
        root.getChildren().add(vehicleListScreen);
        vehicleFormScreen = null;

        root.addEventHandler(VehicleEditRequestEvent.VEHICLE_EDIT_REQUEST_ANY, event -> {
            try {
                this.vehicleFormScreen = new VehicleFormComponentLoader(dm).load(event.getVehicle());
            } catch (Exception exception) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, exception);
                return;
            }
            root.getChildren().remove(0);
            root.getChildren().add(vehicleFormScreen);
        });

        root.addEventHandler(VehicleEditFinishedEvent.VEHICLE_EDIT_FINISHED_ANY, event -> {

            root.getChildren().remove(0);
            root.getChildren().add(vehicleListScreen);
            try {
                ((VehicleListController) vehicleListScreen.getProperties().get("controller")).shown();

            } catch (Exception exception) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, exception);
            }
        });

        Platform.runLater(() -> {
            root.prefWidthProperty().bind(((Pane) root.getParent()).widthProperty());
            root.prefHeightProperty().bind(((Pane) root.getParent()).heightProperty());
        });

    }

    public Node getRoot() {
        return root;
    }

}
