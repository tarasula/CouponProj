package exceptions;

import db_package.Company;
import db_package.Coupon;
import db_package.Customer;

public class NoDataException extends Exception {
	
	private String message = "The  is not exist!";
	
	/**
	 * Create exception object and generate exception message
	 * @param company - Exception description
	 * @param message - Description of exception
	 */
	public NoDataException(Company company) {
		StringBuffer stringBuffer = new StringBuffer(message);
		stringBuffer.insert(4, "Company " + company.getCompName());
	}

	/**
	 * Create exception object and generate exception message
	 * @param customer - Exception description
	 * @param message - Description of exception
	 */
	public NoDataException(Customer customer) {
		StringBuffer stringBuffer = new StringBuffer(message);
		stringBuffer.insert(4, "Customer " + customer.getCustName());
	}
	
	/**
	 * Create exception object and generate exception message
	 * @param coupon - Exception description
	 * @param message - Description of exception
	 */
	public NoDataException(Coupon coupon) {
		StringBuffer stringBuffer = new StringBuffer(message);
		stringBuffer.insert(4, "Coupon " + coupon.getTitle());
	}
	
	public NoDataException(String message) {
		this.message = message;
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
