/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.bookings;

import gmsis.models.customers.Customer;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * @author jakwan
 */

//it's for go back to the booking home screen not the list
public class BookingViewEvent extends Event{
    public static final EventType<BookingViewEvent> BOOKING_VIEW_REQUEST_ANY = new EventType<>(Event.ANY, "Booking-view-request");   
    public static final EventType<BookingViewEvent> BOOKING_VIEW = new EventType<>(BOOKING_VIEW_REQUEST_ANY, "Booking-View");
    public static final EventType<BookingViewEvent> BOOKING_VIEW_ADD = new EventType<>(BOOKING_VIEW_REQUEST_ANY, "Booking-View-add-request");
    private Customer customer;
    
    public BookingViewEvent(){
        super(BOOKING_VIEW_REQUEST_ANY);
        
    }
    public BookingViewEvent(Customer customer) {
        super(BOOKING_VIEW_REQUEST_ANY);
        this.customer = customer;
    }
    
    public BookingViewEvent(Object source, EventTarget target) {
        super(source, target, BOOKING_VIEW);
    }

    public Customer getCustomer() {
        return customer;
    }
    @Override
    public Event copyFor(Object newSource,EventTarget newTarget){
        return (BookingViewEvent) super.copyFor(newSource, newTarget);
    }
    @Override
    public EventType<? extends BookingViewEvent> getEventType(){
        return (EventType<? extends BookingViewEvent>) super.getEventType();
    }

 
    
}
