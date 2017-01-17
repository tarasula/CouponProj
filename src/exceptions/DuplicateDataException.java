
package exceptions;

import db_package.Company;
import db_package.Coupon;
import db_package.Customer;

/**
 * @author Andrey Orlov
 *
 */
public class DuplicateDataException extends Exception{

	private String message = "Can't insert the  ,is already exist!";

	
	/**
	 * Create exception object and generate exception message
	 * @param company - Exception description
	 */
	public DuplicateDataException(Company company) {
		StringBuffer stringBuffer = new StringBuffer(message);
		stringBuffer.insert(17, "Company " + company.getCompName());
	}

	/**
	 * Create exception object and generate exception message
	 * @param customer - Exception description
	 */
	public DuplicateDataException(Customer customer) {
		StringBuffer stringBuffer = new StringBuffer(message);
		stringBuffer.insert(17, "Customer " + customer.getCustName());
	}
	
	/**
	 * Create exception object and generate exception message
	 * @param coupon - Exception description
	 */
	public DuplicateDataException(Coupon coupon) {
		StringBuffer stringBuffer = new StringBuffer(message);
		stringBuffer.insert(17, "Coupon " + coupon.getTitle());
	}
	
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
