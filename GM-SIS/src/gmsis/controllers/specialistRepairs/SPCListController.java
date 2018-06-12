/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.specialistRepairs;

import com.jfoenix.controls.JFXButton;
import gmsis.di.DependencyManager;
import gmsis.models.authentication.User;
import gmsis.models.specialistRepairs.SpecialistRepairCentre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Stephen
 */
public class SPCListController implements Initializable {
  
    private DependencyManager dm;
    
    private SpecialistRepairCentre selectedSPC = null;
    private User user;

    @FXML
    private TableView<SpecialistRepairCentre> spcTable;
    @FXML
    private TableColumn<SpecialistRepairCentre, Long> idCol;
    @FXML 
    private TableColumn<SpecialistRepairCentre, String> nameCol;
    @FXML
    private TableColumn<SpecialistRepairCentre, String> addressCol;
    @FXML
    private TableColumn<SpecialistRepairCentre, String> emailCol;
    @FXML
    private TableColumn<SpecialistRepairCentre, String> phoneCol;
    @FXML
    private Node spcListRoot;
    @FXML
    private JFXButton createListButton;
    @FXML
    private JFXButton addButton;
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
        spcListRoot.getProperties().put("controller", this);
        spcListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);
        }
    
    
    public void drawTable() throws Exception {
        spcTable.getItems().clear();
        spcTable.setEditable(true);
        try {
            List<SpecialistRepairCentre> spcList =  dm.getSpecialistRepairCentreRepository().all(SpecialistRepairCentre.class);
                idCol.setCellValueFactory(new PropertyValueFactory<SpecialistRepairCentre, Long>("id"));
                nameCol.setCellValueFactory(new PropertyValueFactory<SpecialistRepairCentre, String>("name"));
                addressCol.setCellValueFactory(new PropertyValueFactory<SpecialistRepairCentre, String>("address"));
                emailCol.setCellValueFactory(new PropertyValueFactory<SpecialistRepairCentre, String>("email"));
                phoneCol.setCellValueFactory(new PropertyValueFactory<SpecialistRepairCentre, String>("phoneNumber"));
                
                ObservableList<SpecialistRepairCentre> spcOList = FXCollections.observableArrayList(spcList);
                spcTable.setItems(spcOList);
                
                //Add change listener
            spcTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (spcTable.getSelectionModel().getSelectedItem() != null) {
                selectedSPC = spcTable.getSelectionModel().getSelectedItem();
            }
        });
            
        } catch (Exception ex) {
            Logger.getLogger(SPCListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void toggleRequiresSelection(Node node) {
        node.setDisable(selectedSPC == null);
        node.setVisible(selectedSPC != null);
    }

    public void addSPC(ActionEvent e) {
       
        try{
            List<SpecialistRepairCentre> spcList =  dm.getSpecialistRepairCentreRepository().all(SpecialistRepairCentre.class);
            if(spcList.size()<10){
                this.spcTable.fireEvent(new SPCEditRequestEvent());
            }
            else{
                JOptionPane.showMessageDialog(null, "Cannot add any more Specialist Repair Centres - maximum of 10 reached");
            }
        }catch(Exception ex){
            Logger.getLogger(SPCListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void editSelectedSPC(ActionEvent e) {
        this.spcTable.fireEvent(new SPCEditRequestEvent(selectedSPC));
    }
    
    public void deleteSelectedSPC(ActionEvent e) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete this Specialist Repair Centre?");
        confirm.setHeaderText("Do you want to delete " + selectedSPC.getName() + "?");
        confirm.setContentText("All specialist bookings related to this Specialist Repair Centre will be deleted too.");

        ButtonType deleteBtn = new ButtonType("Delete " + selectedSPC.getName(), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirm.getButtonTypes().setAll(cancelBtn, deleteBtn);

        ButtonType result = confirm.showAndWait().orElse(cancelBtn);
        if (result == deleteBtn) {
            try {
                dm.getSpecialistRepairCentreRepository().delete(selectedSPC);
                selectedSPC = null;
                spcListRoot.lookupAll(".requires-selection").forEach(this::toggleRequiresSelection);
                drawTable();
                spcTable.getSelectionModel().select(null);
            } catch (Exception ex) {
                Logger.getLogger(SPCListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
    public void shown() throws Exception {
        drawTable();
        spcTable.getSelectionModel().select(null);
    } 
    public void goBack(Event e){
        spcListRoot.fireEvent(new SPCBookingListViewRequestEvent());
    }

    public void setUser(User user) {
        this.user = user;
        
        if(user != null && !user.getIsAdmin())
        {
            addButton.setVisible(false);   
            editButton.setVisible(false);
            deleteButton.setVisible(false);
        }
    }
 }

