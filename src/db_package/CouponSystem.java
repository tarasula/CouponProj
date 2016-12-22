package db_package;

import exceptions.ProjectException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;

public class CouponSystem {

	private CouponDBDAO couponDAO;
	private AdminFacade adminFacade;
	private CompanyFacade companyFacade;
	private CustomerFacade customerFacade;
	private DailyCouponExpirationTask dceTask;

	public static Object key = new Object();
	public static CouponSystem instance = null;

	private CouponSystem() {
	}

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

	public CouponClientFacade login(String name, int password, String type) {
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
				e.printStackTrace();
			}
			return null;
		}
	}

	public void shutdown() {
		ConnectionPool.getInstance().closeAllConnection();
		dceTask.stopTask();
	}
}
