/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.specialistRepairs;

import gmsis.models.specialistRepairs.SpecialistRepair;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * @author stephen
 */
public class SPCBookingEditFinishedEvent extends Event {
    public static final EventType<SPCBookingEditFinishedEvent> SPC_BOOKING_EDIT_FINISHED_ANY = new EventType<>(Event.ANY, "spc-booking-edit-finished");
    public static final EventType<SPCBookingEditFinishedEvent> SPC_BOOKING_EDIT_FINISHED_CANCELLED = new EventType<>(SPC_BOOKING_EDIT_FINISHED_ANY, "spc-booking-edit-cancelled");
    public static final EventType<SPCBookingEditFinishedEvent> SPC_BOOKING_EDIT_FINISHED_COMPLETED = new EventType<>(SPC_BOOKING_EDIT_FINISHED_ANY, "spc-booking-edit-completed");
    
    private SpecialistRepair specialistRepair;

    private SPCBookingEditFinishedEvent(EventType<SPCBookingEditFinishedEvent> t, SpecialistRepair specialistRepair) {
        super(t);
        this.specialistRepair = specialistRepair;
    }
    
    public static SPCBookingEditFinishedEvent cancelled(SpecialistRepair c) {
        return new SPCBookingEditFinishedEvent(SPC_BOOKING_EDIT_FINISHED_CANCELLED, c);
    }
    
    public static SPCBookingEditFinishedEvent completed(SpecialistRepair c) {
        return new SPCBookingEditFinishedEvent(SPC_BOOKING_EDIT_FINISHED_COMPLETED, c);
    }

    public SpecialistRepair getSpecialistRepair() {
        return specialistRepair;
    }

    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (SPCBookingEditFinishedEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends SPCBookingEditFinishedEvent> getEventType() {
        return (EventType<? extends SPCBookingEditFinishedEvent>) super.getEventType();
    }

}