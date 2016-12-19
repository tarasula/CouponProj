
package facade;
import java.util.Collection;

import db_package.Coupon;
import db_package.CouponDBDAO;

public class CustomerFacade implements CouponClientFacade {

	//TODO add DAO
	private CouponDBDAO couponDAO;
	public CustomerFacade() {
		
	}
	//במחלקה CustomerFacade תהיה מתודה המאפשרת רכישת קופון – purchaseCoupon(Coupon). 
	//מעבר לכך שדרוש עדכון כמות הקופונים שנותרה – 
	//יש לבדוק אם בכלל נותרו קופונים ולוודא שהלקוח לא רכש בעבר קופון זהה...
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
