/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.ui.customers;

import gmsis.controllers.customers.CustomerProfileController;
import gmsis.di.DependencyManager;
import gmsis.models.customers.Customer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public class CustomerProfileComponentLoader {
    private DependencyManager dm;

    public CustomerProfileComponentLoader(DependencyManager instance) {
        this.dm = instance;
    }

    public Node load(Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/customers/CustomerProfile.fxml"));
        Node node = loader.load();
        CustomerProfileController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        ctrl.setCustomer(customer);
        return node;
    }

}
