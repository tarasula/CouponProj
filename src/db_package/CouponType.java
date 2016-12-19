/**
 * 
 */
package db_package;

/**
 * @author Andrey Orlov
 *
 */
public enum CouponType {
	RESTURANS("RESTURANS"), 
	ELECTRICITY("ELECTRICITY"), 
	FOOD("FOOD"),
	HEALTH("HEALTH"),
	SPORTS("SPORTS"),
	CAMPING("CAMPING"),
	TREVELLING("TREVELLING");
	
	
	private String type;

	CouponType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
    
    public static CouponType fromString(String text) {
        if (text != null) {
          for (CouponType b : CouponType .values()) {
            if (text.equalsIgnoreCase(b.type())) {
              return b;
            }
          }
        }
        return null;
      }
    
    
}
