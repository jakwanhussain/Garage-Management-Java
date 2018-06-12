/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.parts;

import gmsis.di.DependencyManager;
import gmsis.models.bookings.Booking;
import gmsis.models.customers.Customer;
import gmsis.models.parts.PartsItem;
import gmsis.models.parts.PartsModel;
import gmsis.models.vehicles.Vehicle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Abdullah
 */
public class PartsController implements Initializable {

    private DependencyManager dm;

    private PartsItem selectedItem;

    @FXML
    private TextField customerSearch, regSearch, addSN, addSN1, searchParts, idPM, namePM, desPM, costPM, newSerial;
    @FXML
    private ComboBox mlist, addBID;

    @FXML
    private TableView<PartsItem> Available_DisplayTable;
    @FXML
    private TableColumn<PartsItem, String> AParts_Serial, AParts_Description, AParts_Name;
    @FXML
    private TableColumn<PartsItem, Double> AParts_Cost;
    @FXML
    private TableColumn<PartsItem, Integer> AParts_ID;
    @FXML
    private TableColumn<PartsItem, LocalDate> AParts_Add;

    @FXML
    private TableView<PartsItem> SearchC_DisplayTable;
    @FXML
    private TableColumn<PartsItem, String> SPartsC_Serial, SPartsC_Customer, SPartsC_Name, SPartsC_Reg, SPartsC_Add, SPartsC_Post, SPartsC_Mail, SPartsC_Phone, SPartsC_Type;
    @FXML
    private TableColumn<PartsItem, Long> SPartsC_Book, SPartsR_Book;

    @FXML
    private TableView<PartsItem> SearchR_DisplayTable;
    @FXML
    private TableColumn<PartsItem, String> SPartsR_Serial, SPartsR_Customer, SPartsR_Name, SPartsR_Reg, SPartsR_Make, SPartsR_Model, SPartsR_Fuel, SPartsR_WC;
    @FXML
    private TableColumn<PartsItem, Double> SPartsR_Size;
    @FXML
    private TableColumn<PartsItem, LocalDate> SPartsR_LS, SPartsR_MOT;
    @FXML
    private TableColumn<PartsItem, Long> SPartsR_ID;
    @FXML
    private TableColumn<PartsItem, Integer> SPartsR_Mile;

    @FXML
    private TableView<PartsItem> Repair_DisplayTable;
    @FXML
    private TableColumn<PartsItem, String> RepParts_Serial, RepParts_Reg, RepParts_Name, RepParts_Cus, RepParts_SPC, RepParts_Phone, RepParts_Add, RepParts_Fin;
    @FXML
    private TableColumn<PartsItem, LocalDate> RepParts_IO, RepParts_WE;
    @FXML
    private TableColumn<PartsItem, Long> RepParts_ID;

