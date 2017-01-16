/**
 * 
 */
package db_package;

import java.util.Collection;

/**
 * Customer Interface for speaking with DB  
 * @author Andrey Orlov
 * @version 1.0
 */
public interface CouponDAO {
		/**
		 * Create coupon method 
		 * @param crCoup - coupon for creating
		 * @throws Exception if coupon already exist
		 */
	public void createCoupon(Coupon crCoup) throws Exception;
		/**
		 * Remove coupon method 
		 * @param rmCoup - coupon for removing
		 * @throws Exception if no coupon for removing
		 */
	public void removeCoupon(Coupon rmCoup)throws Exception;
		/**
		 * Update coupon method 
		 * @param upCoup - coupon for updating
		 * @throws Exception if no coupon for update 
		 */
	public void updateCoupon(Coupon upCoup) throws Exception;
		/**
		 * Get coupon by specific ID
		 * @param id - Coupon id
		 * @return Coupon object from DB
		 * @throws Exception if Coupon with specific ID does not exist
		 */
	public Coupon getCoupon(long id)throws Exception;
		/**
		 * Get all Coupons method
		 * @return list with all Coupons
		 * @throws Exception if the method have SQL exceptions
		 */
	public Collection<Coupon> getAllCoupon()throws Exception;
		/**
		 * Get specific coupon by type
		 * @param coupType - Type of Coupon
		 * @return list of coupons with specific type
		 * @throws Exception if the method have SQL exceptions
		 */
	public Collection<Coupon> getCouponByType(CouponType coupType)throws Exception;
		/**
		 * Purchase method 
		 * @param coupon - specific coupon for purchase
		 * @throws Exception if the coupon is not exist or if customer is already by the coupon
		 */
	public void purchaseCoupon(Coupon coupon) throws Exception;
	
}
