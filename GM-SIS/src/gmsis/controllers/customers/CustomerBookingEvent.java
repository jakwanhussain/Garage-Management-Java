/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.customers;

import gmsis.models.customers.Customer;
import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author filip
 */
public class CustomerBookingEvent extends Event {
    //public static final EventType<BookingViewEvent> BOOKING_VIEW_REQUEST_ANY = new EventType<>(Event.ANY, "customer-booking-any");
    public static final EventType<CustomerBookingEvent> ANY = new EventType<>(Event.ANY, "customer-booking-any");
    public static final EventType<CustomerBookingEvent> NEW_BOOKING = new EventType<>(ANY, "customer-booking-new");
    
    private Customer customer;
    
    private CustomerBookingEvent(EventType<CustomerBookingEvent> t) {
        super(t);
    }
    
    public static CustomerBookingEvent newBooking(Customer c) {
        CustomerBookingEvent event = new CustomerBookingEvent(NEW_BOOKING);
        event.customer = c;
        return event;
    }
    
    public Customer getCustomer() {
        return customer;
    }
}
