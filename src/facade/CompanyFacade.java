package facade;

import java.util.Collection;

import db_package.Coupon;
import db_package.CouponDBDAO;
import db_package.CouponType;

public class CompanyFacade implements CouponClientFacade {
	
	//TODO add DAO
	private CouponDBDAO couponDAO;

	public CompanyFacade(){
		
	}
	
	public void createCoupon(Coupon coup){
		couponDAO.createCoupon(coup);
	}
	
	public void removeCoupon(Coupon coup){
		couponDAO.removeCoupon(coup);
	}
	
	public void updateCoupon(Coupon coup){
		couponDAO.updateCoupon(coup);
	}
	
	public Coupon getCoupon(long id){
		return couponDAO.getCoupon(id);
	}
	
	public Collection<Coupon> getAllCoupons(){
		return couponDAO.getAllCoupon();
	}
	
	public Collection<Coupon> getCouponsByType(CouponType coupType){
		return couponDAO.getCouponByType(coupType);
	}
	
	@Override
	public CouponClientFacade login(String name, int password, String clienType) {
		// TODO Auto-generated method stub
		return null;
	}

}
