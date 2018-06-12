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
 * @author stephen
 */
public class SPCBookingNewWithBookingInfoRequestEvent extends Event {
    public static final EventType<SPCBookingNewWithBookingInfoRequestEvent> SPC_BOOKING_NEW_WITH_BOOKING_INFO_REQUEST_ANY = new EventType<>(Event.ANY, "spc-booking-with-booking-info-request");
    public static final EventType<SPCBookingNewWithBookingInfoRequestEvent> SPC_BOOKING_NEW_WITH_BOOKING_INFO_REQUEST_NEW = new EventType<>(SPC_BOOKING_NEW_WITH_BOOKING_INFO_REQUEST_ANY, "spc-booking-with-booking-info-request-new");

    private Booking booking;
    

    public SPCBookingNewWithBookingInfoRequestEvent(Booking booking) {
        super(SPC_BOOKING_NEW_WITH_BOOKING_INFO_REQUEST_ANY);
        this.booking = booking;
    }
    
    public SPCBookingNewWithBookingInfoRequestEvent(Object source, EventTarget target) {
        super(source, target, SPC_BOOKING_NEW_WITH_BOOKING_INFO_REQUEST_NEW);
    }
    
    public Booking getBooking() {
        return booking;
    }

    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (SPCBookingNewWithBookingInfoRequestEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends SPCBookingNewWithBookingInfoRequestEvent> getEventType() {
        return (EventType<? extends SPCBookingNewWithBookingInfoRequestEvent>) super.getEventType();
    }
}