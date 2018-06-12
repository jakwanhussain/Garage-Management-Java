/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.specialistRepairs;

import com.jfoenix.controls.JFXTextField;
import gmsis.di.DependencyManager;
import gmsis.models.specialistRepairs.SpecialistRepairCentre;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stephen
 */
public class SPCFormController implements Initializable {
    private DependencyManager dm;

    private SpecialistRepairCentre spc;
    
    @FXML
    private Node spcFormRoot;
    @FXML
    private JFXTextField spcName;
    @FXML
    private JFXTextField spcAddress;
    @FXML
    private JFXTextField spcPhone;
    @FXML
    private JFXTextField spcEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void setDependencyManager(DependencyManager dm) {
            this.dm = dm;
        }

    public void setSPC(SpecialistRepairCentre spc) {
        if(spc!=null){
            spcName.setText(spc.getName());
            spcAddress.setText(spc.getAddress());
            spcPhone.setText(spc.getPhoneNumber());
            spcEmail.setText(spc.getEmail());
        }
        this.spc = spc; 
    }

    public void saveSPC(){
        if (spc == null) {
            spc = new SpecialistRepairCentre();
        }
        spc.setName(spcName.getText());
        spc.setAddress(spcAddress.getText());
        spc.setPhoneNumber(spcPhone.getText());
        spc.setEmail(spcEmail.getText());
        try {
            dm.getSpecialistRepairCentreRepository().save(spc);
            spcFormRoot.fireEvent(SPCEditFinishedEvent.completed(spc));
        } catch (Exception ex) {
            Logger.getLogger(SPCFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
        
    public void cancelSPC(){
        spcFormRoot.fireEvent(SPCEditFinishedEvent.cancelled(spc));

    }
}
