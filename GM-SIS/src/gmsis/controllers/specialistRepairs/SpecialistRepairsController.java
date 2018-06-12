/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package gmsis.controllers.specialistRepairs;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import gmsis.controllers.bookings.BookingsController;
import gmsis.di.DependencyManager;
import gmsis.models.authentication.User;
import gmsis.models.bookings.Booking;
import gmsis.models.customers.Customer;
import gmsis.models.parts.PartsItem;
import gmsis.models.specialistRepairs.SpecialistRepair;
import gmsis.models.specialistRepairs.SpecialistRepairCentre;
import gmsis.models.vehicles.Vehicle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author Stephen
 */
public class SpecialistRepairsController implements Initializable {
    
    private DependencyManager dm;
    private User user;
    
    private SpecialistRepair specialistRepair;
   
    @FXML private Node specialistRepairRoot;
       
    @FXML private JFXTextField costText;
    
    @FXML private ComboBox selectCustomer;
    
    //private JFXTextField customerVehicleText;
    
    @FXML private ComboBox customerVehicle;
    
    @FXML private ToggleGroup toRepair;
    
    @FXML private RadioButton partButton;
    
    @FXML private RadioButton vehicleButton;
    
    @FXML private JFXTextField partText;
    
    //@FXML private JFXTextField vehicleText;
    
    @FXML private ComboBox selectSPCDrop;
    
    @FXML private JFXDatePicker deliveryDate;
    
    @FXML private JFXDatePicker returnDate;
   
    @FXML private ComboBox selectBooking;
    
    private List<Customer> customer;
    private PartsItem part;
    private List<Vehicle> vehicle;
    private SpecialistRepairCentre spc;
    private Booking booking;
       
