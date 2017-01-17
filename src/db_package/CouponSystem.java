package db_package;

import exceptions.LogInException;
import exceptions.OverallException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;

/**
 * Singleton, Factory pattern class, use for getting specific object of facades
 * @author Andrey Orlov 
 */

public class CouponSystem {

	/**Link to Daily Coupon Expiration Task*/
	private DailyCouponExpirationTask dceTask;

	/**Object for synchronize*/
	public static Object key = new Object();
	
	/**Instance of CouponSystem class*/
	public static CouponSystem instance = null;

	/**
	 * Create object constructor
	 */
	private CouponSystem() {
		 dceTask = new DailyCouponExpirationTask();
		 Thread dailyCouponExpirationTask = new Thread(new DailyCouponExpirationTask());
		 dailyCouponExpirationTask.start();
	}

	/**
	 * Synchronized get Instance method
	 * @return instance
	 */
	public static CouponSystem getInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new CouponSystem();
					
				}
			}
		}
		return instance;
	}

	/**
	 * Method to login with some credentials
	 * @param name - Name of client
	 * @param password - Password of client
	 * @param type - Type of client (Company, Customer, Administrator)
	 * @return CouponClientFacade Object
	 */
	public CouponClientFacade login(String name, String password, ClientType type) {
		CouponClientFacade resultFacade = null;
		
		switch(type){
			
		case ADMIN:
			
		case ADMINISTRATOR:
			resultFacade = new AdminFacade(); 
			break;
			
		case COMPANY:
			resultFacade = new CompanyFacade(); 
			break;
			
		case CUSTOMER: 
			resultFacade = new CustomerFacade(); 
			break;
			
		default:
			try {
				throw new LogInException("Log in failed, incorrect user type!");
			} catch (LogInException e) {
				System.err.println(e.getMessage());
			}
			break;
		}
	
			return resultFacade.login(name, password, type);	
	}

	/**
	 * Method which close all connections, stop Daily task and shutdown the system
	 */
	public void shutdown() {
		ConnectionPool.ifShutDownClicked = true;
		ConnectionPool.getInstance().closeAllConnection();
		dceTask.stopTask();
	}
}
