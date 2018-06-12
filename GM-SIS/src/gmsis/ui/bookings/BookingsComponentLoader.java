/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.ui.bookings;

import gmsis.controllers.bookings.BookingsController;
import gmsis.di.DependencyManager;
import gmsis.models.bookings.Booking;
import gmsis.models.customers.Customer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 *
 * @author jakwan
 */
public class BookingsComponentLoader {
    private DependencyManager dm;
    
    public BookingsComponentLoader(DependencyManager dm){
        this.dm = dm;
    }
    
    public Node load(Booking booking) throws IOException, Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/bookings/Bookings.fxml"));
        Node node = loader.load();
        BookingsController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        ctrl.setBooking(booking);
        return node;
     
    }
    public Node loadWithCustomer(Customer cust) throws IOException, Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gmsis/ui/bookings/Bookings.fxml"));
        Node node = loader.load();
        BookingsController ctrl = loader.getController();
        ctrl.setDependencyManager(dm);
        ctrl.setCustomer(cust);
        return node;
    }
}
