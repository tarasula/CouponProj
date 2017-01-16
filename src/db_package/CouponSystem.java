package db_package;

import exceptions.ProjectException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;

/**
 * Singleton, Factory pattern class, use for getting specific object of facades
 * @author Andrey Orlov 
 */

public class CouponSystem {

	/**Link to Administrator Facade-layer*/
	private AdminFacade adminFacade;
	
	/**Link to Company Facade-layer*/
	private CompanyFacade companyFacade;
	
	/**Link to Customer Facade-layer*/
	private CustomerFacade customerFacade;
	
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
		 Thread t1 = new Thread(new DailyCouponExpirationTask());
		 t1.start();
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
	 * @param clienType - Type of client (Company, Customer, Administrator)
	 * @return CouponClientFacade Object
	 */
	public CouponClientFacade login(String name, String password, String type) {
		if (type.equalsIgnoreCase("ADMIN") || type.equalsIgnoreCase("ADMINISTRATOR")) {
			adminFacade = new AdminFacade();
			return adminFacade.login(name, password, type);
			
		} else if (type.equalsIgnoreCase("COMPANY")) {
			companyFacade = new CompanyFacade();
			return companyFacade.login(name, password, type);
			
		} else if (type.equalsIgnoreCase("CUSTOMER")) {
			customerFacade = new CustomerFacade();
			return customerFacade.login(name, password, type);
			
		} else {
			try {
				throw new ProjectException("Log in failed, incorrect user type!");
			} catch (ProjectException e) {
				System.err.println(e.getMessage());
			}
			return null;
		}
	}

	/**
	 * Method which close all connections, stop Daily task and shutdown the system
	 */
	public void shutdown() {
		ConnectionPool.getInstance().closeAllConnection();
		dceTask.stopTask();
	}
}
