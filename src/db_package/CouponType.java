/**
 * 
 */
package db_package;

/**
 * Enum class use for Coupon class
 * @author Andrey Orlov
 */
public enum CouponType {
	RESTURANS("RESTURANS"), 
	ELECTRICITY("ELECTRICITY"), 
	FOOD("FOOD"),
	HEALTH("HEALTH"),
	SPORTS("SPORTS"),
	CAMPING("CAMPING"),
	TREVELLING("TREVELLING");
	
	/**Type field */
	private String type;

	/**
	 * Create object constructor
	 * @param type - Type of Coupon
	 */
	CouponType(String type) {
        this.type = type;
    }

	/**
	 * Get Type method
	 * @return type of Coupon
	 */
    public String getType() {
        return type;
    }
        
}
