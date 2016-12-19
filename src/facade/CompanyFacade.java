package facade;

import java.util.Collection;

import db_package.Coupon;
import db_package.CouponType;

public class CompanyFacade implements CouponClientFacade {
	
	//TODO add DAO

	public CompanyFacade(){
		
	}
	
	public void createCoupon(Coupon coup){
		
	}
	
	public void removeCoupon(Coupon coup){
		
	}
	
	public void updateCoupon(Coupon coup){
		
	}
	
	public Coupon getCoupon(int id){
		return null;
	}
	
	public Collection<Coupon> getAllCoupons(){
		return null;
	}
	
	public Collection<Coupon> getCouponsByType(CouponType coupType){
		return null;
	}
	
	@Override
	public CouponClientFacade login(String name, int password, String clienType) {
		// TODO Auto-generated method stub
		return null;
	}

}
