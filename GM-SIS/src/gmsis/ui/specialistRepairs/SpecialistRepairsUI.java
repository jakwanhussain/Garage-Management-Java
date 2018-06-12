/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.specialistRepairs;

import gmsis.controllers.specialistRepairs.*;
import gmsis.di.DependencyManager;
import gmsis.models.authentication.User;
import gmsis.ui.GmsisApp;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stephen
 */
public class SpecialistRepairsUI {

    private StackPane root;
    private Node specialistRepairsScreen;
    private Node specialistRepairsListScreen;
    private Node SPCListScreen;
    private Node SPCFormScreen;
    private Node SPCOutstandingListScreen;
    private User user;

    private DependencyManager dm;

    public SpecialistRepairsUI(User user, DependencyManager dm) throws IOException {
        this.dm = dm;
        this.user = user;

        root = new StackPane();
        specialistRepairsListScreen = new SPCBookingListComponentLoader(dm).load();
        root.getChildren().add(specialistRepairsListScreen);

        specialistRepairsScreen = null;
        root.addEventHandler(SPCBookingEditRequestEvent.SPC_BOOKING_EDIT_REQUEST_ANY, e -> {
            try {
                this.specialistRepairsScreen = new SpecialistRepairsComponentLoader(dm).load(e.getSpecialistRepair());
            } catch (Exception ex) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            root.getChildren().remove(0);
            root.getChildren().add(specialistRepairsScreen);
        });
        root.addEventHandler(SPCBookingNewWithBookingInfoRequestEvent.SPC_BOOKING_NEW_WITH_BOOKING_INFO_REQUEST_ANY, e -> {
            try {
                this.specialistRepairsScreen = new SpecialistRepairsComponentLoader(dm).loadWithBooking(e.getBooking());
            } catch (Exception ex) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            root.getChildren().remove(0);
            root.getChildren().add(specialistRepairsScreen);
        });

        root.addEventHandler(SPCBookingEditFinishedEvent.SPC_BOOKING_EDIT_FINISHED_ANY, e -> {
            root.getChildren().remove(0);
            root.getChildren().add(specialistRepairsListScreen);
            try {
                ((SPCBookingListController) specialistRepairsListScreen.getProperties().get("controller")).shown();
            } catch (Exception ex) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        SPCListScreen = null;
        root.addEventHandler(SPCViewRequestEvent.SPC_VIEW_REQUEST_ANY, e -> {
            try {
                this.SPCListScreen = new SPCListComponentLoader(dm).load(user);
            } catch (Exception ex) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            root.getChildren().remove(0);
            root.getChildren().add(SPCListScreen);
        });

        root.addEventHandler(SPCBookingListViewRequestEvent.SPC_LIST_VIEW_REQUEST_ANY, e -> {
            root.getChildren().remove(0);
            root.getChildren().add(specialistRepairsListScreen);
            try {
                ((SPCBookingListController) specialistRepairsListScreen.getProperties().get("controller")).shown();
            } catch (Exception ex) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        root.addEventHandler(SPCEditRequestEvent.SPC_EDIT_REQUEST_ANY, e -> {
            try {
                this.SPCFormScreen = new SPCFormComponentLoader(dm).load(e.getSPC());
            } catch (Exception ex) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            root.getChildren().remove(0);
            root.getChildren().add(SPCFormScreen);
        });

        root.addEventHandler(SPCEditFinishedEvent.SPC_EDIT_FINISHED_ANY, e -> {
            root.getChildren().remove(0);
            root.getChildren().add(SPCListScreen);
            try {
                ((SPCListController) SPCListScreen.getProperties().get("controller")).shown();
            } catch (Exception ex) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        root.addEventHandler(SPCOutstandingListViewRequestEvent.SPC_OUTSTANDING_LIST_VIEW_REQUEST_ANY, e ->{
            try{
                this.SPCOutstandingListScreen = new SPCOutstandingListComponentLoader(dm).load();
            }catch(Exception ex){
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            root.getChildren().remove(0);
            root.getChildren().add(SPCOutstandingListScreen);
        });

        Platform.runLater(() -> {
            root.prefWidthProperty().bind(((Pane) root.getParent()).widthProperty());
            root.prefHeightProperty().bind(((Pane) root.getParent()).heightProperty());
        });

    }

    public Node get() {
        return root;
    }

}
