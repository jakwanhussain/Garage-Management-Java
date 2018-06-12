/*  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.bookings;
import com.jfoenix.controls.*;
import gmsis.controllers.customers.CustomerBookingEvent;
import gmsis.controllers.specialistRepairs.SPCBookingViewRequestEvent;
import gmsis.di.DependencyManager;
import gmsis.models.bookings.Booking;
import gmsis.models.bookings.Mechanic;
import gmsis.models.customers.Customer;
import gmsis.models.vehicles.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author jakwan
 */
public class BookingsController implements Initializable {

    @FXML
    private Node BookingRoot;
    @FXML
    private ComboBox customerName;
    @FXML
    private ComboBox customerVehicle;
    @FXML
    private JFXTextField mileage;
    @FXML
    private JFXDatePicker bookingDate;
    @FXML
    private ComboBox bookingTime;
    @FXML
    private JFXTextField duration;
    @FXML
    private ComboBox mechanicId;
    @FXML
    private JFXCheckBox Addspecialist;
    @FXML
    private JFXButton submit;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXButton bookingList;
    @FXML
    private JFXListView<Booking> list;
    @FXML
    private TextArea note;

    @FXML
    private JFXButton showDetails;
    CustomerBookingEvent selecte;
    private DependencyManager dm;
    private Booking bookings;
    private LocalDate localDate;
    private LocalTime localTime;
    private List<Customer> customer;
    private Customer cus;
    private List<Vehicle> vehicle;
    private List<Mechanic> mechanic;

    public void setDependencyManager(DependencyManager dm) {

        this.dm = dm;
        drawBox();
    }

