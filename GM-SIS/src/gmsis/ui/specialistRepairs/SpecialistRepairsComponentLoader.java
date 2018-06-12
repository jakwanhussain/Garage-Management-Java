/*
 *  This file is part of GM-SIS by #Team10
 */
package gmsis.ui.specialistRepairs;

import gmsis.controllers.specialistRepairs.SpecialistRepairsController;
import gmsis.di.DependencyManager;
import gmsis.models.bookings.Booking;
import gmsis.models.specialistRepairs.SpecialistRepair;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 *
 * @author Stephen
 */
public class SpecialistRepairsComponentLoader {
        
    private DependencyManager dm;

    public SpecialistRepairsComponentLoader(DependencyManager dm){
        this.dm = dm;
    }
    public Node load(SpecialistRepair spc) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/specialistRepairs/SpecialistRepairs.fxml"));
        Node node = loader.load();
        SpecialistRepairsController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);  
        ctrl.setSpecialistRepair(spc);
        return node;
    } 
    public Node loadWithBooking(Booking booking) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/specialistRepairs/SpecialistRepairs.fxml"));
        Node node = loader.load();
        SpecialistRepairsController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        ctrl.setBooking(booking);
        return node;
    }  



}

