/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.customers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.collections.ObservableListWrapper;
import gmsis.di.DependencyManager;
import gmsis.models.customers.Customer;
import gmsis.ui.customers.CustomerListItemComponentLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class CustomerListController implements Initializable {

    private static final String BY_NAME = "Name";
    private static final String BY_REGISTRATION = "Registration Number";

    private DependencyManager dm;
    private Customer selectedCustomer = null;

    @FXML
    private JFXComboBox searchType;
    @FXML
    private JFXTextField searchInput;
    @FXML
    private JFXListView<Node> list;
    @FXML
    private VBox customerListRoot;

    private void toggleRequiresSelection(Node node) {
        node.setDisable(selectedCustomer == null);
        node.setVisible(selectedCustomer != null);
    }

    private void drawList(List<Customer> customers) throws Exception {
        list.getItems().clear();
        customerSelectionChanged(null);
        for (Customer c : customers) {
            list.getItems().add(CustomerListItemComponentLoader.load(c));
        }
    }

    public void drawList() throws Exception {
        drawList(dm.getCustomerRepository().all(Customer.class));
    }

    public void addCustomer(ActionEvent e) {
        this.list.fireEvent(new CustomerEditRequestEvent());
    }

    public void editSelectedCustomer(ActionEvent e) {
        this.list.fireEvent(new CustomerEditRequestEvent(selectedCustomer));
    }

    public void addBookingToCustomer(ActionEvent e) {
        this.list.fireEvent(CustomerBookingEvent.newBooking(selectedCustomer));
    }

    public void deleteSelectedCustomer(ActionEvent e) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete this customer?");
        confirm.setHeaderText("Do you want to delete " + selectedCustomer.getFullName() + "?");
        confirm.setContentText("All vehicles, bookings, and bills belonging to this customer will be deleted too.");

        ButtonType deleteBtn = new ButtonType("Delete " + selectedCustomer.getFullName(), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirm.getButtonTypes().setAll(cancelBtn, deleteBtn);

        ButtonType result = confirm.showAndWait().orElse(cancelBtn);
        if (result == deleteBtn) {
            try {
                dm.getCustomerRepository().delete(selectedCustomer);
                selectedCustomer = null;
                customerListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);
                drawList();
                list.getSelectionModel().select(null);
            } catch (Exception ex) {
                Logger.getLogger(CustomerListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void customerListClick(MouseEvent e) {
        Node item = this.list.getSelectionModel().getSelectedItem();
        if (item == null) {
            customerSelectionChanged(null);
        }
        Customer currentSelection = (Customer) item.getProperties().get("customer");
        if (!currentSelection.equals(selectedCustomer)) {
            customerSelectionChanged(currentSelection);
        }

        if (e.getClickCount() == 2) {
            list.fireEvent(CustomerEditRequestEvent.showProfile(currentSelection));
        }
    }

    public void customerSelectionChanged(Customer selected) {
        selectedCustomer = selected;
        customerListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerListRoot.getProperties().put("controller", this);
        customerListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);

        //noinspection unchecked
        searchType.setItems(new ObservableListWrapper<>(Arrays.asList(BY_NAME, BY_REGISTRATION)));
    }

    public void setDependencyManager(DependencyManager dm) {
        this.dm = dm;
        try {
            drawList();
        } catch (Exception ex) {
            Logger.getLogger(CustomerListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void shown() throws Exception {
        drawList();
        customerSelectionChanged(null);
        list.getSelectionModel().select(null);
    }

    public void onSearchInput(KeyEvent e) throws Exception {
        if (e.getCode() == KeyCode.ENTER) {
            String search = searchInput.getText();
            switch ((String) searchType.getSelectionModel().getSelectedItem()) {
                case BY_NAME:
                    drawList(dm.getCustomerRepository().findByName(search));
                    break;
                case BY_REGISTRATION:
                    drawList(dm.getCustomerRepository().findByRegistration(search));
                    break;
            }
        }
    }

    public void clearSearch(@SuppressWarnings("unused") ActionEvent e) throws Exception {
        searchInput.clear();
        drawList();
    }
}
