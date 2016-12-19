/**
 * 
 */
package db_package;

import java.util.Collection;

/**
 * @author P0021787
 *
 */
public interface CustomerDAO {
	
	public void createCustomer(Customer crCust);
	public void removeCustomer(Customer rmCust);
	public void updateCustomer(Customer upCust);
	public Customer getCustomer(long id);
	public Collection<Customer> getAllCustomer();
	public Collection<Coupon> getCoupons();
	public boolean login(String custName, int password);
	
	

}
