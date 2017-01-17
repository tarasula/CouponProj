/**
 * 
 */
package db_package;

/**
 * @author Andrey Orlov
 *
 */
public enum ClientType {
	
	ADMINISTRATOR("ADMINISTRATOR"),
	ADMIN("ADMIN"),
	CUSTOMER("CUSTOMER"),
	COMPANY("COMPANY");
	
	
	/**Type field */
	private String type;

	/**
	 * Create object constructor
	 * @param type - Type of Client
	 */
	ClientType(String type) {
        this.type = type;
    }
	
	/**
	 * Get Type method
	 * @return type of Client
	 */
    public String getType() {
        return type;
    }
}
