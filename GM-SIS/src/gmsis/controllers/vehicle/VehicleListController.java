/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.vehicle;

import com.jfoenix.controls.*;
import gmsis.controllers.bookings.BookingListController;
import gmsis.controllers.parts.PartsController;
import gmsis.controllers.specialistRepairs.SpecialistRepairsController;
import gmsis.di.DependencyManager;
import gmsis.models.vehicles.Vehicle;
import gmsis.models.vehicles.VehicleType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by sabrinasaytac
 */
public class VehicleListController implements Initializable {

    private DependencyManager dm;
    private Vehicle selectedVehicle = null;

    @FXML
    private JFXButton addVehicle;
    @FXML
    private JFXButton editVehicle;
    @FXML
    private JFXButton deleteVehicle;
    @FXML
    private TableView<Vehicle> vehicleTable;
    @FXML
    private TableColumn<Vehicle, VehicleType> vehicleType;
    @FXML
    private TableColumn<Vehicle, String> registrationNumber;
    @FXML
    private TableColumn<Vehicle, String> owner;
    @FXML
    private TableColumn<Vehicle, String> make;
    @FXML
    private TableColumn<Vehicle, String> model;
    @FXML
    private TableColumn<Vehicle, Double> engineSize;
    @FXML
    private TableColumn<Vehicle, String> fuelType;
    @FXML
    private TableColumn<Vehicle, String> colour;
    @FXML
    private JFXCheckBox vehicleTypeCar;
    @FXML
    private JFXCheckBox vehicleTypeVan;
    @FXML
    private JFXCheckBox vehicleTypeTruck;
    @FXML
    private JFXComboBox searchType;
    @FXML
    private JFXTextField searchInput;
    @FXML
    private JFXListView<Node> list;
    @FXML
    private VBox vehicleListRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehicleListRoot.getProperties().put("controller", this);
    }

    private void toggleRequiresSelection(Node node) {
        node.setVisible(selectedVehicle != null);
        node.setDisable(selectedVehicle == null);
    }

    public void addVehicle(@SuppressWarnings("unused") ActionEvent actionEvent) {
        try {
            this.vehicleTable.fireEvent(new VehicleEditRequestEvent());
        } catch (Exception exception) {
            Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    public void editSelectedVehicle(@SuppressWarnings("unused") ActionEvent actionEvent) {
        this.vehicleTable.fireEvent(new VehicleEditRequestEvent(selectedVehicle));
    }

    public void addBookingToVehicle(ActionEvent actionEvent) {
    }

    public void deleteSelectedVehicle(ActionEvent actionEvent) {

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete this vehicle?");
        confirm.setHeaderText("Do you want to delete vehicle " + selectedVehicle.getRegistrationNumber() + "?");
        confirm.setContentText("All bookings, parts and specialist repairs related to this vehicle will be deleted too.");
        ButtonType deleteBtn = new ButtonType("Delete " + selectedVehicle.getRegistrationNumber(), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirm.getButtonTypes().setAll(cancelBtn, deleteBtn);

        ButtonType result = confirm.showAndWait().orElse(cancelBtn);
        if (result == deleteBtn) {
            try {

                if (selectedVehicle != null) {

                    for (int i = 0; i < selectedVehicle.getBookings().size(); i++) {

                        if (selectedVehicle.getBookings() != null) {
                            try {
                                dm.getBookingsRepository().delete(selectedVehicle.getBookings().get(i));
                            } catch (Exception ex) {
                                Logger.getLogger(BookingListController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
                if (selectedVehicle.getParts() != null) {
                    for (int i = 0; i < selectedVehicle.getParts().size(); i++) {
                        try {
                            dm.getPartsItemRepository().delete(selectedVehicle.getParts().get(i));
                        } catch (Exception ex) {
                            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                if (selectedVehicle.getSpecialistRepair() != null) {
                    for (int i = 0; i < selectedVehicle.getSpecialistRepair().size(); i++) {
                        try {
                            dm.getSpecialistRepairsRepository().delete(selectedVehicle.getSpecialistRepair().get(i));
                        } catch (Exception ex) {
                            Logger.getLogger(SpecialistRepairsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                dm.getVehicleRepository().delete(selectedVehicle);

                selectedVehicle = null;
                vehicleListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);
                drawTable();
                vehicleTable.getSelectionModel().select(null);
            } catch (Exception ex) {
                Logger.getLogger(Vehicle.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void vehicleSelected(ObservableValue<? extends Vehicle> observableValue, Vehicle oldValue, Vehicle newValue) {
        if (vehicleTable.getSelectionModel().getSelectedItem() != null) {
            selectedVehicle = vehicleTable.getSelectionModel().getSelectedItem();
        }

        vehicleListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);
    }

    public void drawTable() throws Exception {

        vehicleTable.getItems().clear();
        vehicleTable.setEditable(true);
        selectedVehicle = null;

        try {

            List<Vehicle> vehicleList = dm.getVehicleRepository().all(Vehicle.class);

            vehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
            owner.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getOwner().getFullName()));
            registrationNumber.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
            make.setCellValueFactory(new PropertyValueFactory<>("make"));
            model.setCellValueFactory(new PropertyValueFactory<>("model"));
            engineSize.setCellValueFactory(new PropertyValueFactory<>("engineSize"));
            fuelType.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
            colour.setCellValueFactory(new PropertyValueFactory<>("colour"));

            ObservableList<Vehicle> vehicleObservableList = FXCollections.observableArrayList(vehicleList);
            vehicleTable.setItems(vehicleObservableList);

            vehicleTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                vehicleSelected(observable, oldValue, newValue);
            });

        } catch (Exception exception) {
            Logger.getLogger(VehicleListController.class.getName()).log(Level.SEVERE, null, exception);
        }

        searchType.getItems().clear();
        searchType.getItems().add("Registration");
        searchType.getItems().add("Manufacturer");

    }

    public void shown() throws Exception {
        drawTable();
        vehicleTable.getSelectionModel().select(null);
        vehicleListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);

    }

    public void search(@SuppressWarnings("unused") Event event) throws Exception {

        String searchText = searchInput.getText();
        List<Vehicle> searchedVehicles;

        if (searchType.getValue().equals("Registration")) {
            searchedVehicles = dm.getVehicleRepository().findByRegistration(searchText);
        } else if (searchType.getValue().equals("Manufacturer")) {
            searchedVehicles = dm.getVehicleRepository().findByManufacturer(searchText);
        } else {
            new Alert(Alert.AlertType.ERROR, "Please select a search method.").showAndWait();
            return;
        }

        ObservableList<Vehicle> vehicleSearchResults = FXCollections.observableArrayList(searchedVehicles);
        vehicleTable.getItems().clear();
        vehicleTable.setItems(vehicleSearchResults);
        selectedVehicle = null;

    }

    @FXML
    void showServiceDetails(ActionEvent event) throws Exception {
        String pastBookings = "";
        String futureBookings = "";
        String parts = "";
        LocalDate now = LocalDate.now();
        if (selectedVehicle.getSpecialistRepair() != null) {
            for (int i = 0; i < selectedVehicle.getBookings().size(); i++) {
                if (!(selectedVehicle.getBookings().get(i).getBookingDate().compareTo(now) < 0)) {
                    pastBookings = pastBookings + selectedVehicle.getBookings().get(i).getBookingDate() + ", ";
                } else {
                    futureBookings = futureBookings + selectedVehicle.getBookings().get(i).getBookingDate() + ", ";
                }
            }
        }

        if (selectedVehicle.getParts() != null) {
            for (int i = 0; i < selectedVehicle.getParts().size(); i++) {
                parts = parts + selectedVehicle.getParts().get(i).getSerialNumber() + ", ";
            }
        }

        if (selectedVehicle != null) {
            Alert moreDetails = new Alert(Alert.AlertType.CONFIRMATION);
            moreDetails.setTitle("Service Details");
            moreDetails.setHeaderText("Vehicle " + selectedVehicle.getRegistrationNumber() + " owned by " + selectedVehicle.getOwner().getFullName() + "");
            moreDetails.setContentText("Past booking dates: " + pastBookings + "\nFuture booking dates: " + futureBookings + "\nParts used: " + parts);
            ButtonType closeBtn = new ButtonType("Close ", ButtonBar.ButtonData.CANCEL_CLOSE);
            moreDetails.getButtonTypes().setAll(closeBtn);
            moreDetails.show();
        } else {
            JOptionPane.showMessageDialog(null, "No vehicle selected");
        }
    }

    @FXML
    void showWarrantyDetails(ActionEvent event) throws Exception {

        if (selectedVehicle != null) {

            Alert moreDetails = new Alert(Alert.AlertType.CONFIRMATION);
            moreDetails.setTitle("Warranty Details");
            moreDetails.setHeaderText("Vehicle " + selectedVehicle.getRegistrationNumber() + " owned by " + selectedVehicle.getOwner().getFullName() + "");
            if (selectedVehicle.getWarrantyStartDate() != null) {
                moreDetails.setContentText("Warranty company name: " + selectedVehicle.getWarrantyCompany().getCompanyName() +
                        "\nWarranty company address: " + selectedVehicle.getWarrantyCompany().getCompanyAddress() +
                        "\nWarranty start date: " + selectedVehicle.getWarrantyStartDate() + "\nWarranty end date: " +
                        selectedVehicle.getWarrantyEndDate());
            } else {
                moreDetails.setContentText("No warranty details");
            }
            ButtonType closeBtn = new ButtonType("Close ", ButtonBar.ButtonData.CANCEL_CLOSE);
            moreDetails.getButtonTypes().setAll(closeBtn);
            moreDetails.show();
        }
    }

    public void setDependencyManager(DependencyManager dm) {
        this.dm = dm;
    }

}
