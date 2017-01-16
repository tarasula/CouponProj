package facade;
/**
 * CouponClientFacade Interface for Facade-layer
 * @author Andrey Orlov
 * @version 1.0
 */
public interface CouponClientFacade {
	/**
	 * Method to login with some credentials
	 * @param name - Name of client
	 * @param password - Password of client
	 * @param clienType - Type of client (Company, Customer, Administrator)
	 * @return CouponClientFacade Object
	 */
	public CouponClientFacade login(String name, String password, String clienType);

}
