/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.controllers.authentication;

import gmsis.models.authentication.User;
import javafx.event.Event;
import javafx.event.EventType;

/**
 *
 * @author filip
 */
public class UserLoggedInEvent extends Event {
    public static final EventType<UserLoggedInEvent> LOGGED_IN = new EventType<>(Event.ANY, "user-logged-in");
    
    private User user;

    public UserLoggedInEvent(User u) {
        super(LOGGED_IN);
        this.user = u;
    }

    public User getUser() {
        return user;
    }
}
