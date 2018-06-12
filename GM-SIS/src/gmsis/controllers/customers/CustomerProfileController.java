/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.customers;

import gmsis.di.DependencyManager;
import gmsis.models.bookings.Booking;
import gmsis.models.customers.Customer;
import gmsis.models.customers.InvoiceStatus;
import gmsis.models.vehicles.Vehicle;
import gmsis.ui.customers.BillListItemComponentLoader;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by filip on 21/02/17.
 */
public class CustomerProfileController implements Initializable {
    static class VehicleListItemModel {
        private Vehicle v;

        public VehicleListItemModel(Vehicle v) {
            this.v = v;
        }

        public String toString() {
            return String.format("%s: %s %s", v.getRegistrationNumber(), v.getMake(), v.getModel());
        }
    }

    static class BookingListItemModel {
        private Booking b;

        public BookingListItemModel(Booking b) {
            this.b = b;
        }

        public String toString() {
            return String.format("%s %s: %s", b.getBookingDate(), b.getBookingTime(), b.getVehicle().getRegistrationNumber());
        }
    }

    public Label fullName;
    public Label phone;
    public ListView<VehicleListItemModel> vehicleList;
    public ListView<BookingListItemModel> bookingList;
    public ListView<Node> billList;
    private DependencyManager dm;
    private Customer customer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        billList.addEventHandler(SettleBillEvent.SETTLE, this::settleBill);
    }

    private void settleBill(SettleBillEvent e) {
        e.getBill().setStatus(InvoiceStatus.PAID);
        try {
            dm.getBillRepository().save(e.getBill());
            update();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not settle bill:", ex);

        }
    }

    public void setDependencyManager(DependencyManager dm) {
        this.dm = dm;
    }

    private void update() {
        fullName.setText(customer.getFullName());
        phone.setText(customer.getPhoneNumber());

        vehicleList.getItems().clear();
        bookingList.getItems().clear();
        billList.getItems().clear();

        customer.getVehicles().forEach(v -> vehicleList.getItems().add(new VehicleListItemModel(v)));
        customer.getBookings().forEach(b -> bookingList.getItems().add(new BookingListItemModel(b)));
        customer.getBills().forEach(b -> {
            try {
                billList.getItems().add(BillListItemComponentLoader.load(b));
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not display bill:", e);

            }
        });
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        update();
    }

    public void editCustomer(ActionEvent actionEvent) {
        vehicleList.fireEvent(new CustomerEditRequestEvent(customer));
    }

    public void onBackButtonPressed(ActionEvent actionEvent) {
        vehicleList.fireEvent(CustomerEditFinishedEvent.cancelled(customer));
    }
}
