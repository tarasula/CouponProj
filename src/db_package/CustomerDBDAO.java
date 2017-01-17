package db_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exceptions.DuplicateDataException;
import exceptions.LogInException;
import exceptions.NoDataException;
import exceptions.OverallException;
import facade.CustomerFacade;
import utils.CouponConstants;
import utils.CustomerConstants;
import utils.SQLQueries;

/**
 * This class is Customer DAO-layer that uses for speaking with DB and relevant
 * tables.
 * 
 * @author Andrey Orlov
 * @version 1.0
 */

public class CustomerDBDAO implements CustomerDAO {

	/**
	 * Create object for speaking with DB
	 */
	public CustomerDBDAO() {
	}

	/**
	 * Get connection method from ConnectionPool class for speaking with DB
	 * 
	 * @return connection
	 */
	private Connection getConnection() {
		Connection con = null;
		con = ConnectionPool.getInstance().getConnection();
		return con;
	}

	/**
	 * Method for create Customer in Customer table in DB. Checking if this
	 * (Customer cust) exist in DB.
	 * 
	 * @param cust
	 *            - Customer object with filled fields
	 * @exception 
	 *                 OverallException - The Customer already exist.
	 */
	@Override
	public void createCustomer(Customer cust) throws Exception {
		PreparedStatement prepSt = null;
		Statement st;
		Connection con;
		ResultSet rs = null;
		con = getConnection();
		st = con.createStatement();
		if (st != null) {
			rs = st.executeQuery(SQLQueries.SELECT_CUSTOMER_BY_NAME + cust.getCustName() + "'");
			if (!rs.next()) {
				prepSt = con.prepareStatement(SQLQueries.INSERT_INTO_CUSTOMER_VALUES,
						Statement.RETURN_GENERATED_KEYS);
				prepSt.setString(1, cust.getCustName());
				prepSt.setString(2, cust.getPassword());
				prepSt.executeUpdate();

				ResultSet keys = prepSt.getGeneratedKeys();
				long id = 0;
				if (keys.next()) {
					id = keys.getLong(1);
					cust.setId(id);
				}
				System.out.println("Customer " + cust.getCustName() + " was created");
			} else {
				throw new DuplicateDataException(cust);
			}
		}
		prepSt.close();
		st.close();
		rs.close();
		ConnectionPool.getInstance().returnConnection(con);
	}

	/**
	 * Method for remove Customer from Customer table in DB. Firstly get all
	 * customers and check if this customer is exist
	 * 
	 * @param cust
	 *            - Customer object with filled fields
	 * @exception
	 *                OverallException - This Customer is not exist.
	 */
	@Override
	public void removeCustomer(Customer cust) throws Exception {
		Statement st;
		Connection con;
		ResultSet rs = null;
		con = getConnection();
		st = con.createStatement();
		if (st != null) {
			rs = st.executeQuery(SQLQueries.SELECT_CUSTOMER_BY_NAME + cust.getCustName() + "'");
			if (rs.next()) {
				st.execute(SQLQueries.REMOVE_CUSTOMER + cust.getCustName() + "'");
				System.out.println("The Customer " + cust.getCustName() + " removed");
			} else {
				throw new NoDataException(cust);
			}
		}
		st.close();
		rs.close();
		ConnectionPool.getInstance().returnConnection(con);
	}

	/**
	 * Method for update Customer in Customer table in DB. Firstly get all
	 * customers and check if this customer is exist
	 * 
	 * @param cust
	 *            - Customer object with filled fields
	 */
	@Override
	public void updateCustomer(Customer cust) throws Exception {
		Statement st;
		Connection con;
		ResultSet rs = null;
		con = getConnection();
		st = con.createStatement();
		if (st != null) {
			rs = st.executeQuery(SQLQueries.SELECT_CUSTOMER_BY_NAME + cust.getCustName() + "'");
			if (rs.next()) {
				st.execute(SQLQueries.UPDATE_CUSTOMER_SET + cust.getPassword() + "' WHERE CUST_NAME = '"
						+ cust.getCustName() + "'");
				System.out.println("The Customer " + cust.getCustName() + " was updated");
			} else {
				throw new NoDataException(cust);
			}
		}
		st.close();
		rs.close();
		ConnectionPool.getInstance().returnConnection(con);
	}

