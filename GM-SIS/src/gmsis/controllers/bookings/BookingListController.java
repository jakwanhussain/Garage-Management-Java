/*
 *  This file is part of GM-SIS by #Team10
 */
package gmsis.controllers.bookings;

import com.jfoenix.controls.JFXButton;
import gmsis.di.DependencyManager;
import gmsis.models.authentication.User;
import gmsis.models.bookings.Booking;
import gmsis.models.bookings.Mechanic;
import gmsis.models.customers.Customer;
import gmsis.models.vehicles.Vehicle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.cell.TextFieldTableCell;
import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author jakwan
 *
 */
public class BookingListController implements Initializable {

    @FXML
    private TableColumn<Booking, Long> idCol;

    @FXML
    private TableColumn<Booking, String> nameCol;

    @FXML
    private TableColumn<Booking, String> regCol;

    @FXML
    private TableColumn<Booking, Integer> durationCol;

    @FXML
    private TableColumn<Booking, String> typeCol;

    @FXML
    private TableColumn<Booking, String> mechanicCol;

    @FXML
    private TableColumn<Booking, LocalDate> dateCol;

    @FXML
    private TableColumn<Booking, LocalTime> timeCol;

    @FXML
    private TableColumn<Booking, Double> costCol;

    @FXML
    private TableColumn<Booking, Booking> billCol;

    @FXML
    private TableColumn<Booking, String> statusCol;

    @FXML
    private Node bookingListRoot;

    @FXML
    private TableView<Booking> bookingTable;

    @FXML
    private TableColumn<Booking, Integer> mileageCol;

    @FXML
    private TextField searchBookingsText;

    @FXML
    private ComboBox searchBy;

    @FXML
    private JFXButton edit;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton details;

    @FXML
    private JFXButton newBooking;

    @FXML
    private JFXButton completeButton;

    private String searchText;
    private DependencyManager dm;
    private Booking selectBooking = null;

