/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.users;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * Created by filip on 17/03/17.
 */
public class PasswordChangePopupFinishEvent extends Event {
    public final static EventType<PasswordChangePopupFinishEvent> ANY = new EventType<>(Event.ANY, "password-change-popup-finished");
    public final static EventType<PasswordChangePopupFinishEvent> CHANGED = new EventType<>(Event.ANY, "password-change-popup-finished-changed");
    public final static EventType<PasswordChangePopupFinishEvent> NOT_CHANGED = new EventType<>(Event.ANY, "password-change-popup-finished-not-changed");

    private String newPass;

    private PasswordChangePopupFinishEvent(@NamedArg("eventType") EventType<? extends Event> eventType) {
        super(eventType);
    }

    public static PasswordChangePopupFinishEvent changed(String newPass) {
        PasswordChangePopupFinishEvent e = new PasswordChangePopupFinishEvent(CHANGED);
        e.newPass = newPass;
        return e;
    }

    public static PasswordChangePopupFinishEvent notChanged() {
        return new PasswordChangePopupFinishEvent(NOT_CHANGED);
    }

    public String getNewPassword() {
        return newPass;
    }
}
