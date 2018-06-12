/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.customers;

import gmsis.models.customers.Bill;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * Created by filip on 21/03/17.
 */
public class SettleBillEvent extends Event {
    public static final EventType<SettleBillEvent> SETTLE = new EventType<>(Event.ANY, "settle-bill");

    private Bill bill;

    public SettleBillEvent(Bill bill) {
        super(SETTLE);
        this.bill = bill;
    }

    public Bill getBill() {
        return bill;
    }
}
