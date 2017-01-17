package utils;

/**
 * This is utility class that uses for contains constants for DB.
 * @author  Andrey Orlov
 * @version 1.0
 * 
 */
public class SQLQueries {
	/**
	 * Company queries
	 */
	public static final String INSERT_INTO_COMPANY_VALUES = "INSERT INTO Company VALUES ";
	public static final String UPDATE_COMPANY_SET = "UPDATE Company SET PASSWORD = ";
	public static final String SELECT_COMPANY_BY_ID = "SELECT * FROM Company WHERE ID = ";
	public static final String SELECT_ALL_COMPANIES = "SELECT * FROM Company";
	public static final String REMOVE_COMPANY = "DELETE FROM Company WHERE ID = ";
	public static final String SELECT_COMPANY_PASSWORD_BY_NAME = "SELECT PASSWORD FROM Company WHERE COMP_NAME = ";
	public static final String SELECT_COMPANY_ID_BY_NAME = "SELECT ID FROM Company WHERE COMP_NAME = ";
	public static final String SELECT_COMPANY_ID_AND_COUPON_ID = "SELECT com.ID as COMP_ID, coup.ID as COUPON_ID FROM Company com join Coupon coup on com.COMP_NAME = ? and coup.TITLE = ?";
	public static final String SELECT_COMPANY_COUPONS = "select * from Coupon where ID IN (select Company_Coupon.COUPON_ID from Company_Coupon where COMP_ID = ";
	public static final String SELECT_COMPANY_BY_NAME = "select * from Company where COMP_NAME ='";
	
	/**
	 * Customer queries
	 */
	public static final String INSERT_INTO_CUSTOMER_VALUES = "INSERT INTO Customer VALUES (?,?)";
	public static final String UPDATE_CUSTOMER_SET = "UPDATE Customer SET PASSWORD = '";
	public static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM Customer WHERE ID = ";
	public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM Customer";
	public static final String REMOVE_CUSTOMER = "DELETE FROM Customer WHERE CUST_NAME = '";
	public static final String SELECT_CUSTOMER_PASSWORD_BY_NAME = "SELECT PASSWORD FROM Customer WHERE CUST_NAME = ";
	public static final String SELECT_CUSTOMER_ID_BY_NAME = "SELECT ID FROM Customer WHERE CUST_NAME = ";
	public static final String SELECT_CUSTOMER_BY_NAME = "SELECT * FROM Customer WHERE CUST_NAME = '";
	
	/**
	 * Coupon queries
	 */
	public static final String SELECT_CUSTOMER_COUPONS = "select * from Coupon where ID IN (select Customer_Coupon.COUPON_ID from Customer_Coupon where CUST_ID = "; 
	public static final String SELECT_AMOUNT_FROM_COUPON = "select ID, AMOUNT, END_DATE from Coupon where TITLE = ";
	public static final String SELECT_COUPON_ID_BY_TITLE= "select ID from Coupon where TITLE = ";
	public static final String INSERT_INTO_COUPON_VALUES = "INSERT INTO Coupon VALUES (?,?,?,?,?,?,?,?);";
	public static final String UPDATE_COUPON_SET = "UPDATE Coupon SET END_DATE = '";
	public static final String SELECT_COUPON_BY_ID = "SELECT * FROM Coupon WHERE ID = ";
	public static final String SELECT_ALL_COUPONS = "SELECT * FROM Coupon";
	public static final String SELECT_ALL_COUPONS_BY_TYPE = "SELECT * FROM Coupon WHERE TYPE = '";
	public static final String REMOVE_COMPANY_COUPONS = "delete Coupon from Coupon where ID IN (select Company_Coupon.COUPON_ID from Company_Coupon where COMP_ID = ";
	public static final String REMOVE_FROM_COMPANY_COUPONS = "delete Company_Coupon from Company_Coupon where COMP_ID = ";
	public static final String REMOVE_COUPON = "DELETE FROM Coupon WHERE ID = ";
	public static final String SELECT_COUPON_BY_TITLE= "select * from Coupon where TITLE = '";
	public static final String UPDATE_AMOUNT_IN_COUPON = "update Coupon set amount = (select Coupon.AMOUNT from coupon where ID = ?) - 1 where ID = ?;";
	public static final String SELECT_END_DATE_OF_COUPONS = "select ID, TITLE, END_DATE from Coupon";
	public static final String CHECK_IF_COUPON_WAS_PURCHASED = "select COUPON_ID from Customer_Coupon where coupon_id = (select ID from Coupon where TITLE = ?)";
	
	/**
	 * Company Coupon queries
	 */
	public static final String SELECT_ALL_COMPANY_COUPONS = "select coup.ID, coup.TITLE, coup.START_DATE,coup.END_DATE, coup.AMOUNT,coup.TYPE,coup.MESSAGE,coup.PRICE,coup.IMAGE from Company_Coupon comp INNER JOIN Coupon coup ON comp.COUPON_ID = coup.id and comp.COMP_ID = (SELECT ID FROM Company WHERE COMP_NAME = ";
	public static final String INSERT_INTO_COMPANY_COUPON_VALUES = "INSERT INTO Company_Coupon VALUES ";
	public static final String DELETE_FROM_COMPANY_COUPON = "DELETE FROM Company_Coupon WHERE COUPON_ID = ";
	public static final String SELECT_COUPON_ID = "SELECT COUPON_ID FROM Company_Coupon WHERE COMP_ID = (SELECT ID FROM Company WHERE COMP_NAME = ";
	public static final String SELECT_ID_FROM_COUPON = "select COUPON_ID from Company_Coupon where COMP_ID = ";
	
	/**
	 * Customer Coupon queries
	 */
	public static final String SELECT_ALL_CUSTOMER_COUPONS = "select coup.ID, coup.TITLE, coup.START_DATE,coup.END_DATE, coup.AMOUNT,coup.TYPE,coup.MESSAGE,coup.PRICE,coup.IMAGE from Customer_Coupon cust INNER JOIN "
			+ "Coupon coup ON cust.COUPON_ID = coup.id and cust.CUST_ID = (SELECT ID FROM Customer WHERE CUST_NAME = ";
	public static final String REMOVE_FROM_CUSTOMER_COUPONS = "delete Customer_Coupon from Customer_Coupon where COUPON_ID IN(select Company_Coupon.COUPON_ID from Company_Coupon where COMP_ID = ";
	public static final String INSERT_INTO_CUSTOMER_COUPON_VALUES = "INSERT INTO Customer_Coupon VALUES ";
	public static final String DELETE_FROM_CUSTOMER_COUPON = "DELETE FROM Customer_Coupon WHERE COUPON_ID = ";
	public static final String SELECT_COUPON_ID_FROM_CUSTOMER_COUPON = "select Customer_Coupon.COUPON_ID from Customer_Coupon where CUST_ID = ";
	
}
