package exceptions;

import db_package.CompanyDBDAO;
import db_package.CustomerDBDAO;
import facade.AdminFacade;
import utils.AdminConstants;
import utils.CompanyConstants;
import utils.CustomerConstants;

public class LogInException extends Exception{
	
	private String message;
	
	public LogInException(CompanyDBDAO company){
		this.message = CompanyConstants.LOGIN_FAILED;
	}
	
	public LogInException(CustomerDBDAO customer){
		this.message = CustomerConstants.LOGIN_FAILED;
	}
	
	public LogInException(AdminFacade admin){
		this.message = AdminConstants.LOGIN_FAILED;
	}
	
	public LogInException(String message){
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
