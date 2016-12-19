
package facade;
import java.util.Collection;

import db_package.Coupon;

public class CustomerFacade implements CouponClientFacade {

	//TODO add DAO
	
	public CustomerFacade() {
		
	}

	public void purchaseCoupon(Coupon coup){
		
	}
	
	public Collection<Coupon> getAllPurchasedCoupons(){
		return null;
	}
	
	public Collection<Coupon> getAllPurchasedCouponsByType(String type){
		return null;
	}
	
	public Collection<Coupon> getAllPurchasedCouponsByPrice(int price){
		return null;
	}
	
	@Override
	public CouponClientFacade login(String name, int password, String clienType) {
		// TODO Auto-generated method stub
		return null;
	}

}
