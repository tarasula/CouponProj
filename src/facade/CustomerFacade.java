
package facade;

import java.util.ArrayList;
import java.util.Collection;

import db_package.Coupon;
import db_package.CouponDBDAO;
import db_package.CouponType;
import db_package.CustomerDBDAO;
import util.CheckCouponPrice;

public class CustomerFacade implements CouponClientFacade {

	
	private CustomerDBDAO customerDAO;
	private CouponDBDAO couponDAO;
	private static String customerName;

	public CustomerFacade() {
		couponDAO = new CouponDBDAO();
		customerDAO = new CustomerDBDAO();
	}

	public void purchaseCoupon(Coupon coup) {
		couponDAO.purchaseCoupon(coup);
	}

	public Collection<Coupon> getAllPurchasedCoupons() {
		return customerDAO.getCoupons();
	}

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) {
		ArrayList<Coupon> purchasedCouponByType = new ArrayList<>();
		ArrayList<Coupon> allPurchasedCoupons = (ArrayList<Coupon>) customerDAO.getCoupons();
		for (int i = 0; i < allPurchasedCoupons.size(); i++) {
			if (allPurchasedCoupons.get(i).getType().toString().trim().equals(type.toString())) {
				purchasedCouponByType.add(allPurchasedCoupons.get(i));
			}
		}
		return purchasedCouponByType;
	}

	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) {
		int count = CheckCouponPrice.countOfNumersAfterPoint(price);
		ArrayList<Coupon> purchasedCouponByPrice = new ArrayList<>();
		ArrayList<Coupon> allPurchasedCoupons = (ArrayList<Coupon>) customerDAO.getCoupons();
		double correctPrice = 0;
//		allPurchasedCoupons = (ArrayList<Coupon>) CheckCouponPrice.fixDBPrice(allPurchasedCoupons, price);
		for (int i = 0; i < allPurchasedCoupons.size(); i++) {
			if (count == 1) {
				correctPrice = CheckCouponPrice.getCorrectPrice(allPurchasedCoupons.get(i).getPrice(), count);
				if (correctPrice == price) {
					purchasedCouponByPrice.add(allPurchasedCoupons.get(i));
				}
			} else if (count == 2) {
				correctPrice = CheckCouponPrice.getCorrectPrice(allPurchasedCoupons.get(i).getPrice(), count);
				if (correctPrice == price) {
					purchasedCouponByPrice.add(allPurchasedCoupons.get(i));
				}
			}else{
				if(allPurchasedCoupons.get(i).getPrice() == price){
					purchasedCouponByPrice.add(allPurchasedCoupons.get(i));
				}
			}
		}
		return purchasedCouponByPrice;
	}

	public static String getCustomerName() {
		return customerName;
	}

	public static void setCustomerName(String customerName) {
		CustomerFacade.customerName = customerName;
	}

	@Override
	public CouponClientFacade login(String name, String password, String clienType) {
		CustomerFacade.setCustomerName(name);
		if (customerDAO.login(name, password)) {
			return this;
		}
		return null;
	}

}
