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
public class BookingEditRequestEvent extends Event {
    public static final EventType<BookingEditRequestEvent> BOOKING_EDIT_REQUEST_ANY = new EventType<>(Event.ANY, "booking-edit-request");
    public static final EventType<BookingEditRequestEvent> BOOKING_EDIT_REQUEST_NEW = new EventType<>(BOOKING_EDIT_REQUEST_ANY, "booking-edit-request-new");
    public static final EventType<BookingEditRequestEvent> BOOKING_EDIT_REQUEST_EXISTING = new EventType<>(BOOKING_EDIT_REQUEST_ANY, "booking-edit-request-existing");

    private Booking booking;
    public BookingEditRequestEvent() {
        super(BOOKING_EDIT_REQUEST_NEW);
        booking =null;
    }
    
    public BookingEditRequestEvent(Booking toEdit){
        super(BOOKING_EDIT_REQUEST_EXISTING);
        booking =toEdit;
    }
    
      public BookingEditRequestEvent(Object source, EventTarget target) {
        super(source, target, BOOKING_EDIT_REQUEST_NEW);
    }
    
    public Booking getBooking() {
        return booking;
    }
    
    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (BookingEditRequestEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends BookingEditRequestEvent> getEventType() {
        return (EventType<? extends BookingEditRequestEvent>) super.getEventType();
    }
}