    @FXML
    private TableView<PartsItem> DRepair_DisplayTable;
    @FXML
    private TableColumn<PartsItem, String> DRepParts_Serial, DRepParts_Reg, DRepParts_Name, DRepParts_Cus, DRepParts_Phone;
    @FXML
    private TableColumn<PartsItem, Double> DRepParts_Dur;
    @FXML
    private TableColumn<PartsItem, LocalDate> DRepParts_IO, DRepParts_WE, DRepParts_Add;
    @FXML
    private TableColumn<PartsItem, Long> DRepParts_ID, DRepParts_Fin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setDependencyManager(DependencyManager dm) {
        this.dm = dm;
        updateDR();
        updatePM();
        String x = "";
        try {
            drawDRTable();
            drawATable();
            drawRepTable();
            searchCustomerui();
            searchRegui();

        } catch (Exception ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updatePM() {
        List<String> aint = new ArrayList<String>();
        List<PartsModel> book = dm.getPartsRepository().allModels();
        for (int i = 0; i < book.size(); i++) {
            aint.add(book.get(i).getName());
        }
        mlist.getItems().clear();
        mlist.getItems().addAll(
                aint.toArray()
        );
    }

    public void updateDR() {
        addBID.getItems().clear();
        List<Booking> booking = null;
        try {
            booking = dm.getBookingsRepository().getNotCompleted();
            for (int i = 0; i < booking.size(); i++) {
                addBID.getItems().addAll(booking.get(i).getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void data(MouseEvent e) {
        if (e.getClickCount() == 1) {
            String search = Available_DisplayTable.getSelectionModel().getSelectedItem().getSerialNumber();
            selectedItem = dm.getPartsItemRepository().getAvailablePartsItem(search).get(0);
        }
    }

    @FXML
    public void editItem(ActionEvent e) {
        try {
            PartsItem a = selectedItem;
            if (selectedItem.getSerialNumber().toLowerCase().equals(newSerial.getText().toLowerCase())) {
                new Alert(Alert.AlertType.ERROR, "Serial's are the same").showAndWait();
                return;
            } 
            else if(newSerial.getText().length()!=6){
                new Alert(Alert.AlertType.ERROR, "New serial is not 6 digits").showAndWait();
                return;
            }
                    else {
                try {
                    if (!newSerial.getText().equals("")) {
                        dm.getPartsItemRepository().delete(selectedItem);
                        a.setSerialNumber(newSerial.getText().toUpperCase());
                        dm.getPartsItemRepository().save(a);
                        selectedItem = null;
                        drawATable();
                        newSerial.setText("");
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Serial field cannot be blank").showAndWait();
                        return;
                    }
                } catch (Exception ex) {
                    Logger.getLogger(PartsItem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (NullPointerException al) {
            new Alert(Alert.AlertType.ERROR, "Select a part from table").showAndWait();
            return;
        }
    }

    @FXML
    public void deleteItem(ActionEvent e) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete this part?");
        try {
            confirm.setHeaderText("Do you want to delete " + selectedItem.getPartsModel().getName() + "\nSN: " + selectedItem.getSerialNumber() + "?");
            ButtonType deleteBtn = new ButtonType("Delete " + selectedItem.getPartsModel().getName(), ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            confirm.getButtonTypes().setAll(cancelBtn, deleteBtn);

            ButtonType result = confirm.showAndWait().orElse(cancelBtn);
            if (result == deleteBtn) {
                try {
                    dm.getPartsItemRepository().delete(selectedItem);
                    selectedItem = null;
                    drawATable();
                } catch (Exception ex) {
                    Logger.getLogger(PartsItem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (NullPointerException t) {
            new Alert(Alert.AlertType.ERROR, "Select an item").showAndWait();
            return;
        }
    }

    @FXML
    public void submitPM(ActionEvent event) throws Exception {
        String x = "";
        boolean exists = false;
        List<PartsModel> all = dm.getPartsRepository().allModels();
        String name = namePM.getText();
        String des = desPM.getText();
        if (!name.equals("") || !des.equals("")) {
            try {
                int id = Integer.parseInt(idPM.getText());
                double cost = Double.parseDouble(costPM.getText());

                List<PartsItem> pi = null;
                for (int i = 0; i < all.size(); i++) {
                    if (id == all.get(i).getId() || (name.toLowerCase()).equals((all.get(i).getName().toLowerCase()))) {
                        exists = true;
                        x = JOptionPane.showInputDialog(null, "Do you want to edit model: " + all.get(i).getName() + "?\nChanges will apply to all " + all.get(i).getName());
                        break;
                    }
                }
                if (!exists || x.toLowerCase().equals("yes") || x.toLowerCase().equals("y")) {
                    PartsModel pm = new PartsModel(id, des, name, cost, pi);
                    dm.getPartsRepository().save(pm);
                    updatePM();
                    idPM.setText("");
                    namePM.setText("");
                    costPM.setText("");
                    desPM.setText("");
                    try {
                        drawATable();
                    } catch (Exception ex) {
                        Logger.getLogger(PartsItem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Choose unique properties for new model").showAndWait();
                    return;
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Please use numbers for cost and ID").showAndWait();
                return;
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please fill in the blanks").showAndWait();
            return;

        }
    }

    @FXML
    public void submitAddui(ActionEvent event) throws Exception //MAKE ADDING TO VEHICLES ORIENTATED AROUND SPR ID's
    {
        PartsItem pi = null;
        Vehicle veh = null;
        Booking book = null;
        Customer cus = null;
        String sn = addSN.getText();
        Long bookingid = (Long) addBID.getValue();
        if (!"".equals(sn) && !"".equals(addBID.getValue())) {
            try {
                pi = dm.getPartsItemRepository().getAvailablePartsItem(sn).get(0);
            } catch (IndexOutOfBoundsException e) {
                new Alert(Alert.AlertType.ERROR, "Part is not available or in the System").showAndWait();
                return;
            }
            book = dm.getBookingsRepository().findById(bookingid);
            cus = book.getCustomer();
            veh = book.getVehicle();
            try {
                pi.setVehicle(veh);
                pi.setBookings(book);
                pi.setCustomer(cus);
                pi.setAvailable(false);
                pi.setInstalledOn(book.getBookingDate());
                pi.setWarrantyEnd(book.getBookingDate().plusYears(1));
                pi.setRepair(null);
                List<PartsItem> p = book.getPart();
                p.add(pi);
                book.setPart(p);
                dm.getBookingsRepository().save(book);
                dm.getPartsItemRepository().save(pi);
                drawATable();
            } catch (NullPointerException e) {

                new Alert(Alert.AlertType.ERROR, "Select a booking!").showAndWait();
                return;
            }
            addSN.setText("");
            addBID.setValue(null);
            searchCustomerui();
            searchRegui();
            drawDRTable();
        } else {

            new Alert(Alert.AlertType.ERROR, "Submit a Serial Number and Booking ID").showAndWait();
            return;
        }
    }

    @FXML
    public void submitAPI(ActionEvent a) throws Exception {
        String x = searchParts.getText();
        List<PartsItem> avaiI = dm.getPartsItemRepository().getByName(x);
        AParts_Serial.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getSerialNumber()));
        AParts_Name.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getName()));
        AParts_Cost.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getCost()));
        AParts_Description.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getDes()));
        AParts_ID.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getId()));
        AParts_Add.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getAdded()));
        ObservableList<PartsItem> avai1 = FXCollections.observableArrayList(avaiI);
        Available_DisplayTable.setItems(avai1);
        searchParts.setText("");
    }

    @FXML
    public void drawDRTable() throws Exception {
        try {
            List<PartsItem> avaiI = dm.getPartsItemRepository().currentDR();
            DRepParts_Serial.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getSerialNumber()));
            DRepParts_ID.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getBookings().getId()));
            DRepParts_Reg.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getVehicle().getRegistrationNumber()));
            DRepParts_Cus.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getFullName()));
            DRepParts_Name.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getName()));
            DRepParts_IO.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getInstalledOn()));
            DRepParts_WE.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getWarrantyEnd()));
            DRepParts_Dur.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getBookings().getDuration()));
            DRepParts_Phone.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getBookings().getDone()));
            DRepParts_Add.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getBookings().getBookingDate()));
            DRepParts_Fin.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getBookings().getMechanics().getId()));
            ObservableList<PartsItem> avai1 = FXCollections.observableArrayList(avaiI);
            DRepair_DisplayTable.setItems(avai1);
        } catch (Exception ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void addPI(ActionEvent a) throws Exception {
        String sn = addSN1.getText();
        if (sn.length() == 6) {
            try {
                PartsItem aoa = dm.getPartsItemRepository().getPartItem(sn);
                new Alert(Alert.AlertType.ERROR, "Part Serial Exists").showAndWait();
            } catch (IndexOutOfBoundsException ex11) {
                String name = (String) mlist.getValue();
                if (!"".equals(sn) && !"".equals(name)) {
                    LocalDate add = LocalDate.now();
                    PartsModel pm = dm.getPartsRepository().getPModel(name);
                    PartsItem pi = new PartsItem(sn, pm, true, null, null, add, null, null, null, null);
                    pm.addItem(pi);
                    dm.getPartsItemRepository().save(pi);
                    dm.getPartsRepository().save(pm);
                    drawATable();
                    addSN1.setText(null);
                    mlist.setValue("Select Model");
                } else {

                    new Alert(Alert.AlertType.ERROR, "Please enter a Serial Number and a Parts Model").showAndWait();
                    return;
                }
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Item serial must be 6 characters long").showAndWait();
            return;
        }
    }

    @FXML
    public void displayParts(MouseEvent e) {
        if (e.getClickCount() == 2) {

        }
    }

    public void searchCustomerui() throws Exception {
        String search = customerSearch.getText();
        try {
            List<PartsItem> sPI = dm.getPartsItemRepository().searchCustomerui(search);
            SPartsC_Customer.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getFullName()));
            SPartsC_Name.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getName()));
            SPartsC_Serial.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getSerialNumber()));
            SPartsC_Reg.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getVehicle().getRegistrationNumber()));
            SPartsC_Add.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getAddress()));
            SPartsC_Mail.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getEmailAddress()));
            SPartsC_Post.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getPostcode()));
            SPartsC_Phone.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getPhoneNumber()));
            SPartsC_Type.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getCustomerType().toString()));
            ObservableList<PartsItem> sPI1 = FXCollections.observableArrayList(sPI);

            SearchC_DisplayTable.setItems(sPI1);
            customerSearch.setText("");
        } catch (Exception ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML//CHANGE THIS TABLE
    public void searchRegui() throws Exception {
        String search = regSearch.getText();
        try {
            List<PartsItem> sPI = dm.getPartsItemRepository().searchRegistrationui(search);
            SPartsR_Customer.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getFullName()));
            SPartsR_Name.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getName()));
            SPartsR_Serial.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getSerialNumber()));
            SPartsR_Reg.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getVehicle().getRegistrationNumber()));
            SPartsR_Mile.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getVehicle().getMileage()));
            SPartsR_Make.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getVehicle().getMake()));
            SPartsR_Model.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getVehicle().getModel()));
            SPartsR_Fuel.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getVehicle().getFuelType()));
            SPartsR_Size.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getVehicle().getEngineSize()));
            SPartsR_LS.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getVehicle().getLastService()));
            SPartsR_MOT.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getVehicle().getLastService()));
            ObservableList<PartsItem> sPI1 = FXCollections.observableArrayList(sPI);
            SearchR_DisplayTable.setItems(sPI1);
            regSearch.setText("");
        } catch (Exception ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void drawATable() throws Exception {
        try {
            List<PartsItem> avaiI = dm.getPartsItemRepository().getAvailable();
            AParts_Serial.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getSerialNumber()));
            AParts_Name.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getName()));
            AParts_Cost.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getCost()));
            AParts_Description.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getDes()));
            AParts_ID.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getId()));
            AParts_Add.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getAdded()));
            ObservableList<PartsItem> avai1 = FXCollections.observableArrayList(avaiI);
            Available_DisplayTable.setItems(avai1);
        } catch (Exception ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void drawRepTable() throws Exception {

        try {
            try{
            List<PartsItem> avaiI = dm.getPartsItemRepository().inRepairs();
            RepParts_Serial.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getSerialNumber()));
            RepParts_ID.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getRepair().getBooking().getId()));
            RepParts_Reg.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getVehicle().getRegistrationNumber()));
            RepParts_Cus.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getFullName()));
            RepParts_Name.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartsModel().getName()));
            RepParts_IO.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getInstalledOn()));
            RepParts_WE.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getWarrantyEnd()));
            RepParts_SPC.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getRepair().getSPC().getName()));
            RepParts_Phone.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getRepair().getSPC().getPhoneNumber()));
            RepParts_Add.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getRepair().getSPC().getAddress()));
            RepParts_Fin.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getRepair().getCompleted()));
            ObservableList<PartsItem> avai1 = FXCollections.observableArrayList(avaiI);
            Repair_DisplayTable.setItems(avai1);
            }
            catch(NullPointerException as){
                System.out.println("caught");
            }
        } catch (Exception ex) {
            Logger.getLogger(PartsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void getHelp() {
        Alert moreDetails = new Alert(Alert.AlertType.CONFIRMATION);
        moreDetails.setTitle("Help");
        moreDetails.setHeaderText("Help");
        moreDetails.setContentText("ADD NEW PART ITEM"
                + "\n-Select from existing models"
                + "\n-Serial numbers must be 6 digits long"
                + "\n\nADD DIAGNOSTIC REPAIR PART"
                + "\n-Select from Diagnostic Repiar id"
                + "\n-Enter an available serial number from table"
                + "\n\nADD PART MODEL"
                + "\n-Enter new id, name, description and cost"
                + "\n\nEDIT PART MODEL"
                + "\n-Enter id of part to edit details of"
                + "\n-Enter new name description and cost"
                + "\n\nEDIT SELECTED PART SERIAL"
                + "\n-Select a row from the available table"
                + "\n-Enter new 6 digit serial number"
                + "\n\nDELETE PART ITEM"
                + "\n-Select the part item to delete"
                + "\n-Click delete button"
        );
        ButtonType Btn = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
        moreDetails.getButtonTypes().setAll(Btn);
        moreDetails.show();
    }
}
