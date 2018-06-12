/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.authentication;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Created by filip on 19/03/17.
 */
public class UserLoggedOutEvent extends Event {
    public static final EventType<UserLoggedOutEvent> LOGGED_OUT = new EventType<>(javafx.event.Event.ANY, "user-logged-out");

    public UserLoggedOutEvent() {
        super(LOGGED_OUT);
    }
}
