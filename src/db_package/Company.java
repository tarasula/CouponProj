/**
 * 
 */
package db_package;

import java.util.Collection;

/**
 * class for create Company 
 * @author Andrey Orlov
 */
public class Company {
	/**Field ID for Company identity */
	private long id;
	
	/**Fields compName - The name of Company, password - the password of Company, 
	 * email - the email of Company*/
	private String compName, password, email;
	
	/**Collection member for Company Coupons */
	private Collection<Coupon> coupon ;

	/**
	 * Create object constructor
	 */
	public Company() {
		super();
	}
	

	/**
	 * Get Company ID method
	 * @return id of Company
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set Company ID method
	 * @param id - for set Company id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get Company name method
	 * @return name of Company
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * Set Company name method
	 * @param compName - for set Company name
	 */
	public void setCompName(String compName) {
		this.compName = compName.trim();
	}

	/**
	 * Get Company password method
	 * @return password of Company
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set Company password method
	 * @param password - for set Company password
	 */
	public void setPassword(String password) {
		this.password = password.trim();
	}

	/**
	 * Get Company email method
	 * @return email of Company
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set Company email method
	 * @param email - for set Company email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get Company Coupons method
	 * @return list of Company Coupons
	 */
	public Collection<Coupon> getCoupon() {
		return coupon;
	}

	/**
	 * Set Company Coupons method
	 * @param coupon - for set Company coupons
	 */
	public void setCoupon(Collection<Coupon> coupon) {
		this.coupon = coupon;
	}

	/**
	 * Method for print Company
	 */
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email
				+ ", coupon=" + coupon + "]";
	}
	
}
