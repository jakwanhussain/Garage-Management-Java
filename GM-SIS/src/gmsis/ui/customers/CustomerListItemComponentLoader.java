/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.customers;

import gmsis.controllers.customers.CustomerListItemController;
import gmsis.models.customers.Customer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 *
 * @author filip
 */
public class CustomerListItemComponentLoader {
    public static Node load(Customer customer) throws IOException {
        FXMLLoader loader = new FXMLLoader(CustomerListComponentLoader.class.getResource("/gmsis/ui/customers/CustomerListItem.fxml"));
        Node node = loader.load();
        loader.<CustomerListItemController>getController().setCustomer(customer);
        node.getProperties().put("customer", customer);
        return node;
    }
}
