package util;

public class SQLConstantsQuery {
	
	/*
	 * Company constants
	 */
	public static final String COMPANY_ID = "ID";
	public static final String COMPANY_COMP_NAME = "COMP_NAME";
	public static final String COMPANY_PASSWORD = "PASSWORD";
	public static final String COMPANY_EMAIL = "EMAIL";
	public static final String INSERT_INTO_COMPANY_VALUES = "INSERT INTO Company VALUES ";
	public static final String UPDATE_COMPANY_SET = "UPDATE Company SET ID = ";
	public static final String SELECT_COMPANY_BY_ID = "SELECT * FROM Company WHERE ID = ";
	public static final String SELECT_ALL_COMPANIES = "SELECT * FROM Company";
	public static final String REMOVE_COMPANY = "DELETE FROM Company WHERE ID = ";
	public static final String SELECT_COMPANY_PASSWORD_BY_NAME = "SELECT PASSWORD FROM Company WHERE COMP_NAME = ";
	public static final String SELECT_ALL_COMPANY_COUPONS = "select coup.ID, coup.TITLE, coup.START_DATE,coup.END_DATE, coup.AMOUNT,coup.TYPE,coup.MESSAGE,coup.PRICE,coup.IMAGE from Company com INNER JOIN Coupon coup ON com.ID = coup.id";
	/*
	 * Customer constants
	 */
	public static final String CUSTOMER_ID = "ID";
	public static final String CUSTOMER_CUST_NAME = "CUST_NAME";
	public static final String CUSTOMER_PASSWORD = "PASSWORD";
	public static final String INSERT_INTO_CUSTOMER_VALUES = "INSERT INTO Customer VALUES ";
	public static final String UPDATE_CUSTOMER_SET = "UPDATE Customer SET ID = ";
	public static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM Customer WHERE ID = ";
	public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM Customer";
	public static final String REMOVE_CUSTOMER = "DELETE FROM Customer WHERE ID = ";
	public static final String SELECT_CUSTOMER_PASSWORD_BY_NAME = "SELECT PASSWORD FROM Customer WHERE CUST_NAME = ";
	public static final String SELECT_ALL_CUSTOMER_COUPONS = "select coup.ID, coup.TITLE, coup.START_DATE,coup.END_DATE, coup.AMOUNT,coup.TYPE,coup.MESSAGE,coup.PRICE,coup.IMAGE from Customer cust INNER JOIN Coupon coup ON cust.ID = coup.id";
	/*
	 * Coupon constants
	 */
	public static final String COUPON_ID = "ID";
	public static final String COUPON_TITLE = "TITLE";
	public static final String COUPON_START_DATE = "START_DATE";
	public static final String COUPON_END_DATE = "END_DATE";
	public static final String COUPON_AMOUNT = "AMOUNT";
	public static final String COUPON_TYPE = "TYPE";
	public static final String COUPON_MESSAGE = "MESSAGE";
	public static final String COUPON_PRICE = "PRICE";
	public static final String COUPON_IMAGE = "IMAGE";
//	public static final String CUSTOMER_PASSWORD = "PASSWORD";
	public static final String INSERT_INTO_COUPON_VALUES = "INSERT INTO Coupon VALUES ";
	public static final String UPDATE_COUPON_SET = "UPDATE Coupon SET ID = ";
	public static final String SELECT_COUPON_BY_ID = "SELECT * FROM Coupon WHERE ID = ";
	public static final String SELECT_ALL_COUPONS = "SELECT * FROM Coupon";
	public static final String SELECT_ALL_COUPONS_BY_TYPE = "SELECT * FROM Coupon WHERE TYPE = '";
	
	public static final String REMOVE_COUPON = "DELETE FROM Coupon WHERE ID = ";
//	public static final String SELECT_CUSTOMER_PASSWORD_BY_NAME = "SELECT PASSWORD FROM Customer WHERE CUST_NAME = ";
}
