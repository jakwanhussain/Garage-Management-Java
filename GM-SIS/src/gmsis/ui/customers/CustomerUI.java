/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.customers;

import gmsis.controllers.customers.CustomerEditFinishedEvent;
import gmsis.controllers.customers.CustomerEditRequestEvent;
import gmsis.controllers.customers.CustomerListController;
import gmsis.di.DependencyManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author filip
 */
public class CustomerUI {

    private StackPane root;
    private Node customerListScreen;
    private Node customerFormScreen;
    private Node customerProfileScreen;

    private DependencyManager dm;

    public CustomerUI(DependencyManager dm) throws IOException {
        this.dm = dm;

        root = new StackPane();
        customerListScreen = new CustomerListComponentLoader(dm).load();
        root.getChildren().add(customerListScreen);
        customerFormScreen = null;

        Logger.getLogger(getClass().getName()).info("Registering listener: " + CustomerEditRequestEvent.CUSTOMER_SHOW_PROFILE);
        root.addEventHandler(CustomerEditRequestEvent.CUSTOMER_SHOW_PROFILE, e -> {
            try {
                this.customerProfileScreen = new CustomerProfileComponentLoader(dm).load(e.getCustomer());
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                return;
            }
            root.getChildren().remove(0);
            root.getChildren().add(customerProfileScreen);
        });

        Logger.getLogger(getClass().getName()).info("Registering listener: " + CustomerEditRequestEvent.CUSTOMER_EDIT_REQUEST_ANY);
        root.addEventHandler(CustomerEditRequestEvent.CUSTOMER_EDIT_REQUEST_ANY, e -> {
            try {
                this.customerFormScreen = new CustomerFormComponentLoader(dm).load(e.getCustomer());
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                return;
            }
            root.getChildren().remove(0);
            root.getChildren().add(customerFormScreen);
        });

        Logger.getLogger(getClass().getName()).info("Registering listener: " + CustomerEditFinishedEvent.CUSTOMER_EDIT_FINISHED_ANY);
        root.addEventHandler(CustomerEditFinishedEvent.CUSTOMER_EDIT_FINISHED_ANY, e -> {
            root.getChildren().remove(0);
            root.getChildren().add(customerListScreen);
            try {
                ((CustomerListController) customerListScreen.getProperties().get("controller")).shown();
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        });

        Platform.runLater(() -> {
            root.prefWidthProperty().bind(((Pane) root.getParent()).widthProperty());
            root.prefHeightProperty().bind(((Pane) root.getParent()).heightProperty());
            root.getParent().addEventHandler(ActionEvent.ACTION, e -> {
                try {
                    ((CustomerListController) customerListScreen.getProperties().get("controller")).drawList();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error refreshing customer list:", ex);
                }
            });
        });
    }

    public Node get() {
        return root;
    }
}
