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
public class BookingEditFinishedEvent extends Event {
    public static final EventType<BookingEditFinishedEvent> BOOKING_EDIT_FINISHED_ANY = new EventType<>(Event.ANY, "booking-edit-finished");
    public static final EventType<BookingEditFinishedEvent> BOOKING_EDIT_FINISHED_CANCELLED = new EventType<>(BOOKING_EDIT_FINISHED_ANY, "booking-edit-cancelled");
    public static final EventType<BookingEditFinishedEvent> BOOKING_EDIT_FINISHED_COMPLETED = new EventType<>(BOOKING_EDIT_FINISHED_ANY, "booking-edit-completed");
    
    private Booking booking;
    public BookingEditFinishedEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
    
    private BookingEditFinishedEvent(EventType<BookingEditFinishedEvent> t, Booking booking) {
        super(t);
        this.booking = booking;
    }
    
    public static BookingEditFinishedEvent cancelled(Booking c) {
        return new BookingEditFinishedEvent(BOOKING_EDIT_FINISHED_CANCELLED, c);
    }
    
    public static BookingEditFinishedEvent completed(Booking c) {
        return new BookingEditFinishedEvent(BOOKING_EDIT_FINISHED_COMPLETED, c);
    }

    public Booking getBooking() {
        return booking;
    }
    
    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (BookingEditFinishedEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends BookingEditFinishedEvent> getEventType() {
        return (EventType<? extends BookingEditFinishedEvent>) super.getEventType();
    }
}
