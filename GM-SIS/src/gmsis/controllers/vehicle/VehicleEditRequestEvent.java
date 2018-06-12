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
 * Created by sabrinasaytac
 */
public class VehicleEditRequestEvent extends Event {
    public static final EventType<VehicleEditRequestEvent> VEHICLE_REQUEST_ANY = new EventType<>(Event.ANY, "vehicle-request");
    public static final EventType<VehicleEditRequestEvent> VEHICLE_EDIT_REQUEST_ANY = new EventType<>(VEHICLE_REQUEST_ANY, "vehicle-edit-request");
    public static final EventType<VehicleEditRequestEvent> VEHICLE_EDIT_REQUEST_NEW = new EventType<>(VEHICLE_EDIT_REQUEST_ANY, "vehicle-edit-request-new");
    public static final EventType<VehicleEditRequestEvent> VEHICLE_EDIT_REQUEST_EXISTING = new EventType<>(VEHICLE_EDIT_REQUEST_ANY, "vehicle-edit-request-existing");

    private Vehicle vehicle;

    private VehicleEditRequestEvent(EventType<VehicleEditRequestEvent> type, Vehicle vehicle) {
        super(type);
        this.vehicle = vehicle;
    }

    public VehicleEditRequestEvent() {
        this(VEHICLE_EDIT_REQUEST_NEW, (Vehicle) null);
    }

    public VehicleEditRequestEvent(Vehicle vehicle) {
        this(VEHICLE_EDIT_REQUEST_EXISTING, vehicle);
    }

    public VehicleEditRequestEvent(Object source, EventTarget target) {
        super(source, target, VEHICLE_EDIT_REQUEST_NEW);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return super.copyFor(newSource, newTarget);
    }

    @Override
    public EventType<? extends VehicleEditRequestEvent> getEventType() {
        return (EventType<? extends VehicleEditRequestEvent>) super.getEventType();
    }
}
