/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.persistence.customers;

import gmsis.models.bookings.Booking;
import gmsis.models.customers.Bill;
import gmsis.persistence.Repository;

/**
 *
 * @author filip
 */
public interface BillRepository extends Repository<Bill> {
    public Bill getOrCreateBill(Booking booking) throws Exception;

}
