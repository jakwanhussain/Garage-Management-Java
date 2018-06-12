/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.specialistRepairs;

import com.jfoenix.controls.JFXButton;
import gmsis.controllers.customers.CustomerEditRequestEvent;
import gmsis.di.DependencyManager;
import gmsis.models.authentication.User;
import gmsis.models.customers.Bill;
import gmsis.models.parts.PartsItem;
import gmsis.models.specialistRepairs.SpecialistRepair;
import gmsis.models.specialistRepairs.SpecialistRepairCentre;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stephen
 */
public class SPCBookingListController implements Initializable {

    private DependencyManager dm;
    private SpecialistRepair selectedSPC = null;
    private User user;

    @FXML
    private TableView<SpecialistRepair> spcBookingTable;
    @FXML
    private TableColumn<SpecialistRepair, Long> idCol;
    @FXML 
    private TableColumn<SpecialistRepair, String> nameCol;
    @FXML
    private TableColumn<SpecialistRepair, String> phoneCol;
    @FXML
    private TableColumn<SpecialistRepair, String> spcCol;
    @FXML
    private TableColumn<SpecialistRepair, String> partVehicleCol;
    @FXML
    private TableColumn<SpecialistRepair, String> returnDateCol;
    @FXML
    private TableColumn<SpecialistRepair, Long> bookingIdCol;
    @FXML
    private TableColumn<SpecialistRepair, String> completedCol;
    @FXML
    private Node spcBookingListRoot;
    @FXML 
    private JFXButton viewSpcButton;
    @FXML 
    private TextField searchBookingsText;
    private String searchText;
    @FXML 
    private ComboBox searchBy;
    @FXML
    private ComboBox searchSelectedSPC;
    @FXML
    private JFXButton showBillButton;
    @FXML
    private JFXButton editButton;
    @FXML 
    private JFXButton deleteButton;
    
    
    public void setDependencyManager(DependencyManager dm) {
        this.dm = dm;
        try {
            drawTable();
        } catch (Exception ex) {
            Logger.getLogger(SPCListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spcBookingListRoot.getProperties().put("controller", this);
        spcBookingListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);
        }
    
    
    public void drawTable() {
        spcBookingTable.setEditable(true);
        try {
            List<SpecialistRepair> spcList =  dm.getSpecialistRepairsRepository().all(SpecialistRepair.class);
                idCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getId()));
                nameCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getFullName()));
                phoneCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getPhoneNumber()));
                spcCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getSPC().getName()));
                partVehicleCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartItem() == null ? "V: " + row.getValue().getVehicle().getModel() + ": " 
                                                                                    + row.getValue().getVehicle().getRegistrationNumber() 
                                                                                    : "P: " + row.getValue().getPartItem().getPartsModel().getName() + ": " 
                                                                                    + row.getValue().getPartItem().getSerialNumber()));
                returnDateCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getReturnDate().format(DateTimeFormatter.ISO_DATE)));         
                bookingIdCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getBooking().getId()));
                completedCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCompleted()));
                
                ObservableList<SpecialistRepair> spcOList = FXCollections.observableArrayList(spcList);
                spcBookingTable.setItems(spcOList);
                
                //Add change listener
            spcBookingTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (spcBookingTable.getSelectionModel().getSelectedItem() != null) {
                selectedSPC = spcBookingTable.getSelectionModel().getSelectedItem();
                LocalDate localDateNow = LocalDate.now();
                if(selectedSPC.getReturnDate().isBefore(localDateNow)||"Yes".equals(selectedSPC.getCompleted()))
                {
                    showBillButton.setDisable(false);
                    editButton.setDisable(true);
                    deleteButton.setDisable(true);
                    
                }
                else
                {
                    showBillButton.setDisable(true);
                    editButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            }
        });
            
        } catch (Exception ex) {
            Logger.getLogger(SPCListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        searchBy.getItems().clear();
        searchBy.getItems().add("Name");
        searchBy.getItems().add("Vehicle Reg");
        searchSelectedSPC.getItems().clear();
        try {
            List<SpecialistRepairCentre> spcList = dm.getSpecialistRepairCentreRepository().all(SpecialistRepairCentre.class);
            for(int i = 0; i<spcList.size();i++){
                searchSelectedSPC.getItems().add(spcList.get(i).getName());
            }
        } catch (Exception ex) {
            Logger.getLogger(SPCBookingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<SpecialistRepair> spcList;
        try {
            spcList = dm.getSpecialistRepairsRepository().all(SpecialistRepair.class);
        for(int i = 0; i<spcList.size();i++)
        {
            if(spcList.get(i).getPartItem()!=null)
            {
                PartsItem part = spcList.get(i).getPartItem();
                part.setRepair(spcList.get(i));
                part.setAvailable(false);
                part.setVehicle(spcList.get(i).getVehicle());
                part.setCustomer(spcList.get(i).getCustomer());
                part.setInstalledOn(spcList.get(i).getDeliveryDate());
                part.setWarrantyEnd(spcList.get(i).getDeliveryDate().plusYears(1));
                try {
                    dm.getPartsItemRepository().save(part);
                } catch (Exception ex) {
                    Logger.getLogger(SPCBookingListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        } catch (Exception ex) {
            Logger.getLogger(SPCBookingListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void toggleRequiresSelection(Node node) {
        node.setDisable(selectedSPC == null);
        node.setVisible(selectedSPC != null);
    }
   public void makeBooking(ActionEvent e){
        spcBookingListRoot.fireEvent(new SPCBookingEditRequestEvent());
    }
    
    public void editBooking(ActionEvent e){
        spcBookingListRoot.fireEvent(new SPCBookingEditRequestEvent(selectedSPC));  
    }
    
    public void deleteBooking(ActionEvent e){
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete this Specialist Repair Booking?");
        confirm.setHeaderText("Do you want to delete booking " + selectedSPC.getId() + ": "+selectedSPC.getCustomer().getFullName() + "?");
        confirm.setContentText("Are you sure you wish to delete this booking.");

        ButtonType deleteBtn = new ButtonType("Delete booking for  " + selectedSPC.getCustomer().getFullName(), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirm.getButtonTypes().setAll(cancelBtn, deleteBtn);

        ButtonType result = confirm.showAndWait().orElse(cancelBtn);
        if (result == deleteBtn) {
            try {
                if(selectedSPC.getPartItem()!=null){
                    dm.getPartsItemRepository().delete(selectedSPC.getPartItem());
                }
                dm.getSpecialistRepairsRepository().delete(selectedSPC);
                selectedSPC = null;
                spcBookingListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);
                drawTable();
                spcBookingTable.getSelectionModel().select(null);
            } catch (Exception ex) {
                Logger.getLogger(SPCListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public void loadSpcList(Event e){
        spcBookingListRoot.fireEvent(new SPCViewRequestEvent());
    }

    public void shown() throws Exception {
        drawTable();
        spcBookingTable.getSelectionModel().select(null);
    } 
    public void searchBooking(ActionEvent e){
        List<SpecialistRepair>  searchedRepair = null;
         String selectedSearch = (String) searchBy.getSelectionModel().getSelectedItem();
         String selectedSPCSearch = (String) searchSelectedSPC.getSelectionModel().getSelectedItem();
         if(selectedSearch==null && selectedSPCSearch ==null ){
             JOptionPane.showMessageDialog(null, "Please select a search option from the drop down menu");
             return;
         }
         if(selectedSPCSearch !=null && selectedSearch == null){
             searchedRepair = dm.getSpecialistRepairsRepository().findBySPC(selectedSPCSearch);
         }
         else{
         searchText = searchBookingsText.getText();
         if(searchText.equals("")){
             JOptionPane.showMessageDialog(null, "Please enter some search text");
             return;
         }
         
         List<SpecialistRepair> temp = new ArrayList<SpecialistRepair>();
         if(selectedSearch.equals("Name"))
         {
             searchedRepair = dm.getSpecialistRepairsRepository().findByName(searchText);
             if(selectedSPCSearch != null){
                 for(int i = 0; i<searchedRepair.size();i++)
                 {
                     if(searchedRepair.get(i).getSPC().getName().equals(selectedSPCSearch))
                     {
                         temp.add(searchedRepair.get(i));
                     }
                 }
                 searchedRepair = temp;
             }
         }
         else if(selectedSearch.equals("Vehicle Reg"))
         {  
             searchedRepair = dm.getSpecialistRepairsRepository().findByReg(searchText);
             if(selectedSPCSearch != null){
                 for(int i = 0; i<searchedRepair.size();i++)
                 {
                     if(searchedRepair.get(i).getSPC().getName().equals(selectedSPCSearch))
                     {
                         temp.add(searchedRepair.get(i));
                     }
                 }
                 searchedRepair = temp;
             }
         }
         }
         ObservableList<SpecialistRepair> searchedRepairOList = FXCollections.observableArrayList(searchedRepair);
         spcBookingTable.setItems(searchedRepairOList); 
    }
    public void clearSearch(ActionEvent e) {
        searchBookingsText.clear();
        try{
            drawTable();
        }
        catch (Exception ex) {
            Logger.getLogger(SPCListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void showBill(ActionEvent e){
        try {
            Bill bill = dm.getBillRepository().getOrCreateBill(selectedSPC.getBooking());
            dm.getBillRepository().delete(bill);
            dm.getBillRepository().getOrCreateBill(selectedSPC.getBooking());
        } catch (Exception ex) {
            Logger.getLogger(SPCBookingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        spcBookingListRoot.fireEvent(CustomerEditRequestEvent.showProfile(selectedSPC.getCustomer()));
    }
    public void showBookingDetails(ActionEvent e){
        if(selectedSPC!=null){
        Alert details = new Alert(Alert.AlertType.CONFIRMATION);
        details.setTitle("Specialist Repair Booking Details");
        details.setHeaderText("Booking " + selectedSPC.getId() + ": "+selectedSPC.getCustomer().getFullName() + "");
        details.setContentText("Customer: "+ selectedSPC.getCustomer().getFullName()+"\n"+
                              (selectedSPC.getPartItem() == null ? "Vehicle : "+selectedSPC.getVehicle().getModel()+
                                      "\nRegistration: "+selectedSPC.getVehicle().getRegistrationNumber() : 
                                      "Part :"+selectedSPC.getPartItem().getPartsModel().getName()+"\nSerial number :"+selectedSPC.getPartItem().getSerialNumber())+
                                      " Part of Vehicle : "+selectedSPC.getVehicle().getModel()+" Registration: "+selectedSPC.getVehicle().getRegistrationNumber()+"\n"+
                               "Repair Centre :"+selectedSPC.getSPC().getName()+"\n"+
                               "Delivery Date :"+selectedSPC.getDeliveryDate()+"\n"+
                               "Return Date :"+selectedSPC.getReturnDate()+"\n"+
                               "Part of Booking : "+selectedSPC.getBooking().getId()+"\n"+
                               "Cost :"+selectedSPC.getCost());
        ButtonType closeBtn = new ButtonType("Close ", ButtonBar.ButtonData.CANCEL_CLOSE);
        details.getButtonTypes().setAll(closeBtn);
        details.show();
        }
        else{
            JOptionPane.showMessageDialog(null, "Please select a booking to view");
        }
    }
    public void setBookingCompleted(ActionEvent e){
        Alert complete = new Alert(Alert.AlertType.CONFIRMATION);
        complete.setTitle("Complete Specialist Repair Booking?");
        complete.setHeaderText("Booking " + selectedSPC.getId() + ": "+selectedSPC.getCustomer().getFullName() + "");
        complete.setContentText("Are you sure you want to set this booking to completed?");
        
        ButtonType confirmBtn = new ButtonType("Complete ", ButtonBar.ButtonData.OK_DONE);
        ButtonType closeBtn = new ButtonType("Close ", ButtonBar.ButtonData.CANCEL_CLOSE);
        complete.getButtonTypes().setAll(closeBtn, confirmBtn);
        
        ButtonType result = complete.showAndWait().orElse(closeBtn);
        if(result == confirmBtn){
            selectedSPC.setCompleted("Yes");
            try {
                dm.getSpecialistRepairsRepository().save(selectedSPC);
                drawTable();
            } catch (Exception ex) {
                Logger.getLogger(SPCBookingListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void showOutstanding(ActionEvent e){
        spcBookingListRoot.fireEvent(new SPCOutstandingListViewRequestEvent());
 
    }
    @FXML
    public void getHelp(){
        Alert details = new Alert(Alert.AlertType.CONFIRMATION);
        details.setTitle("Help");
        details.setHeaderText("Information on searching for different data");
        details.setContentText("To search for a customer select the name option from the search drop down box and enter customer name\n"
                              +"To search for a vehicle (and the corresponding parts repaired on that vehicle)select the vehicle reg option from the search drop down box and enter vehicle reg\n"
                              + "To search for all repairs (parts and vehicles) at a specific SPC select a repair centre option from the second drop down box and search"
                              +"\nTo search for vehicle /customer at a repair centre, select desired options from each drop down and enter required name/reg and search"
                              + "\nPress clear search to show all repairs again");
        ButtonType closeBtn = new ButtonType("Close ", ButtonBar.ButtonData.CANCEL_CLOSE);
        details.getButtonTypes().setAll(closeBtn);
        details.show();
    }
    public void showVehicles(ActionEvent e){
        try {
            List<SpecialistRepair> spcList =  dm.getSpecialistRepairsRepository().all(SpecialistRepair.class);
            List<SpecialistRepair> newList = new ArrayList<SpecialistRepair>();
            for(int i =0; i<spcList.size();i++){
                if(spcList.get(i).getPartItem()== null){
                    newList.add(spcList.get(i));
                }
            }
         ObservableList<SpecialistRepair> OList = FXCollections.observableArrayList(newList);
         spcBookingTable.setItems(OList); 
        } catch (Exception ex) {
            Logger.getLogger(SPCBookingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void showParts(ActionEvent e){
        try {
            List<SpecialistRepair> spcList =  dm.getSpecialistRepairsRepository().all(SpecialistRepair.class);
            List<SpecialistRepair> newList = new ArrayList<SpecialistRepair>();
            for(int i =0; i<spcList.size();i++){
                if(spcList.get(i).getPartItem() != null){
                   newList.add(spcList.get(i));
                }
            }
         ObservableList<SpecialistRepair> OList = FXCollections.observableArrayList(newList);
         spcBookingTable.setItems(OList); 
        } catch (Exception ex) {
            Logger.getLogger(SPCBookingListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
