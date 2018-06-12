/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.bookings;

import gmsis.controllers.bookings.BookingEditFinishedEvent;
import gmsis.controllers.bookings.BookingEditRequestEvent;
import gmsis.controllers.bookings.BookingListController;
import gmsis.controllers.bookings.BookingViewEvent;
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
 * @author jakwan
 */
public class BookingsUI {

    private StackPane root;
    private Node bookingsScreen;
    private Node BookingListScreen;
    private DependencyManager dm;

    public BookingsUI(DependencyManager dm) throws IOException {
        this.dm = dm;
        //loads booking list as home screen
        root = new StackPane();
        BookingListScreen = new BookingsListComponentLoader(dm).load();
        root.getChildren().add(BookingListScreen);

        //booking screen to make new bookings
        bookingsScreen = null;
        root.addEventHandler(BookingViewEvent.BOOKING_VIEW_REQUEST_ANY, e -> {
            try {
                this.bookingsScreen = new BookingsListComponentLoader(dm).load();
            } catch (Exception ex) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            root.getChildren().remove(0);
            root.getChildren().add(bookingsScreen);
        });
        // this is to get customer from booking class   
        root.addEventHandler(BookingViewEvent.BOOKING_VIEW_REQUEST_ANY, e -> {
            try {
                this.bookingsScreen = new BookingsComponentLoader(dm).loadWithCustomer(e.getCustomer());
                //this.bookingsScreen = new BookingsComponentLoader(dm).loadExistingEditor(e.getCustomer());
            } catch (Exception ex) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            root.getChildren().remove(0);
            root.getChildren().add(bookingsScreen);
        });
        // add to check
        root.addEventHandler(BookingEditRequestEvent.BOOKING_EDIT_REQUEST_ANY, e -> {
            try {
                this.bookingsScreen = new BookingsComponentLoader(dm).load(e.getBooking());
            } catch (Exception ex) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            root.getChildren().remove(0);
            root.getChildren().add(bookingsScreen);
        });

        root.addEventHandler(BookingEditFinishedEvent.BOOKING_EDIT_FINISHED_ANY, e -> {
            root.getChildren().remove(0);
            root.getChildren().add(BookingListScreen);
            try {
                ((BookingListController) BookingListScreen.getProperties().get("controller")).shown();
            } catch (Exception ex) {
                Logger.getLogger(GmsisApp.class.getName()).log(Level.SEVERE, null, ex);
            }
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
