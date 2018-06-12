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
public class SPCViewRequestEvent extends Event {
    public static final EventType<SPCViewRequestEvent> SPC_VIEW_REQUEST_ANY = new EventType<>(Event.ANY, "spc-view-request");


    public SPCViewRequestEvent() {
        super(SPC_VIEW_REQUEST_ANY);
    }
    public SPCViewRequestEvent(Object source, EventTarget target) {
        super(source, target, SPC_VIEW_REQUEST_ANY);
    }
    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (SPCViewRequestEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends SPCViewRequestEvent> getEventType() {
        return (EventType<? extends SPCViewRequestEvent>) super.getEventType();
    }
}
