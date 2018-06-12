/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.specialistRepairs;

import gmsis.models.bookings.Booking;
import gmsis.models.specialistRepairs.SpecialistRepair;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * @author stephen
 */
public class SPCBookingEditRequestEvent extends Event {
    public static final EventType<SPCBookingEditRequestEvent> SPC_BOOKING_EDIT_REQUEST_ANY = new EventType<>(Event.ANY, "spc-booking-edit-request");
    public static final EventType<SPCBookingEditRequestEvent> SPC_BOOKING_EDIT_REQUEST_NEW = new EventType<>(SPC_BOOKING_EDIT_REQUEST_ANY, "spc-booking-edit-request-new");
    public static final EventType<SPCBookingEditRequestEvent> SPC_BOOKING_EDIT_REQUEST_EXISTING = new EventType<>(SPC_BOOKING_EDIT_REQUEST_ANY, "spc-booking-edit-request-existing");

    private Booking booking;
    private SpecialistRepair specialistRepair;

    public SPCBookingEditRequestEvent() {
        super(SPC_BOOKING_EDIT_REQUEST_NEW);
        specialistRepair = null;
    }
    
    public SPCBookingEditRequestEvent(SpecialistRepair toEdit) {
        super(SPC_BOOKING_EDIT_REQUEST_EXISTING);
        specialistRepair = toEdit;
    }

    public SPCBookingEditRequestEvent(Booking booking) {
        super(SPC_BOOKING_EDIT_REQUEST_NEW);
        this.booking = booking;
    }
    
    public SPCBookingEditRequestEvent(Object source, EventTarget target) {
        super(source, target, SPC_BOOKING_EDIT_REQUEST_NEW);
    }
    
    public SpecialistRepair getSpecialistRepair() {
        return specialistRepair;
    }

    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (SPCBookingEditRequestEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends SPCBookingEditRequestEvent> getEventType() {
        return (EventType<? extends SPCBookingEditRequestEvent>) super.getEventType();
    }
}

