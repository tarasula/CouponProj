package db_package;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import exceptions.ProjectException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

public class MainTest {

	public static void main(String[] args) throws ParseException, SQLException {

		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
	     Date endDate = dateFormat.parse("2015-07-22");
	     Date startDate = dateFormat.parse("2018-09-12");
	     
//		 CompanyFacade companyF = (CompanyFacade) CouponSystem.getInstance().login("Aplied Materrials", "000", "Company");
//		 CompanyFacade companyF = (CompanyFacade) CouponSystem.getInstance().login("Ness", "111", "Company");
//		 CompanyFacade companyF = (CompanyFacade) CouponSystem.getInstance().login("Microsoft", "333", "Company");
		 Coupon coupon = new Coupon();
		 coupon.setAmount(3);
		 try {
			coupon.setEndDate(endDate);
		} catch (ProjectException e) {
			//TODO error message + stop the system
			System.err.println(e.getMessage());
		}
		 coupon.setImage("image2");
		 coupon.setStartDate(startDate);
		 coupon.setPrice(99.5);
		 coupon.setTitle("Hello999");
		 coupon.setMessage("Izik pizza");
		 coupon.setType(CouponType.FOOD);
		 
		 
//		 companyF.createCoupon(coupon);//+
//		 companyF.removeCoupon(coupon);//+
//		 companyF.updateCoupon(coupon);//+
//		 checkList(companyF.getAllCoupons());// + 
//		 System.out.println(companyF.getCoupon(16));//+
//		 checkList(companyF.getCouponsByType(CouponType.FOOD));//+

		CustomerFacade customerF = (CustomerFacade) CouponSystem.getInstance().login("Shadrin", "ivanov", "Customer");
//		CustomerFacade customerF = (CustomerFacade) CouponSystem.getInstance().login("Orlov", "123", "Customer");
//		 Coupon coup = new Coupon(7, "title18", "message17", "13", 44.2, "2015-11-23", "2015-12-23", 7732, CouponType.TREVELLING);
//		 customerF.purchaseCoupon(coupon);//+
//		 checkList(customerF.getAllPurchasedCoupons());//+
		 checkList(customerF.getAllPurchasedCouponsByPrice(7.5));//+ Till price
//		 checkList(customerF.getAllPurchasedCouponsByType(CouponType.ELECTRICITY));//+

		AdminFacade adminF = (AdminFacade) CouponSystem.getInstance().login("Admin", "1234", "Admin");
		Company company = new Company();
		company.setCompName("Aplied Materrials");
		company.setPassword("066");
		company.setEmail("Aplied@mail");
//		
//		 adminF.createCompany(company);//+
//		 adminF.removeCompany(company);//+
//		 adminF.updateCompany(company);//+
//		 System.out.println(adminF.getCompany(3));//+
//		 checkList(adminF.getAllCompanies());//+
//
//
		Customer cost = new Customer();
		cost.setCustName("Shadrin");
		cost.setPassword("ivanov");
//		 adminF.createCustomer(cost);//+
//		 adminF.removeCustomer(cost);//+
//		 adminF.updateCustomer(cost);//+
//		 System.out.println(adminF.getCustomer(23));//+
//		 checkList(adminF.getAllCustomers());//+
		 CouponSystem.getInstance().shutdown();
	}

	private static <E> void checkList(Collection<E> collection) {
		if(collection.isEmpty()){
			System.out.println("The List is empty!");
		}else{
			for (int i = 0; i < collection.size(); i++) {
				System.out.println(((List<E>) collection).get(i));
			}
		}
	}
}
