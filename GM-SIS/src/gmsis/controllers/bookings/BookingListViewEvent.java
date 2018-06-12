/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.bookings;

import gmsis.models.bookings.Booking;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * @author jakwan
 */
// this will show the list of the bookings
public class BookingListViewEvent extends Event{

    /**
     *
     */
    public static final EventType<BookingListViewEvent> BOOKING_VIEW_REQUEST_ANY = new EventType<>(Event.ANY, "booking-view-request");   
   //added this methid to test so change it if you need to
    public static final EventType<BookingListViewEvent> BOOKING_LIST_VIEW_REQUEST_ANY = new EventType<>(BOOKING_VIEW_REQUEST_ANY, "booking-list-view-request");

   
    public BookingListViewEvent(){
        super(BOOKING_VIEW_REQUEST_ANY);
        
    }
    private BookingListViewEvent(EventType<BookingListViewEvent> t, Booking booking) {
        super(t);
        
    }
     public BookingListViewEvent(Object source, EventTarget target) {
        super(source, target, BOOKING_LIST_VIEW_REQUEST_ANY);
    }
   
    @Override
    public Event copyFor(Object newSource,EventTarget newTarget){
        return (BookingListViewEvent) super.copyFor(newSource, newTarget);
    }
    @Override
    public EventType<? extends BookingListViewEvent> getEventType(){
        return (EventType<? extends BookingListViewEvent>) super.getEventType();
    }
    
}
