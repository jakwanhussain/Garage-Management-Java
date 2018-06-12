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
public class CustomerEditRequestEvent extends Event {
    public static final EventType<CustomerEditRequestEvent> CUSTOMER_REQUEST_ANY = new EventType<>(Event.ANY, "customer-request");
    public static final EventType<CustomerEditRequestEvent> CUSTOMER_EDIT_REQUEST_ANY = new EventType<>(CUSTOMER_REQUEST_ANY, "customer-edit-request");
    public static final EventType<CustomerEditRequestEvent> CUSTOMER_EDIT_REQUEST_NEW = new EventType<>(CUSTOMER_EDIT_REQUEST_ANY, "customer-edit-request-new");
    public static final EventType<CustomerEditRequestEvent> CUSTOMER_EDIT_REQUEST_EXISTING = new EventType<>(CUSTOMER_EDIT_REQUEST_ANY, "customer-edit-request-existing");
    public static final EventType<CustomerEditRequestEvent> CUSTOMER_SHOW_PROFILE = new EventType<>(CUSTOMER_REQUEST_ANY, "customer-show-profile");
    
    private Customer customer;

    private CustomerEditRequestEvent(EventType<CustomerEditRequestEvent> type, Customer c) {
        super(type);
        customer = c;
    }

    public CustomerEditRequestEvent() {
        this(CUSTOMER_EDIT_REQUEST_NEW, (Customer) null);
    }
    
    public CustomerEditRequestEvent(Customer toEdit) {
        this(CUSTOMER_EDIT_REQUEST_EXISTING, toEdit);
    }

    public static CustomerEditRequestEvent showProfile(Customer c) {
        return new CustomerEditRequestEvent(CUSTOMER_SHOW_PROFILE, c);
    }
    
    public CustomerEditRequestEvent(Object source, EventTarget target) {
        super(source, target, CUSTOMER_EDIT_REQUEST_NEW);
    }
    
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public Event copyFor(Object newSource, EventTarget newTarget) {
        return (CustomerEditRequestEvent) super.copyFor(newSource, newTarget); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventType<? extends CustomerEditRequestEvent> getEventType() {
        return (EventType<? extends CustomerEditRequestEvent>) super.getEventType();
    }
}
