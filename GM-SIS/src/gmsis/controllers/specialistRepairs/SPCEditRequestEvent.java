/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.specialistRepairs;

import gmsis.models.specialistRepairs.SpecialistRepairCentre;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * @author stephen
 */
public class SPCEditRequestEvent extends Event {
    public static final EventType<SPCEditRequestEvent> SPC_EDIT_REQUEST_ANY = new EventType<>(Event.ANY, "spc-edit-request");
    public static final EventType<SPCEditRequestEvent> SPC_EDIT_REQUEST_NEW = new EventType<>(SPC_EDIT_REQUEST_ANY, "spc-edit-request-new");
    public static final EventType<SPCEditRequestEvent> SPC_EDIT_REQUEST_EXISTING = new EventType<>(SPC_EDIT_REQUEST_ANY, "spc-edit-request-existing");
    
    private SpecialistRepairCentre spc;

    public SPCEditRequestEvent() {
        super(SPC_EDIT_REQUEST_NEW);
        spc = null;
    }
    
    public SPCEditRequestEvent(SpecialistRepairCentre toEdit) {
        super(SPC_EDIT_REQUEST_EXISTING);
        spc = toEdit;
    }
    
    public SPCEditRequestEvent(Object source, EventTarget target) {
        super(source, target, SPC_EDIT_REQUEST_NEW);
    }
    
    public SpecialistRepairCentre getSPC() {
        return spc;
    }

    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (SPCEditRequestEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends SPCEditRequestEvent> getEventType() {
        return (EventType<? extends SPCEditRequestEvent>) super.getEventType();
    }
}