    public void setDependencyManager(DependencyManager dm){
        this.dm = dm;
        drawBox();
    }
    public void drawBox(){
        
        List<Customer> customers = null;
        try {
            customers = dm.getCustomerRepository().all(Customer.class);
            for(int i = 0; i<customers.size();i++){
            selectCustomer.getItems().addAll(customers.get(i).getFullName());
        } 
        } catch (Exception ex) {
            Logger.getLogger(SpecialistRepairsController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        List<SpecialistRepairCentre> spcs = null;
        try{
            spcs = dm.getSpecialistRepairCentreRepository().all(SpecialistRepairCentre.class);
            for(int i = 0; i<spcs.size();i++){
            selectSPCDrop.getItems().addAll(spcs.get(i).getName());
        } 
        }
        catch(Exception ex){
            Logger.getLogger(SpecialistRepairsController.class.getName()).log(Level.SEVERE, null, ex);            
        }
        List<Booking> bookings = null;
        try{
            bookings = dm.getBookingsRepository().all(Booking.class);
            for(int i = 0; i<bookings.size();i++){
            selectBooking.getItems().addAll(bookings.get(i).getId());
        } 
        }
        catch(Exception ex){
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);            
        }
        selectCustomer.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->{
                    updateVehicleDrop ((String) newValue);
                }
        );
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    } 
    public void makeBooking(Event e) throws Exception{
        if (specialistRepair == null) {
            specialistRepair = new SpecialistRepair();
        }
        try{
            String selectedCustomer;
            selectedCustomer = (String) selectCustomer.getSelectionModel().getSelectedItem();
            customer = dm.getCustomerRepository().findByName(selectedCustomer);
            specialistRepair.setCustomer(customer.get(0));
        } catch(IndexOutOfBoundsException iOOBE){
            JOptionPane.showMessageDialog(null, "Please select a customer");
            return;
        }
        try{
            specialistRepair.setCost(Double.parseDouble(costText.getText()));
        } catch(NumberFormatException nFE) {
            JOptionPane.showMessageDialog(null,"Please enter repair cost, numbers only");
            return;
        }
        if(deliveryDate.getValue().isAfter(LocalDate.now())&&returnDate.getValue().isAfter(LocalDate.now()))
        {
            if(deliveryDate.getValue().getDayOfWeek().getValue() == 7 || returnDate.getValue().getDayOfWeek().getValue() == 7){
                JOptionPane.showMessageDialog(null, "Bookings cannot be made on sundays");
                return;
            }
            int b = deliveryDate.getValue().getDayOfYear();
            int c = returnDate.getValue().getDayOfYear();
            //public holidays are 1,3,359 ,360 remain same for until 2020, rest should be updated every year
            if( (b!=1) && (b!=2) && (b!=359) && (b!=360) && (b!=104) && (b!=107) && (b!=121) 
                 && (b!=149) && (b!=240) && (c!=1) && (c!=2) && (c!=359) && (c!=360) && (c!=104) && (c!=107) && (c!=121) 
                 && (c!=149) && (c!=240)){
                specialistRepair.setDeliveryDate(deliveryDate.getValue());
                specialistRepair.setReturnDate(returnDate.getValue());
            }
            else{
                JOptionPane.showMessageDialog(null, "Garage closed, please select a different delivery/return date");
                return;
            }
            
        }
        else{
            JOptionPane.showMessageDialog(null, "Bookings can only be made in the future");
            return;
        }
        if(customerVehicle.getSelectionModel().getSelectedItem() == null){
            JOptionPane.showMessageDialog(null, "Please enter a vehicle reg,\nif it is a part being repaired, enter the vehicle that the part belongs to.");
            return;
        }
        try{
            vehicle = dm.getVehicleRepository().findByRegistration((String) customerVehicle.getSelectionModel().getSelectedItem());
            specialistRepair.setVehicle(vehicle.get(0));
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null, "invalid vehicle registration");
            return;
        }
        if(partButton.isSelected() == true)
        {
            if(partText.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please enter a part serial number");  
                return;
            }
            try{
                part = dm.getPartsItemRepository().getAvailablePartsItem(partText.getText()).get(0);
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Invalid part");
                return;
            }
                if(part.getAvailable()){
                specialistRepair.setPartItem(part);  
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "This part is unavailable");
                    return;
                }
        } 
        
        Long selectedBooking = (Long) selectBooking.getSelectionModel().getSelectedItem();
        booking = dm.getBookingsRepository().findById(selectedBooking);
        if(booking.getCustomer().getId()!=specialistRepair.getCustomer().getId())
        {
            JOptionPane.showMessageDialog(null, "The booking you have selected belongs to a different customer\n"
                                              + "please choose a different booking to connect this specialist booking too or check you have chosen the right customer");
            return;
        }
        else{
            specialistRepair.setBooking(booking);
        }
        
      
        String selectedSPC;
        selectedSPC= (String) selectSPCDrop.getSelectionModel().getSelectedItem();
        spc = dm.getSpecialistRepairCentreRepository().findByName(selectedSPC);
        specialistRepair.setSPC(spc);
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Make this Booking?");
        confirm.setHeaderText("Do you want to make this booking?");
        confirm.setContentText("Customer: "+ specialistRepair.getCustomer().getFullName()+"\n"+
                               //"Part "+specialistRepair.getPartItem().getSerialNumber()+"\n"+
                               (partButton.isSelected() ? "part " + part.getSerialNumber() : "Vehicle " + vehicle.get(0).getRegistrationNumber()) + "\n"+
                               "Repair Centre "+specialistRepair.getSPC().getName()+"\n"+
                               "Delivery Date "+specialistRepair.getDeliveryDate()+"\n"+
                               "Return Date "+specialistRepair.getReturnDate()+"\n"+
                               "Cost "+specialistRepair.getCost());
        ButtonType confirmBtn = new ButtonType("Confirm ", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirm.getButtonTypes().setAll(cancelBtn, confirmBtn);

        ButtonType result = confirm.showAndWait().orElse(cancelBtn);
        if (result == confirmBtn) {
            try {
                dm.getSpecialistRepairsRepository().save(specialistRepair);
                selectedSPC = null;
                specialistRepairRoot.fireEvent(SPCBookingEditFinishedEvent.completed(specialistRepair));
            } catch (Exception ex) {
                Logger.getLogger(SpecialistRepairsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }     
    }
    
    public void setBooking(Booking book){
        this.booking = book;
            selectCustomer.getSelectionModel().select(book.getCustomer().getFullName());
            selectBooking.setValue(book.getId());
           customerVehicle.setValue(book.getVehicle().getRegistrationNumber());
    }
    
    public void cancelBooking(Event e){
        specialistRepairRoot.fireEvent(SPCBookingEditFinishedEvent.cancelled(specialistRepair));
    }

    public void shown() {
        drawBox();
    }

    public void setSpecialistRepair(SpecialistRepair specialistRepair) {
        if(specialistRepair!=null)
        {
            selectCustomer.setValue(specialistRepair.getCustomer().getFullName());
            if(specialistRepair.getPartItem()==null)
            {
                vehicleButton.selectedProperty().set(true);
                
            }
            else{
                partButton.selectedProperty().set(true);
                partText.setText(specialistRepair.getPartItem().getSerialNumber());
                specialistRepair.getPartItem().setAvailable(true);
                try {
                    dm.getPartsItemRepository().save(specialistRepair.getPartItem());
                            } catch (Exception ex) {
                    Logger.getLogger(SpecialistRepairsController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            selectSPCDrop.setValue(specialistRepair.getSPC().getName());
            deliveryDate.setValue(specialistRepair.getDeliveryDate());
            returnDate.setValue(specialistRepair.getReturnDate());
            costText.setText(Double.toString(specialistRepair.getCost()));
            selectBooking.setValue(specialistRepair.getBooking().getId());
            customerVehicle.setValue(specialistRepair.getVehicle().getRegistrationNumber());
            }
        this.specialistRepair = specialistRepair;
       }  

    private void updateVehicleDrop(String string) {
         ArrayList<String> veh= new ArrayList<String>();
     List<Vehicle> vehicles = null;
        try{
            vehicles = dm.getVehicleRepository().findByCustomer(string);            
            for(int i = 0; i<vehicles.size(); i++){
                veh.add(vehicles.get(i).getRegistrationNumber());                                
            }
        } catch(Exception e){
            Logger.getLogger(BookingsController.class.getName()).log(Level.SEVERE, null, e);
        }
               customerVehicle.getItems().clear();
               customerVehicle.getItems().addAll(
               veh.toArray()
        );   
    }
    }


