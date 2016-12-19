package db_package;

import exceptions.ProjectException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CouponClientFacade;
import facade.CustomerFacade;

public class CouponSystem {

	private CouponDBDAO couponDAO;
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
	//Why we need name and password? 
	public CouponClientFacade login(String name, int password, String type) {
		//TODO need to add name & password to IF
		if (type.equals("ADMIN")) {
			return new AdminFacade();
		} else if (type.equals("COMPANY")) {
			return new CompanyFacade();
		} else if (type.equals("CUSTOMER")) {
			return new CustomerFacade();
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
