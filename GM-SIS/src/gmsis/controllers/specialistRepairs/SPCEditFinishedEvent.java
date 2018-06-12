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
public class SPCEditFinishedEvent extends Event {
    public static final EventType<SPCEditFinishedEvent> SPC_EDIT_FINISHED_ANY = new EventType<>(Event.ANY, "spc-edit-finished");
    public static final EventType<SPCEditFinishedEvent> SPC_EDIT_FINISHED_CANCELLED = new EventType<>(SPC_EDIT_FINISHED_ANY, "spcr-edit-cancelled");
    public static final EventType<SPCEditFinishedEvent> SPC_EDIT_FINISHED_COMPLETED = new EventType<>(SPC_EDIT_FINISHED_ANY, "spc-edit-completed");
    
    private SpecialistRepairCentre spc;

    private SPCEditFinishedEvent(EventType<SPCEditFinishedEvent> t, SpecialistRepairCentre spc) {
        super(t);
        this.spc = spc;
    }
    
    public static SPCEditFinishedEvent cancelled(SpecialistRepairCentre c) {
        return new SPCEditFinishedEvent(SPC_EDIT_FINISHED_CANCELLED, c);
    }
    
    public static SPCEditFinishedEvent completed(SpecialistRepairCentre c) {
        return new SPCEditFinishedEvent(SPC_EDIT_FINISHED_COMPLETED, c);
    }

    public SpecialistRepairCentre getSPC() {
        return spc;
    }

    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (SPCEditFinishedEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends SPCEditFinishedEvent> getEventType() {
        return (EventType<? extends SPCEditFinishedEvent>) super.getEventType();
    }

}