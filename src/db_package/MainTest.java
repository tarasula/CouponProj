package db_package;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import exceptions.OverallException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

public class MainTest {

	public static void main(String[] args) throws ParseException, SQLException {

		/*
		 * Creating Date for Coupon
		 */
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
	     Date endDate = dateFormat.parse("2015-07-22");
	     Date startDate = dateFormat.parse("2018-09-12");
	     
	     /*
	      * Creating Coupon
	      */
	     Coupon coupon = new Coupon();
	     coupon.setAmount(3);
	     coupon.setImage("image2");
	     coupon.setStartDate(startDate);
	     coupon.setPrice(99.5);
	     coupon.setTitle("Hello999");
	     coupon.setMessage("Izik pizza");
	     coupon.setType(CouponType.FOOD);
	     try {
	    	 coupon.setEndDate(endDate);
	     } catch (OverallException e) {
	    	 System.err.println(e.getMessage());
	     }
	     
	     /*
	      * Company LogIn
	      */
		 CompanyFacade companyF = (CompanyFacade) CouponSystem.getInstance().login("Aplied Materrials", "066", ClientType.COMPANY);
		
		 /*
		  * Company actions
		  */
//		 companyF.createCoupon(coupon);
//		 companyF.updateCoupon(coupon);
//		 companyF.getAllCoupons());
//		 System.out.println(companyF.getCoupon(16));
//		 companyF.getCouponsByType(CouponType.FOOD));
//		 companyF.removeCoupon(coupon);
		 
		 
		 /*
		  * Customer LogIn
		  */
		CustomerFacade customerF = (CustomerFacade) CouponSystem.getInstance().login("Shadrin", "ivanov", ClientType.CUSTOMER);
		
		/*
		 * Customer actions
		 */
//		 customerF.purchaseCoupon(coupon);
//		 customerF.getAllPurchasedCoupons();
//		 customerF.getAllPurchasedCouponsByPrice(7.5);
//		 customerF.getAllPurchasedCouponsByType(CouponType.ELECTRICITY);

		/*
		 * Admin LogIn
		 */
		AdminFacade adminF = (AdminFacade) CouponSystem.getInstance().login("Admin", "1234", ClientType.ADMIN);
		
		/*
		 * Create Company
		 */
		Company company = new Company();
		company.setCompName("Aplied Materrials");
		company.setPassword("066");
		company.setEmail("Aplied@mail");

		/*
		 * Create Customer
		 */
		Customer cost = new Customer();
		cost.setCustName("Shadrin");
		cost.setPassword("ivanov");
		
		/*
		 * Admin actions
		 */
//		 adminF.createCompany(company);
//		 adminF.updateCompany(company);
//		 System.out.println(adminF.getCompany(3));
//		 adminF.getAllCompanies();
//		 adminF.removeCompany(company);

		
//		 adminF.createCustomer(cost);
//		 adminF.removeCustomer(cost);
//		 adminF.updateCustomer(cost);
//		 System.out.println(adminF.getCustomer(23));
//		 adminF.getAllCustomers();
		
		/*
		 * ShutDown of system
		 */
		 CouponSystem.getInstance().shutdown();
	}
}
