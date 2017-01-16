
package facade;

import java.util.ArrayList;
import java.util.Collection;

import db_package.Coupon;
import db_package.CouponDBDAO;
import db_package.CouponType;
import db_package.CustomerDBDAO;
import exceptions.ProjectException;
import util.CheckCouponPrice;
/**
 * This class is Customer business-layer level that uses for speaking 
 * with Customer credentials with DAO-layer.
 * 
 * @author  Andrey Orlov
 * @version 1.0
 * 
 */
public class CustomerFacade implements CouponClientFacade {

	/**Link to Customer DAO-layer*/
	private CustomerDBDAO customerDAO;
	
	/**Link to Coupon DAO-layer*/
	private CouponDBDAO couponDAO;
	/**Field Customer name*/
	private static String customerName;

	/**
	 * Create objects for speaking with DAO-layer
	 */
	public CustomerFacade() {
		couponDAO = new CouponDBDAO();
		customerDAO = new CustomerDBDAO();
	}

	/**
	 * Method for purchase specific Coupon
	 * @param coup - Coupon object with fields
	 */
	public void purchaseCoupon(Coupon coup) {
		try {
			couponDAO.purchaseCoupon(coup);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method for get all purchased Coupons
	 * @return list of Coupons
	 */
	public Collection<Coupon> getAllPurchasedCoupons() {
		try {
			return customerDAO.getCoupons();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Method for get all purchased Coupons by type
	 * @param type - Coupon type
	 * @return list of Coupon with specific type
	 */
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) {
		ArrayList<Coupon> purchasedCouponByType = new ArrayList<>();
		ArrayList<Coupon> allPurchasedCoupons = null;
		try {
			allPurchasedCoupons = (ArrayList<Coupon>) customerDAO.getCoupons();
		for (int i = 0; i < allPurchasedCoupons.size(); i++) {
			if (allPurchasedCoupons.get(i).getType().toString().trim().equals(type.toString())) {
				purchasedCouponByType.add(allPurchasedCoupons.get(i));
			}
		}
			if(purchasedCouponByType.isEmpty()){
				throw new ProjectException("This user does not have purchased Coupon by this type.");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return purchasedCouponByType;
	}

	/**
	 * Method for get all purchased Coupons by price
	 * @param price - Coupon price
	 * @return list of Coupon with specific price
	 */
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) {
		int count = CheckCouponPrice.countOfNumersAfterPoint(price);
		ArrayList<Coupon> purchasedCouponByPrice = new ArrayList<>();
		ArrayList<Coupon> allPurchasedCoupons = null;
		try {
			allPurchasedCoupons = (ArrayList<Coupon>) customerDAO.getCoupons();
		double correctPrice = 0;
		for (int i = 0; i < allPurchasedCoupons.size(); i++) {
			if (count == 1) {
				correctPrice = CheckCouponPrice.getCorrectPrice(allPurchasedCoupons.get(i).getPrice(), count);
				if (correctPrice <= price) {
					purchasedCouponByPrice.add(allPurchasedCoupons.get(i));
				}
			} else if (count == 2) {
				correctPrice = CheckCouponPrice.getCorrectPrice(allPurchasedCoupons.get(i).getPrice(), count);
				if (correctPrice <= price) {
					purchasedCouponByPrice.add(allPurchasedCoupons.get(i));
				}
			}else{
				if(allPurchasedCoupons.get(i).getPrice() <= price){
					purchasedCouponByPrice.add(allPurchasedCoupons.get(i));
				}
			}
		}
			if(purchasedCouponByPrice.isEmpty()){
				throw new ProjectException("This user does not have purchased Coupon by this price.");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return purchasedCouponByPrice;
	}

	/**
	 * Method for get Customer name that logged in.
	 * @return Company name
	 */
	public static String getCustomerName() {
		return customerName;
	}

	/**
	 * Method for set Customer name that logged in.
	 * @param customerName - Customer name for set
	 */
	public static void setCustomerName(String customerName) {
		CustomerFacade.customerName = customerName;
	}

	/**
	 * Method to login with Customer credential
	 * @param name - Customer name
	 * @param password - Customer password
	 * @param clienType - Type of client (Customer, Company, Administrator)
	 * @return CustomerFacade Object
	 */
	@Override
	public CouponClientFacade login(String name, String password, String clienType) {
		CustomerFacade.setCustomerName(name);
		try {
			if (customerDAO.login(name, password)) {
				return this;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

}
