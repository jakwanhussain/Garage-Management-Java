/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.specialistRepairs;

import gmsis.di.DependencyManager;
import gmsis.models.specialistRepairs.SpecialistRepair;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
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
public class SPCOutstandingListController implements Initializable {
    
    private DependencyManager dm;
    @FXML
    private Node spcOutstandingListRoot;
    @FXML
    private TableView<SpecialistRepair> spcOutstandingTable;
    @FXML
    private TableColumn<SpecialistRepair, Long> idCol;
    @FXML 
    private TableColumn<SpecialistRepair, String> nameCol;
    @FXML
    private TableColumn<SpecialistRepair, String> spcCol;
    @FXML
    private TableColumn<SpecialistRepair, String> partVehicleCol;
    @FXML
    private TableColumn<SpecialistRepair, String> returnDateCol;
    
    
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
        spcOutstandingListRoot.getProperties().put("controller", this);
        }
    
        public void drawTable() {
        spcOutstandingTable.setEditable(true);
        try {
            List<SpecialistRepair> spcList = dm.getSpecialistRepairsRepository().all(SpecialistRepair.class);
            List<SpecialistRepair> newSpcList = new ArrayList<SpecialistRepair>();
            for(int i = 0; i<spcList.size();i++){
                if(spcList.get(i).getCompleted().equals("No")){
                    newSpcList.add(spcList.get(i));
                }
            }
            idCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getId()));
            nameCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getCustomer().getFullName()));
            spcCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getSPC().getName()));
            partVehicleCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getPartItem() == null ? row.getValue().getVehicle().getModel() : row.getValue().getPartItem().getPartsModel().getName()));
            returnDateCol.setCellValueFactory(row -> new SimpleObjectProperty<>(row.getValue().getReturnDate().format(DateTimeFormatter.ISO_DATE))); 
            
            ObservableList<SpecialistRepair> spcOList = FXCollections.observableArrayList(newSpcList);
            spcOutstandingTable.setItems(spcOList);
            } catch (Exception ex) {
            Logger.getLogger(SPCOutstandingListController.class.getName()).log(Level.SEVERE, null, ex);
            }   
        } 

    public void closeOutstanding(ActionEvent e){
        spcOutstandingListRoot.fireEvent(new SPCBookingListViewRequestEvent());
    }
    public void shown() throws Exception {
        drawTable();
    } 
}
