/**
 * 
 */
package db_package;

import java.util.Collection;

/**
 * class for create Customer 
 * @author Andrey Orlov
 */
public class Customer {
	/**Field ID for Customer identity */
	private long id;
	
	/**Fields custName - The name of Customer, password - the password of Customer */
	private String custName, password;
	
	/**Collection member for Customer Coupons */
	private Collection<Coupon> coupons;

	/**
	 * Create object constructor
	 */
	public Customer() {
		super();
	}

	/**
	 * Get Customer ID method
	 * @return id of Customer
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set Customer ID method
	 * @param id - for set Customer id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get Customer name method
	 * @return name of Customer
	 */
	public String getCustName() {
		return custName;
	}

	/**
	 * Set Customer name method
	 * @param custName - for set Customer name
	 */
	public void setCustName(String custName) {
		this.custName = custName.trim();
	}

	/**
	 * Get Customer password method
	 * @return password of Customer
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set Customer password method
	 * @param password - for set Customer password
	 */
	public void setPassword(String password) {
		this.password = password.trim();
	}

	/**
	 * Get Customer Coupons method
	 * @return list of Customer Coupons
	 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * Set Customer Coupons method
	 * @param coupons - for set Customer coupons
	 */
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

	/**
	 * Method for print Customer
	 */
	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", coupons=" + coupons
				+ "]";
	}
	
}
