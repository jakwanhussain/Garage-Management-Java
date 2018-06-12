/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.specialistRepairs;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * @author Stephen
 */
public class SPCBookingListViewRequestEvent extends Event {
    public static final EventType<SPCBookingListViewRequestEvent> SPC_LIST_VIEW_REQUEST_ANY = new EventType<>(Event.ANY, "list-view-request");
    public static final EventType<SPCBookingListViewRequestEvent> SPC_BOOKING_LIST_VIEW = new EventType<>(SPC_LIST_VIEW_REQUEST_ANY, "spc-booking-list-view-request");
    
    
    public SPCBookingListViewRequestEvent() {
        super(SPC_LIST_VIEW_REQUEST_ANY);
    }

    public SPCBookingListViewRequestEvent(Object source, EventTarget target) {
        super(source, target, SPC_BOOKING_LIST_VIEW);
    }
    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (SPCBookingListViewRequestEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends SPCBookingListViewRequestEvent> getEventType() {
        return (EventType<? extends SPCBookingListViewRequestEvent>) super.getEventType();
    }

}