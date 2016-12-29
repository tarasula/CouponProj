package db_package;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

public class MainTest {

	public static void main(String[] args) throws ParseException, SQLException {

		 Thread t1 = new Thread(new DailyCouponExpirationTask());
		 t1.start();

		CompanyFacade companyF = (CompanyFacade) CouponSystem.getInstance().login("Amdocs", "222", "Company");
		 Coupon coupon = new Coupon(2, "new title2", "message67", "33", 9.2,
		 "2015-11-23", "2018-12-23", 7732, CouponType.TREVELLING);
//		 companyF.createCoupon(coupon);
//		 companyF.removeCoupon(coupon);
//		 companyF.updateCoupon(coupon);
//		 checkList(companyF.getAllCoupons());//print price like - 44.20000023423472
//		 System.out.println(companyF.getCoupon(8));
//		 checkList(companyF.getCouponsByType(CouponType.TREVELLING));

		CustomerFacade customerF = (CustomerFacade) CouponSystem.getInstance().login("Orlov", "998", "Customer");
//		 Coupon coup = new Coupon(7, "title18", "message17", "13", 44.2,
//		 "2015-11-23", "2015-12-23", 7732, CouponType.TREVELLING);
//		 customerF.purchaseCoupon(coupon);
//		 checkList(customerF.getAllPurchasedCoupons());
//		 checkList(customerF.getAllPurchasedCouponsByPrice(44.2));
//		 checkList(customerF.getAllPurchasedCouponsByType(CouponType.TREVELLING));

		AdminFacade adminF = (AdminFacade) CouponSystem.getInstance().login("Admin", "1234", "Admin");
		Company company = new Company(3, "Amdocs", "222", "amdocs@rambler.ru");
//		 Company company = new Company(3, "Google", "ggg",
//		 "google@rambler.ru");
//		 adminF.createCompany(company);
//		 adminF.removeCompany(company);
//		 adminF.updateCompany(company);
//		 System.out.println(adminF.getCompany(3));
//		checkList(adminF.getAllCompanies());

		Customer cost = new Customer(7, "Orlov", "998");
//		Customer cost = new Customer(1, "Mark Zuckeberg", "bla");
//		 adminF.createCustomer(cost);
//		 adminF.removeCustomer(cost);
//		 adminF.updateCustomer(cost);
//		 System.out.println(adminF.getCustomer(0));
//		 checkList(adminF.getAllCustomers());

//		Scanner scan = new Scanner(System.in);
//		while (true) {
//			if (scan.hasNext()) {
//				scan.nextLine().equals("exit");
//				return;
//			}
//		}

	}

	private static <E> void checkList(Collection<E> collection) {
		for (int i = 0; i < collection.size(); i++) {
			System.out.println(((List<E>) collection).get(i));
		}
	}

	// every night at 2am the task will be run
	// public void startDailyCouponExpirationTask(){
	// Calendar today = Calendar.getInstance();
	// today.set(Calendar.HOUR_OF_DAY, 2);
	// today.set(Calendar.MINUTE, 0);
	// today.set(Calendar.SECOND, 0);
	//
	// Timer timer = new Timer();
	// Thread expirationThread = new Thread(new DailyCouponExpirationTask());
	// timer.schedule(expirationThread, today.getTime());
	// }
}
