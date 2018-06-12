/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.customers;

import gmsis.models.customers.Customer;
import gmsis.persistence.Repository;

import java.util.List;

/**
 *
 * @author filip
 */
public interface CustomerRepository extends Repository<Customer> {
    public List<Customer> findByName(String search);
    public List<Customer> findByRegistration(String search);
}
