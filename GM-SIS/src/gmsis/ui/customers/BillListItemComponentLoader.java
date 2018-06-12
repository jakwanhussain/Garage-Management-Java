/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.ui.customers;

import gmsis.controllers.customers.BillListItemController;
import gmsis.models.customers.Bill;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Created by filip on 21/03/17.
 */
public class BillListItemComponentLoader {
    public static Node load(Bill bill) throws IOException {
        FXMLLoader loader = new FXMLLoader(CustomerListComponentLoader.class.getResource("/gmsis/ui/customers/BillListItem.fxml"));
        Node node = loader.load();
        loader.<BillListItemController>getController().setBill(bill);
        return node;
    }
}
