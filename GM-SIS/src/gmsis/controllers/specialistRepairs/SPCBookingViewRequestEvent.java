/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.specialistRepairs;

import gmsis.models.bookings.Booking;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * @author Stephen
 */
public class SPCBookingViewRequestEvent extends Event {
    public static final EventType<SPCBookingViewRequestEvent> SPC_VIEW_REQUEST_ANY = new EventType<>(Event.ANY, "view-request");
    public static final EventType<SPCBookingViewRequestEvent> SPC_VIEW_REQUEST_SPC_BOOKING = new EventType<>(SPC_VIEW_REQUEST_ANY, "spc-booking-view-request");
    public static final EventType<SPCBookingViewRequestEvent> SPC_VIEW_REQUEST_SPC_BOOKING_ADD = new EventType<>(SPC_VIEW_REQUEST_ANY, "spc-booking-add-view-request");
    
    private Booking booking;
    public SPCBookingViewRequestEvent() {
        super(SPC_VIEW_REQUEST_ANY);
    }
    
    
    public SPCBookingViewRequestEvent(Object source, EventTarget target) {
        super(source, target, SPC_VIEW_REQUEST_SPC_BOOKING);
    }
    public SPCBookingViewRequestEvent(Booking booking) {
        super(SPC_VIEW_REQUEST_SPC_BOOKING_ADD);
        this.booking = booking;
    }
    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (SPCBookingViewRequestEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends SPCBookingViewRequestEvent> getEventType() {
        return (EventType<? extends SPCBookingViewRequestEvent>) super.getEventType();
    }
    public Booking getBooking(){
        return booking;
    }

}