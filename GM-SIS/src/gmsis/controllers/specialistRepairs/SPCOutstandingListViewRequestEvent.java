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
public class SPCOutstandingListViewRequestEvent extends Event {
    public static final EventType<SPCOutstandingListViewRequestEvent> SPC_OUTSTANDING_LIST_VIEW_REQUEST_ANY = new EventType<>(Event.ANY, "view-outstanding-request");
    public static final EventType<SPCOutstandingListViewRequestEvent> SPC_OUTSTANDING_LIST_VIEW_REQUEST = new EventType<>(SPC_OUTSTANDING_LIST_VIEW_REQUEST_ANY, "spc-outstanding-list-view-request");
 
    
    public SPCOutstandingListViewRequestEvent() {
        super(SPC_OUTSTANDING_LIST_VIEW_REQUEST_ANY);
    }

    public SPCOutstandingListViewRequestEvent(Object source, EventTarget target) {
        super(source, target, SPC_OUTSTANDING_LIST_VIEW_REQUEST);
    }
    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (SPCOutstandingListViewRequestEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends SPCOutstandingListViewRequestEvent> getEventType() {
        return (EventType<? extends SPCOutstandingListViewRequestEvent>) super.getEventType();
    }

}
