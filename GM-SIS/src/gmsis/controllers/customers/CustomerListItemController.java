/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.customers;

import gmsis.models.customers.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class CustomerListItemController implements Initializable {
    @FXML private Label vehicles;
    @FXML private Label name;
    @FXML private Label phone;
    @FXML
    private Label customerType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
    }    

    public void setCustomer(Customer customer) {
        this.name.setText(customer.getFullName());
        this.phone.setText(customer.getPhoneNumber());
        this.customerType.setText(customer.getCustomerType().name());
        this.customerType.getStyleClass().add("label-customer-" + customer.getCustomerType().name().toLowerCase());

        int nVehicles = customer.getVehicles().size();
        this.vehicles.setText(nVehicles + " vehicle" + (nVehicles == 1 ? "" : "s"));
    }
    
}
