package db_package;

import java.util.Collection;

/**
 * Customer Interface for speaking with DB  
 * @author Andrey Orlov
 * @version 1.0
 */
public interface CustomerDAO {
	/**
	 * Create customer method 
	 * @param crCust - customer for creating
	 * @throws Exception if customer already exist
	 */
	public void createCustomer(Customer crCust) throws Exception;
	/**
	 * Remove customer method 
	 * @param rmCust - customer for removing
	 * @throws Exception if no customer for removing
	 */
	public void removeCustomer(Customer rmCust)throws Exception;
	/**
	 * Update customer method 
	 * @param upCust - customer for updating
	 * @throws Exception if no customer for update 
	 */
	public void updateCustomer(Customer upCust)throws Exception;
	/**
	 * Get customer by specific ID
	 * @param id - Customer id
	 * @return Customer object from DB
	 * @throws Exception if Customer with specific ID does not exist
	 */
	public Customer getCustomer(long id)throws Exception;
	/**
	 * Get all Customers method
	 * @return list with all customers
	 * @throws Exception if the method have SQL exceptions
	 */
	public Collection<Customer> getAllCustomer()throws Exception;
	/**
	 * Get specific customer coupons method
	 * @return list of company coupons 
	 * @throws Exception if company does not have coupons
	 */
	public Collection<Coupon> getCoupons() throws Exception;
	/**
	 * Login method 
	 * @param custName - Customer name
	 * @param password - Customer password
	 * @return true or false
	 * @throws Exception if the login is failed
	 */
	public boolean login(String custName, String password)throws Exception;
}
