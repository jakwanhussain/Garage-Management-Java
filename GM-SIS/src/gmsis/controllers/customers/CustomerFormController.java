/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.customers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import gmsis.di.DependencyManager;
import gmsis.models.customers.Customer;
import gmsis.models.customers.CustomerType;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class CustomerFormController implements Initializable {

    private DependencyManager dm;

    private Customer customer;

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField postcode;
    @FXML
    private ToggleGroup customerType;
    @FXML
    private HBox customerFormRoot;
    @FXML
    private Button saveButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // All text fields are required
        Stream.of(name, phone, email, address, postcode).forEach(field -> {
            RequiredFieldValidator required = new RequiredFieldValidator();
            required.setMessage("This field is required.");
            required.setErrorStyleClass("has-error");
            field.getValidators().add(required);
        });

        // All text field trigger validation when they lose focus
        Stream.of(name, phone, email, address, postcode).forEach(field -> field.focusedProperty().addListener(onBlur(this::validateAll)));

        // Request focus on the name field on the next tick
        Platform.runLater(() -> name.requestFocus());
    }

    public void setDependencyManager(DependencyManager dm) {
        this.dm = dm;
    }

    public void setCustomer(Customer customer) {
        if (customer != null) {
            name.setText(customer.getFullName());
            phone.setText(customer.getPhoneNumber());
            email.setText(customer.getEmailAddress());
            address.setText(customer.getAddress());
            postcode.setText(customer.getPostcode());
            Toggle t = null;
            switch (customer.getCustomerType()) {
                case BUSINESS:
                    t = (Toggle) customerFormRoot.lookup(".type--business");
                    break;
                case INDIVIDUAL:
                    t = (Toggle) customerFormRoot.lookup(".type--individual");
                    break;
            }
            customerType.selectToggle(t);
        }
        this.customer = customer;
    }

    public void commitEdit(Event e) {
        if (customer == null) {
            customer = new Customer();
        }
        customer.setFullName(name.getText());
        customer.setPhoneNumber(phone.getText());
        customer.setEmailAddress(email.getText());
        customer.setAddress(address.getText());
        customer.setPostcode(postcode.getText());
        String ctype = (String) customerType.getSelectedToggle().getProperties().get("value");
        customer.setCustomerType(CustomerType.valueOf(ctype));
        try {
            dm.getCustomerRepository().save(customer);
            customerFormRoot.fireEvent(CustomerEditFinishedEvent.completed(customer));
        } catch (Exception ex) {
            Logger.getLogger(CustomerFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cancelEdit(Event e) {
        customerFormRoot.fireEvent(CustomerEditFinishedEvent.cancelled(customer));
    }

    private static ChangeListener<Boolean> onBlur(Consumer<Boolean> fn) {
        return (ObservableValue<? extends Boolean> oV, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                fn.accept(newValue);
            }
        };
    }

    private void validateAll(boolean v) {
        saveButton.setDisable(!Stream.of(name, phone, email, address, postcode).map(JFXTextField::validate).allMatch(p -> p));
    }
}
