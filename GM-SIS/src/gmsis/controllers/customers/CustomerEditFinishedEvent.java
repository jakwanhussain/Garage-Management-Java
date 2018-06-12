/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.controllers.customers;

import gmsis.models.customers.Customer;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

/**
 *
 * @author filip
 */
public class CustomerEditFinishedEvent extends Event {
    public static final EventType<CustomerEditFinishedEvent> CUSTOMER_EDIT_FINISHED_ANY = new EventType<>(Event.ANY, "customer-edit-finished");
    public static final EventType<CustomerEditFinishedEvent> CUSTOMER_EDIT_FINISHED_CANCELLED = new EventType<>(CUSTOMER_EDIT_FINISHED_ANY, "customer-edit-cancelled");
    public static final EventType<CustomerEditFinishedEvent> CUSTOMER_EDIT_FINISHED_COMPLETED = new EventType<>(CUSTOMER_EDIT_FINISHED_ANY, "customer-edit-completed");
    
    private Customer customer;

    private CustomerEditFinishedEvent(EventType<CustomerEditFinishedEvent> t, Customer customer) {
        super(t);
        this.customer = customer;
    }
    
    public static CustomerEditFinishedEvent cancelled(Customer c) {
        return new CustomerEditFinishedEvent(CUSTOMER_EDIT_FINISHED_CANCELLED, c);
    }
    
    public static CustomerEditFinishedEvent completed(Customer c) {
        return new CustomerEditFinishedEvent(CUSTOMER_EDIT_FINISHED_COMPLETED, c);
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (CustomerEditFinishedEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends CustomerEditFinishedEvent> getEventType() {
        return (EventType<? extends CustomerEditFinishedEvent>) super.getEventType();
    }

}
