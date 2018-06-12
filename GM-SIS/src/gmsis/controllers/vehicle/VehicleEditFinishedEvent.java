/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.vehicle;

import gmsis.models.vehicles.Vehicle;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;


/**
 * Created by sabrinasaytac on 02/03/2017.
 */
public class VehicleEditFinishedEvent extends Event {

    public static final EventType<VehicleEditFinishedEvent> VEHICLE_EDIT_FINISHED_ANY = new EventType<>(javafx.event.Event.ANY, "vehicle-edit-finished");
    public static final EventType<VehicleEditFinishedEvent> VEHICLE_EDIT_FINISHED_CANCELLED = new EventType<>(VEHICLE_EDIT_FINISHED_ANY, "vehicle-edit-cancelled");
    public static final EventType<VehicleEditFinishedEvent> VEHICLE_EDIT_FINISHED_COMPLETED = new EventType<>(VEHICLE_EDIT_FINISHED_ANY, "vehicle-edit-completed");

    private Vehicle vehicle;

    private VehicleEditFinishedEvent(EventType<VehicleEditFinishedEvent> type, Vehicle vehicle) {
        super(type);
        this.vehicle = vehicle;
    }

    public static VehicleEditFinishedEvent cancelled(Vehicle vehicle) {
        return new VehicleEditFinishedEvent(VEHICLE_EDIT_FINISHED_CANCELLED, vehicle);
    }

    public static VehicleEditFinishedEvent completed(Vehicle vehicle) {
        return new VehicleEditFinishedEvent(VEHICLE_EDIT_FINISHED_COMPLETED, vehicle);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public javafx.event.Event copyFor(Object newSource, EventTarget newTarget) {
        return super.copyFor(newSource, newTarget);
    }

    @Override
    public EventType<? extends VehicleEditFinishedEvent> getEventType() {
        return (EventType<? extends VehicleEditFinishedEvent>) super.getEventType();
    }

}