    public void drawBox() {
        List<Customer> customers = null;
        try {
            customers = dm.getCustomerRepository().all(Customer.class);
            for (int i = 0; i < customers.size(); i++) {
                customerName.getItems().addAll(customers.get(i).getFullName());
            }
        } catch (Exception e) {
            Logger.getLogger(BookingsController.class.getName()).log(Level.SEVERE, null, e);
        }

        customerName.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    updateVehicleReg((String) newValue);
                }
        );

        List<Mechanic> mechanics = null;
        try {
            mechanics = dm.getMechanicsRepository().all(Mechanic.class);
            for (int i = 0; i < mechanics.size(); i++) {
                mechanicId.getItems().addAll(mechanics.get(i).getId());
            }
        } catch (Exception e) {
            Logger.getLogger(BookingsController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle recources) {

    }

    public void makeBookings(Event e) throws Exception {
        if (bookings == null) {
            bookings = new Booking();
            // booking status 
            bookings.setDone("No");
        }

        try {
            String selectedCustomer;
            selectedCustomer = (String) customerName.getSelectionModel().getSelectedItem();
            customer = dm.getCustomerRepository().findByName(selectedCustomer);
            bookings.setCustomer(customer.get(0));
        } catch (IndexOutOfBoundsException out) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer").showAndWait();
            return;
        }
        //change accordingly 
        try {
            String selectedVehicle;
            selectedVehicle = (String) customerVehicle.getSelectionModel().getSelectedItem();
            vehicle = dm.getVehicleRepository().findByRegistration(selectedVehicle);
            bookings.setVehicle(vehicle.get(0));
        } catch (IndexOutOfBoundsException out) {
            new Alert(Alert.AlertType.ERROR, "Please select a vehicle").showAndWait();
            return;
        }

        //booking mileage
        try {
            bookings.setMileage(Integer.parseInt(mileage.getText()));
        } catch (NumberFormatException num1) {
            new Alert(Alert.AlertType.ERROR, "Please mileage in numbers only").showAndWait();
            return;
        }
        //mechanic ID
        Long selectedMechanic;
        selectedMechanic = (Long) mechanicId.getSelectionModel().getSelectedItem();
        if (selectedMechanic == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Mechanic").showAndWait();
            return;
        }
        bookings.setMechanics(dm.getMechanicsRepository().get(Mechanic.class, selectedMechanic));
        //duration
        try {
            bookings.setDuration(Double.parseDouble(duration.getText()));
        } catch (NumberFormatException num) {
            new Alert(Alert.AlertType.ERROR, "Please duration in numbers only").showAndWait();
            return;
        }
        //booking cost is mechanic hourly rate times by booking duration
        bookings.setCost((Double.parseDouble(duration.getText())) * (bookings.getMechanics().getHourlyRate()));
        // booking note
        bookings.setDetail(note.getText());

        //edit the past booking
        if ((bookingDate.getValue().compareTo(LocalDate.now()) <= -1)) {
            new Alert(Alert.AlertType.ERROR, "Booking can only be made in future").showAndWait();
            return;
        } else if ((bookingTime.getValue() == null) || ((String) bookingTime.getValue()).equals("Close on Sunday")) {
            new Alert(Alert.AlertType.ERROR, "Please select a time").showAndWait();
            return;
        }
        //booking date
        bookings.setBookingDate(bookingDate.getValue());

        //booking time
        bookings.setBookingTime((String) bookingTime.getValue());

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Booking Confirmation");
        confirm.setHeaderText("Do you want to make this booking?");
        confirm.setContentText("Customer Name: " + bookings.getCustomer().getFullName());
        ButtonType confirmBtn = new ButtonType("Confirm ", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel ", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirm.getButtonTypes().setAll(cancelBtn, confirmBtn);
        ButtonType result = confirm.showAndWait().orElse(cancelBtn);

        if (result == confirmBtn) {
            if (Addspecialist.isSelected()) {
                bookings.setType("Specialist Repairs");
                dm.getBookingsRepository().save(bookings);
                BookingRoot.fireEvent(new SPCBookingViewRequestEvent(bookings));
                BookingRoot.fireEvent(BookingEditFinishedEvent.completed(bookings));
            } else {
                bookings.setType("Diagnosis & Repairs");
                dm.getBookingsRepository().save(bookings);
                BookingRoot.fireEvent(BookingEditFinishedEvent.completed(bookings));
            }
        }
    }
    // get different time depending on date
    //Garage operating hours 9-5.30 and 9-12 but last booking time is different
    //this only for upto last booking time
    public void getTime() {
        if (bookingDate.getValue() != null) {
            ObservableList<String> option1 = FXCollections.observableArrayList(
                    "09:00",
                    "09:30",
                    "10:00",
                    "10:30",
                    "11:00",
                    "11:30",
                    "12:00",
                    "12:30",
                    "13:00",
                    "13:00",
                    "13:30",
                    "14:00",
                    "14:30",
                    "15:00",
                    "15:30",
                    "16:00",
                    "16:30"
                    
            );
            ObservableList<String> option2 = FXCollections.observableArrayList(
                    "09:00",
                    "09:30",
                    "10:00",
                    "10:30",
                    "11:00"
            );
            ObservableList<String> option3 = FXCollections.observableArrayList("Garage Close");
            int a = bookingDate.getValue().getDayOfWeek().getValue();
            int b = bookingDate.getValue().getDayOfYear();
            //public holidays are 1,3,359 ,360 remain same for until 2020, rest should be updated every year
            if ((b != 1) && (b != 2) && (b != 359) && (b != 360) && (b != 104) && (b != 107) && (b != 121)
                    && (b != 149) && (b != 240)) {
                if (a == 7) {
                    bookingTime.setItems(option3);
                } else if (a == 6) {
                    bookingTime.setItems(option2);
                } else {
                    bookingTime.setItems(option1);
                }
            } else {
                bookingTime.setItems(option3);
            }
        }
    }

    //method sets customer when you change tab customer to booking
    public void setCustomer(Customer customer) throws Exception {
        this.cus = customer;
        customerName.getSelectionModel().select(customer.getFullName());
    }

    public void shown() {
        drawBox();
    }
    
    // auto populate the vehicle drop down for the customer
    public void updateVehicleReg(String value) {
        ArrayList<String> vehRN = new ArrayList<String>();
        List<Vehicle> vehicles = null;
        try {
            vehicles = dm.getVehicleRepository().findByCustomer(value);
            for (int i = 0; i < vehicles.size(); i++) {
                vehRN.add(vehicles.get(i).getRegistrationNumber());
            }
        } catch (Exception e) {
            Logger.getLogger(BookingsController.class.getName()).log(Level.SEVERE, null, e);
        }
        customerVehicle.getItems().clear();
        customerVehicle.getItems().addAll(
                vehRN.toArray()
        );
    }

    @FXML
    void makeClear(ActionEvent event) {
        BookingRoot.fireEvent(new BookingEditRequestEvent());
    }

    @FXML
    void viewBookings(Event e) {
        BookingRoot.fireEvent(BookingEditFinishedEvent.cancelled(bookings));
    }

    public void setBooking(Booking booking) {
        if (booking != null) {
            customerName.setValue(booking.getCustomer().getFullName());
            customerVehicle.setValue(booking.getVehicle().getRegistrationNumber());
            bookingTime.setValue(booking.getBookingTime());
            mileage.setText(Integer.toString(booking.getMileage()));
            duration.setText(Double.toString(booking.getDuration()));
            bookingDate.setValue(booking.getBookingDate());
            mechanicId.setValue(booking.getMechanics().getId());
            note.setText(booking.getDetail());
        }
        this.bookings = booking;
    }
}
