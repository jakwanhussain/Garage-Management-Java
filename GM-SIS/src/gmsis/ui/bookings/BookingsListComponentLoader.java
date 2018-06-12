/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.bookings;

import gmsis.controllers.bookings.BookingListController;
import gmsis.di.DependencyManager;
import gmsis.models.authentication.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 *
 * @author Stephen
 */
public class BookingsListComponentLoader {
    
        private DependencyManager dm;

    public BookingsListComponentLoader(DependencyManager dm){
        this.dm = dm;
    }
    public Node load() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/bookings/BookingList.fxml"));
        Node node = loader.load();
        BookingListController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        return node;
    }  

}