    @Override
    public void initialize(URL location, ResourceBundle recources) {
        bookingListRoot.getProperties().put("controller", this);
        bookingListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);
    }

    public void setDependencyManager(DependencyManager dm) {
        this.dm = dm;
        try {
            drawTable();
        } catch (Exception ex) {
            Logger.getLogger(BookingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // switch to booking form
    @FXML
    public void newBookings(Event e) {
        bookingListRoot.fireEvent(new BookingEditRequestEvent());
    }

    // draws the table for booking
    private void drawTable() throws Exception {

        bookingTable.setEditable(true);
        try {
            List<Booking> bookingList = dm.getBookingsRepository().all(Booking.class);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(row -> {
                Customer c = row.getValue().getCustomer();
                return new SimpleObjectProperty<>(c != null ? c.getFullName() : "(No customer assigned)");
            });
            regCol.setCellValueFactory(row -> {
                Vehicle v = row.getValue().getVehicle();
                return new SimpleObjectProperty<>(v != null ? v.getRegistrationNumber() : "(No vehicle)");
            });

            mileageCol.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("mileage"));
            mileageCol.setEditable(true);
            mileageCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

            mileageCol.setOnEditCommit(e -> {
                Integer mileage = e.getNewValue();
                Booking booking = e.getRowValue();
                booking.setMileage(mileage);
                try {
                    dm.getBookingsRepository().save(booking);
                } catch (Exception ex) {
                    Logger.getLogger(BookingListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
            timeCol.setCellValueFactory(new PropertyValueFactory<>("bookingTime"));
            mechanicCol.setCellValueFactory(row -> {
                Mechanic m = row.getValue().getMechanics();
                return new SimpleObjectProperty<>(m != null ? m.getId().toString() : "(no mechanic)");
            });
            billCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue()));
            billCol.setCellFactory(col -> new TableCell<Booking, Booking>() {
                @Override
                protected void updateItem(Booking item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else if (item.getBill() != null) {
                        setText("Bill #" + item.getBill().getId());

                    } else if (item.getBill() == null) {
                        Button btn = new Button("Create Bill");
                        btn.setOnAction(e -> {

                            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                            confirm.setTitle("Create Bill?");
                            confirm.setHeaderText("Do you want to create biil? ");
                            confirm.setContentText("Would you like to create bill?");
                            ButtonType compBtn = new ButtonType("Create Bill", ButtonBar.ButtonData.OK_DONE);
                            ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                            confirm.getButtonTypes().setAll(compBtn, cancelBtn);
                            /**
                             * something is slightly wrong
                             */
                            ButtonType result = confirm.showAndWait().orElse(cancelBtn);
                            if (result == compBtn) {
                                try {
                                    selectBooking.setDone("Yes");
                                    dm.getBookingsRepository().save(selectBooking);
                                    dm.getBillRepository().getOrCreateBill(item);
                                    try {
                                        drawTable();
                                    } catch (Exception ex) {
                                        Logger.getLogger(BookingListController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not create bill:", ex);
                                }
                            }
                        });
                        setGraphic(btn);
                    } else {
                        setText("Not billable");
                    }
                }
            });
            statusCol.setCellValueFactory(new PropertyValueFactory<>("done"));
            ObservableList<Booking> booking_list = FXCollections.observableArrayList(bookingList);
            bookingTable.setItems(booking_list);

            bookingTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                if (bookingTable.getSelectionModel().getSelectedItem() != null) {
                    selectBooking = bookingTable.getSelectionModel().getSelectedItem();
                    details.setText("Show details for " + selectBooking.getId());
                }
            });

        } catch (Exception ex) {
            Logger.getLogger(BookingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchBy.getItems().clear();
        searchBy.getItems().add("Name");
        searchBy.getItems().add("Vehicle Registration");
        searchBy.getItems().add("Vehicle Manufacturer");
    }

    private void toggleRequiresSelection(Node node) {
        node.setDisable(selectBooking == null);
        node.setVisible(selectBooking != null);
    }

    //show the table
    public void shown() throws Exception {
        drawTable();
        bookingTable.getSelectionModel().select(null);
    }

    //search by reg, name and vehicle manufacturer
    @FXML
    void searchBooking(ActionEvent event) {
        String selectedSearch = (String) searchBy.getSelectionModel().getSelectedItem();
        if (selectedSearch == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Name, Registration or Manufacturer").showAndWait();
            return;
        }
        List<Booking> searchedBooking = null;
        searchText = searchBookingsText.getText();
        if (selectedSearch.equals("Name")) {
            searchedBooking = dm.getBookingsRepository().findByName(searchText);
        } else if (selectedSearch.equals("Vehicle Registration")) {
            searchedBooking = dm.getBookingsRepository().findByRegistration(searchText);
        } else if (selectedSearch.equals("Vehicle Manufacturer")) {
            searchedBooking = dm.getBookingsRepository().findByManufacturer(searchText);
        }
        ObservableList<Booking> bookingSearch = FXCollections.observableArrayList(searchedBooking);
        bookingTable.setItems(bookingSearch);
    }

    @FXML
    void clearSearch(ActionEvent event) {
        try {

            searchBookingsText.clear();
            drawTable();
        } catch (Exception ex) {
            Logger.getLogger(BookingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void makeDelete(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.WARNING);
        confirm.setTitle("Delete booking");
        confirm.setHeaderText("Do you want to delete  " + selectBooking.getCustomer().getFullName() + "'s booking?");
        confirm.setContentText("Are you sure you wish to delete this booking?");

        ButtonType deleteButton = new ButtonType("Delete " + selectBooking.getCustomer().getFullName(), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirm.getButtonTypes().setAll(cancelButton, deleteButton);

        ButtonType result = confirm.showAndWait().orElse(cancelButton);
        if (result == deleteButton) {
            try {
                try{
                    for(int i = 0; i<selectBooking.getPart().size();i++)
                    {   dm.getPartsItemRepository().delete(selectBooking.getPart().get(i));
                    }
                }
                catch(IndexOutOfBoundsException a){
                    JOptionPane.showMessageDialog(null,"Caught");
                }                
                dm.getBookingsRepository().delete(selectBooking);
                selectBooking = null;
                bookingListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);
                drawTable();
                bookingTable.getSelectionModel().select(null);
            } catch (Exception Ex) {
                Logger.getLogger(BookingListController.class.getName()).log(Level.SEVERE, null, Ex);
            }
        }
    }

    public void makeEdit(ActionEvent e) {
        this.bookingTable.fireEvent(new BookingEditRequestEvent(selectBooking));
    }

    public void setComplete(ActionEvent e) {
        if (selectBooking.getDone().equals("No")) {
            Alert complete = new Alert(Alert.AlertType.CONFIRMATION);
            complete.setTitle("Complete Status");
            complete.setHeaderText("Booking " + selectBooking.getCustomer().getFullName() + "");
            complete.setContentText("Are you sure you want to set this booking to completed?");

            ButtonType confirmBtn = new ButtonType("Complete ", ButtonBar.ButtonData.OK_DONE);
            ButtonType closeBtn = new ButtonType("Close ", ButtonBar.ButtonData.CANCEL_CLOSE);
            complete.getButtonTypes().setAll(closeBtn, confirmBtn);

            ButtonType result = complete.showAndWait().orElse(closeBtn);
            if (result == confirmBtn) {
                selectBooking.setDone("Yes");
                try {
                    dm.getBookingsRepository().save(selectBooking);
                    drawTable();
                } catch (Exception ex) {
                    Logger.getLogger(BookingListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            Alert complete = new Alert(Alert.AlertType.CONFIRMATION);
            complete.setTitle("Booking Status");
            complete.setHeaderText("Booking " + selectBooking.getCustomer().getFullName() + "");
            complete.setContentText("Are you sure you want to set this booking to No?");

            ButtonType confirmBtn = new ButtonType("Ok ", ButtonBar.ButtonData.OK_DONE);
            ButtonType closeBtn = new ButtonType("Close ", ButtonBar.ButtonData.CANCEL_CLOSE);
            complete.getButtonTypes().setAll(closeBtn, confirmBtn);

            ButtonType result = complete.showAndWait().orElse(closeBtn);
            if (result == confirmBtn) {
                selectBooking.setDone("No");
                try {
                    dm.getBookingsRepository().save(selectBooking);
                    drawTable();
                } catch (Exception ex) {
                    Logger.getLogger(BookingListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    public void getHelp() {
        Alert moreDetails = new Alert(Alert.AlertType.CONFIRMATION);
        moreDetails.setTitle("Help");
        moreDetails.setHeaderText("Help");
        moreDetails.setContentText("EDIT MILEAGE:"
                + "\n-Double click on the mileage cell and then keyboard enter"
                + "\n-Future booking mileage can be also edited from edit"
                + "\n\n SEARCH BY"
                + "\n-Select the 'Select' combo-box and chose option"
                + "\n-Enter your option in the text field and click 'Search Booking"
                + "\n\nCLEAR SEARCH"
                + "\n-Click this to reset the list"
                + "\n\n SET BOOKING TO COMPLETE"
                + "\n-Select the booking from list and then press 'Complete Booking'"
                + "\n-Also by selecting  booking from list and then press'Create Bill'"
                + "\n\n SHOW DETAILS"
                + "\n-Fore more details select the booking from list and "
                + "\n-then press 'Show Details'"
                + "\n\n CREATE BILL"
                + "\n-When a booking is complete you can simply create bill"
                + "\nby pressing 'Create Bill' button"
                + "\n\n EDIT"
                + "\n-Select a booking from the list and press 'Edit'"
                + "\n\n CREATE NEW BOOKING"
                + "\n-Click it to create a new booking"
        );
        ButtonType Btn = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        moreDetails.getButtonTypes().setAll(Btn);
        moreDetails.show();
    }

    @FXML
    void showDetails(ActionEvent event) throws Exception {
        String parts = "";
        String cost = "";
        if (selectBooking.getPart() != null) {
            for (int i = 0; i < selectBooking.getPart().size(); i++) {
                parts = parts + selectBooking.getPart().get(i).getSerialNumber() + ", ";
                cost = cost + selectBooking.getPart().get(i).getPartsModel().getCost() + ", ";
            }
        }
        if (parts.equals("")) {
            parts = "None";
            cost = "0";
        }

        if (selectBooking != null) {
            Alert moreDetails = new Alert(Alert.AlertType.CONFIRMATION);
            moreDetails.setTitle("Booking Details");
            moreDetails.setHeaderText("Booking " + selectBooking.getId() + ": " + selectBooking.getCustomer().getFullName() + "");
            moreDetails.setContentText("Customer: " + selectBooking.getCustomer().getFullName() + "\n"
                    + "Car Make: " + selectBooking.getVehicle().getMake() + "\n"
                    + "Car Model: " + selectBooking.getVehicle().getModel() + "\n"
                    + "Booking Duration(HH:MM): " + selectBooking.getDuration() + "\n"
                    + "Details: " + selectBooking.getDetail() + "\n"
                    + "Booking Complete Status: " + selectBooking.getDone() + "\n"
                    + "Parts: " + parts +"\n"
                    + "Parts Costs: £" + cost
            );
            ButtonType closeBtn = new ButtonType("Close ", ButtonBar.ButtonData.CANCEL_CLOSE);
            moreDetails.getButtonTypes().setAll(closeBtn);
            moreDetails.show();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a booking");
        }
    }
}
