/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.users;

import gmsis.models.authentication.User;
import gmsis.models.bookings.Mechanic;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;
import org.jetbrains.annotations.NotNull;

/**
 * Created by filip on 23/03/17.
 */
public class MechanicChangeEvent extends Event {
    public static final EventType<MechanicChangeEvent> ANY = new EventType<>(Event.ANY, "mechanic-change");
    public static final EventType<MechanicChangeEvent> SAVE_MECHANIC = new EventType<>(ANY, "mechanic-change-save");
    public static final EventType<MechanicChangeEvent> DELETE_MECHANIC = new EventType<>(ANY, "mechanic-change-delate");

    private User user;
    private Mechanic mechanic;

    private MechanicChangeEvent(@NamedArg("eventType") EventType<? extends Event> eventType, User user, Mechanic mechanic) {
        super(eventType);
        this.user = user;
        this.mechanic = mechanic;
    }

    public static MechanicChangeEvent save(@NotNull User user, @NotNull Mechanic mechanic) {
        return new MechanicChangeEvent(SAVE_MECHANIC, user, mechanic);
    }

    public static MechanicChangeEvent delete(@NotNull User user, @NotNull Mechanic mechanic) {
        return new MechanicChangeEvent(DELETE_MECHANIC, user, mechanic);
    }

    public User getUser() {
        return user;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }
}
