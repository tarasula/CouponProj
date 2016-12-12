package util;

public class SQLConstantsQuery {
	
	//**Company constants
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
	
	//**Customer constants
	public static final String CUSTOMER_ID = "ID";
	public static final String CUSTOMER_CUST_NAME = "CUST_NAME";
	public static final String CUSTOMER_PASSWORD = "PASSWORD";
	public static final String INSERT_INTO_CUSTOMER_VALUES = "INSERT INTO Customer VALUES ";
	public static final String UPDATE_CUSTOMER_SET = "UPDATE Customer SET";
	public static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM Customer WHERE ID = ";
	public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM Customer";
	public static final String REMOVE_CUSTOMER = "DELETE FROM Customer WHERE ID = ";
	public static final String SELECT_CUSTOMER_PASSWORD_BY_NAME = "SELECT PASSWORD FROM Customer WHERE CUST_NAME = ";
	
}
