/**
 * 
 */
package db_package;

import java.util.Collection;

/**
 * @author P0021787
 *
 */
public interface CouponDAO {

	public void createCoupon(Coupon crCoup);
	public void removeCoupon(Coupon rmCoup);
	public void updateCoupon(Coupon upCoup);
	public Coupon getCoupon(long id);
	public Collection<Coupon> getAllCoupon();
	public Collection<Coupon> getCouponByType(CouponType coupType);
	
}
