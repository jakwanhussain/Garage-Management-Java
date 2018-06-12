/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.vehicle;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import gmsis.di.DependencyManager;
import gmsis.models.customers.Customer;
import gmsis.models.vehicles.Vehicle;
import gmsis.models.vehicles.VehicleType;
import gmsis.models.vehicles.WarrantyCompany;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by sabrinasaytac on 02/03/2017.
 */

public class VehicleFormController {

    private DependencyManager dm;
    private Vehicle vehicle;
    private WarrantyCompany warrantyCompany;


    @FXML
    private JFXComboBox<String> owner;
    @FXML
    private JFXTextField registrationNumber;
    @FXML
    private JFXTextField make;
    @FXML
    private JFXTextField model;
    @FXML
    private JFXTextField engineSize;
    @FXML
    private JFXTextField fuelType;
    @FXML
    private JFXTextField colour;
    @FXML
    private JFXDatePicker motRenewalDate;
    @FXML
    private VBox vehicleFormRoot;
    @FXML
    private Button saveVehicle;
    @FXML
    private ToggleGroup vehicleType;
    @FXML
    private JFXCheckBox warrantyStatus;
    @FXML
    private JFXTextField warrantyCompanyName;
    @FXML
    private JFXTextField warrantyCompanyAddress;
    @FXML
    private JFXDatePicker warrantyStartDate;
    @FXML
    private JFXDatePicker warrantyEndDate;
    @FXML
    private JFXRadioButton car;
    @FXML
    private JFXRadioButton truck;
    @FXML
    private JFXRadioButton van;
    @FXML
    private JFXComboBox warrantyCompanies;

    //@Override
    public void initialize(URL location, ResourceBundle resources) {
        Stream.of(registrationNumber, make, model, engineSize, fuelType, colour).forEach(field -> {
            RequiredFieldValidator required = new RequiredFieldValidator();
            required.setMessage("This field is required.");
            required.setErrorStyleClass("has-error");
            field.getValidators().add(required);
        });
    }

    public void setDependencyManager(DependencyManager dependencyManager) {
        this.dm = dependencyManager;
    }

    public void warrantySelected() {
        if (warrantyStatus.isSelected()) {
            warrantyCompanyName.setVisible(true);
            warrantyCompanyAddress.setVisible(true);
            warrantyStartDate.setVisible(true);
            warrantyEndDate.setVisible(true);
        } else {
            warrantyCompanyName.setVisible(false);
            warrantyCompanyAddress.setVisible(false);
            warrantyStartDate.setVisible(false);
            warrantyEndDate.setVisible(false);
            warrantyCompany = null;
        }
    }

    public void setVehicle(Vehicle vehicle) throws Exception {

        List<Customer> ownerList = dm.getCustomerRepository().all(Customer.class);
        owner.getItems().addAll(ownerList.stream().map(Customer::getFullName).collect(Collectors.toList()));
        List<WarrantyCompany> companyList = dm.getWarrantyCompanyRepository().all(WarrantyCompany.class);
        for (int i = 0; i < companyList.size(); i++) {
            warrantyCompanies.getItems().add(companyList.get(i).getCompanyName());
        }
        warrantyCompanies.getItems().add("New Company");

        if (vehicle != null) {

            warrantyCompany = vehicle.getWarrantyCompany();

            VehicleType t = vehicle.getVehicleType();
            car.setSelected(t == VehicleType.CAR);
            van.setSelected(t == VehicleType.VAN);
            truck.setSelected(t == VehicleType.TRUCK);
            owner.setValue(vehicle.getOwner().getFullName());
            registrationNumber.setText(vehicle.getRegistrationNumber().trim().toUpperCase(Locale.UK));
            make.setText(vehicle.getMake().trim());
            model.setText(vehicle.getModel().trim());
            engineSize.setText(String.valueOf(vehicle.getEngineSize()));
            fuelType.setText(vehicle.getFuelType().trim());
            colour.setText(vehicle.getColour().trim());
            motRenewalDate.setValue(vehicle.getMotRenewal());
            boolean hasWarranty = (warrantyCompany != null);
            warrantyStatus.setSelected(hasWarranty);
            warrantyCompanyName.setVisible(hasWarranty);
            warrantyCompanyAddress.setVisible(hasWarranty);
            warrantyStartDate.setVisible(hasWarranty);
            warrantyEndDate.setVisible(hasWarranty);
            warrantyCompanies.setValue((warrantyCompany == null) ? null : vehicle.getWarrantyCompany().getCompanyName());
            warrantyCompanyName.setText((warrantyCompany == null) ? null : warrantyCompany.getCompanyName());
            warrantyCompanyAddress.setText((warrantyCompany == null) ? null : warrantyCompany.getCompanyAddress());
            warrantyStartDate.setValue((warrantyCompany == null) ? null : vehicle.getWarrantyStartDate());
            warrantyEndDate.setValue((warrantyCompany == null) ? null : vehicle.getWarrantyEndDate());
        }
        this.vehicle = vehicle;
    }

    public void saveEdit() {
        if (vehicle == null) {
            vehicle = new Vehicle();
        }

        if (car.isSelected()) {
            vehicle.setVehicleType(VehicleType.CAR);
        } else if (van.isSelected()) {
            vehicle.setVehicleType(VehicleType.VAN);
        } else if (truck.isSelected()) {
            vehicle.setVehicleType(VehicleType.TRUCK);
        }
        vehicle.setOwner(dm.getCustomerRepository().findByName(owner.getValue()).get(0));
        vehicle.setRegistrationNumber(registrationNumber.getText());
        vehicle.setMake(make.getText());
        vehicle.setModel(model.getText());
        vehicle.setEngineSize(Double.parseDouble(engineSize.getText()));
        vehicle.setFuelType(fuelType.getText());
        vehicle.setColour(colour.getText());
        vehicle.setMotRenewal(motRenewalDate.getValue());
        boolean warrantyChecked = warrantyStatus.isSelected();
        vehicle.setLastService(LocalDate.now());
        if (warrantyChecked) {
            if (warrantyCompanies.getValue().equals("New Company")) {
                vehicle.setWarrantyCompany(new WarrantyCompany(warrantyCompanyName.getText(), warrantyCompanyAddress.getText()));
            } else {
                vehicle.setWarrantyCompany(dm.getWarrantyCompanyRepository().findByName((String) warrantyCompanies.getValue()).get(0));
            }
            vehicle.setWarrantyStartDate((!warrantyChecked) ? null : warrantyStartDate.getValue());
            vehicle.setWarrantyEndDate((!warrantyChecked) ? null : warrantyEndDate.getValue());
        }

        try {
            dm.getVehicleRepository().save(vehicle);
            vehicleFormRoot.fireEvent(VehicleEditFinishedEvent.completed(vehicle));
        } catch (Exception ex) {
            Logger.getLogger(VehicleFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cancelEdit() {

        vehicleFormRoot.fireEvent(VehicleEditFinishedEvent.cancelled(vehicle));
    }

}
