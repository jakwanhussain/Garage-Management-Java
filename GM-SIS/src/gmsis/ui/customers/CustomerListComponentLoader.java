/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.customers;

import gmsis.controllers.customers.CustomerListController;
import gmsis.di.DependencyManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 *
 * @author filip
 */
public class CustomerListComponentLoader {
    private DependencyManager dm;

    public CustomerListComponentLoader(DependencyManager dm) {
        this.dm = dm;
    }
    
    public Node load() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/customers/CustomerList.fxml"));
        Node node = loader.load();
        CustomerListController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        return node;
    }
}
