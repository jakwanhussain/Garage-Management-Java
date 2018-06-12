/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.ui;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.control.Tab;

/**
 * Created by filip on 28/02/17.
 */
public class TabChangeEvent extends Event {
    public static final EventType<TabChangeEvent> TAB_CHANGE = new EventType<>(Event.ANY, "tab-change");
    private Tab selected;
    public TabChangeEvent(Tab selected) {
        super(TAB_CHANGE);
        this.selected = selected;
    }

    public Tab getTab() {
        return selected;
    }
}