	/**
	 * Method for get Customer by ID from Customer table in DB.
	 * 
	 * @param id
	 *            - Customer ID
	 * @return Customer object with filled fields
	 */
	@Override
	public Customer getCustomer(long id) throws Exception {
		Customer customer = new Customer();
		ResultSet rsCust = null;
		ResultSet rsCoup = null;
		ArrayList<Coupon> coupons = new ArrayList<>();
		String typeFromDB;
		Statement st;
		Connection con;
		con = getConnection();
		st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		if (st != null) {
			rsCust = st.executeQuery(SQLQueries.SELECT_CUSTOMER_BY_ID + id);
			if (!rsCust.next()) {
				throw new NoDataException("Customer with ID - " + id + " is not exist.");
			}
			rsCust.absolute(0);
			while (rsCust.next()) {
				customer.setId(rsCust.getLong(CustomerConstants.CUSTOMER_ID));
				customer.setCustName(rsCust.getString(CustomerConstants.CUSTOMER_CUST_NAME));
				customer.setPassword(rsCust.getString(CustomerConstants.CUSTOMER_PASSWORD));
			}
			rsCoup = st.executeQuery(SQLQueries.SELECT_CUSTOMER_COUPONS + id + ")");
			while (rsCoup.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rsCoup.getLong(CouponConstants.ID));
				coupon.setStartDate(rsCoup.getDate(CouponConstants.COUPON_START_DATE));
				coupon.setTitle(rsCoup.getString(CouponConstants.COUPON_TITLE));
				coupon.setEndDate(rsCoup.getDate(CouponConstants.COUPON_END_DATE));
				coupon.setAmount(rsCoup.getInt(CouponConstants.COUPON_AMOUNT));
				typeFromDB = rsCoup.getString(CouponConstants.COUPON_TYPE);
				for (CouponType ct : CouponType.values()) {
					if (typeFromDB.trim().equals(ct.toString())) {
						coupon.setType(ct);
					}
				}
				coupon.setMessage(rsCoup.getString(CouponConstants.COUPON_MESSAGE));
				coupon.setPrice(rsCoup.getDouble(CouponConstants.COUPON_PRICE));
				coupon.setImage(rsCoup.getString(CouponConstants.COUPON_IMAGE));
				coupons.add(coupon);
			}
			customer.setCoupons(coupons);
		}
		rsCust.close();
		rsCoup.close();
		st.close();
		ConnectionPool.getInstance().returnConnection(con);
		return customer;
	}

	/**
	 * Method for get all Customers from Customer table in DB.
	 * 
	 * @return Customer list with filled fields
	 */
	@Override
	public Collection<Customer> getAllCustomer() throws Exception {
		ResultSet rs = null;
		ResultSet rsCoup = null;
		ArrayList<Customer> customerList = new ArrayList<>();
		ArrayList<Coupon> coupons;
		Statement stCoup;
		String typeFromDB;
		Statement stGetAll;
		Connection con;
		con = getConnection();
		stGetAll = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		if (stGetAll != null) {
			rs = stGetAll.executeQuery(SQLQueries.SELECT_ALL_CUSTOMERS);
			if (!rs.next()) {
				return customerList;
			}
			rs.absolute(0);
			while (rs.next()) {
				coupons = new ArrayList<>();
				Customer cust = new Customer();
				cust.setId(rs.getLong(CustomerConstants.CUSTOMER_ID));
				cust.setCustName(rs.getString(CustomerConstants.CUSTOMER_CUST_NAME));
				cust.setPassword(rs.getString(CustomerConstants.CUSTOMER_PASSWORD));
				stCoup = con.createStatement();
				rsCoup = stCoup.executeQuery(SQLQueries.SELECT_CUSTOMER_COUPONS + cust.getId() + ")");
				while (rsCoup.next()) {
					Coupon coupon = new Coupon();
					coupon.setId(rsCoup.getLong(CouponConstants.ID));
					coupon.setTitle(rsCoup.getString(CouponConstants.COUPON_TITLE));
					coupon.setStartDate(rsCoup.getDate(CouponConstants.COUPON_START_DATE));
					coupon.setEndDate(rsCoup.getDate(CouponConstants.COUPON_END_DATE));
					coupon.setAmount(rsCoup.getInt(CouponConstants.COUPON_AMOUNT));
					typeFromDB = rsCoup.getString(CouponConstants.COUPON_TYPE);
					for (CouponType ct : CouponType.values()) {
						if (typeFromDB.trim().equals(ct.toString())) {
							coupon.setType(ct);
						}
					}
					coupon.setMessage(rsCoup.getString(CouponConstants.COUPON_MESSAGE));
					coupon.setPrice(rsCoup.getDouble(CouponConstants.COUPON_PRICE));
					coupon.setImage(rsCoup.getString(CouponConstants.COUPON_IMAGE));
					coupons.add(coupon);
				}
				cust.setCoupons(coupons);
				customerList.add(cust);
			}
		}
		rsCoup.close();
		rs.close();
		stGetAll.close();
		ConnectionPool.getInstance().returnConnection(con);
		return customerList;
	}

	/**
	 * Method for get all Coupon that Customer was purchase.
	 * 
	 * @return Coupon list with filled fields
	 */
	@Override
	public List<Coupon> getCoupons() throws OverallException, Exception {
		ResultSet rs = null;
		ArrayList<Coupon> customerList = new ArrayList<>();
		Statement st;
		String typeFromDB;
		Connection con;
		con = getConnection();
		st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		if (st != null) {
			rs = st.executeQuery(
					SQLQueries.SELECT_ALL_CUSTOMER_COUPONS + "'" + CustomerFacade.getCustomerName() + "')");
			if (!rs.next()) {
				throw new NoDataException("Customer does not have Coupons!");
			}
			rs.absolute(0);
			while (rs.next()) {
				Coupon coupon = new Coupon();
				coupon.setId(rs.getLong(CouponConstants.ID));
				coupon.setTitle(rs.getString(CouponConstants.COUPON_TITLE));
				coupon.setStartDate(rs.getDate(CouponConstants.COUPON_START_DATE));
				coupon.setEndDate(rs.getDate(CouponConstants.COUPON_END_DATE));
				coupon.setAmount(rs.getInt(CouponConstants.COUPON_AMOUNT));
				typeFromDB = rs.getString(CouponConstants.COUPON_TYPE);
				for (CouponType ct : CouponType.values()) {
					if (typeFromDB.trim().equals(ct.toString())) {
						coupon.setType(ct);
					}
				}
				coupon.setMessage(rs.getString(CouponConstants.COUPON_MESSAGE));
				coupon.setPrice(rs.getDouble(CouponConstants.COUPON_PRICE));
				coupon.setImage(rs.getString(CouponConstants.COUPON_IMAGE));
				customerList.add(coupon);
			}
			if (customerList.isEmpty()) {
				throw new NoDataException("This user does not have Coupons");
			}
		}
		rs.close();
		st.close();
		ConnectionPool.getInstance().returnConnection(con);
		return customerList;
	}

	/**
	 * Method to login with Customer credential
	 * 
	 * @param custName
	 *            - Customer name
	 * @param password
	 *            - Customer password
	 * @return true/false
	 * @exception 
	 *                OverallException if log in failed
	 */
	@Override
	public boolean login(String custName, String password) throws Exception {
		ResultSet rs = null;
		Statement st;
		Connection con;
		con = getConnection();
		st = con.createStatement();
		if (st != null) {
			rs = st.executeQuery(SQLQueries.SELECT_CUSTOMER_PASSWORD_BY_NAME + "'" + custName + "'");
			while (rs.next()) {
				if (password.equals(rs.getString(CustomerConstants.CUSTOMER_PASSWORD).trim())) {
					System.out.println("Customer login success.");
					return true;
				}
			}
		}
		rs.close();
		st.close();
		ConnectionPool.getInstance().returnConnection(con);
		throw new LogInException(this);
	}

}
