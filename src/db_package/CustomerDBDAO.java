package db_package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import exceptions.ProjectException;
import facade.CustomerFacade;
import util.SQLConstantsQuery;

public class CustomerDBDAO implements CustomerDAO {

	private ConnectionPool pool;

	public CustomerDBDAO() {
	}

	private Statement getStatment() {
		Connection con = null;
		Statement st = null;
		con = pool.getInstance().getConnection();
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}

	@Override
	public void createCustomer(Customer cust) {
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				st.execute(SQLConstantsQuery.INSERT_INTO_CUSTOMER_VALUES + "(" + cust.getId() + ",'"
						+ cust.getCustName() + "','" + cust.getPassword() + "');");
				System.out.println("Customer " + cust.getCustName() + " added to DB");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeCustomer(Customer cust) {
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				st.execute(SQLConstantsQuery.REMOVE_CUSTOMER + cust.getId());
				System.out.println("The Customer " + cust.getCustName() + " removed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCustomer(Customer cust) {
		Customer customer = getCustomer((int) cust.getId());
		if (customer.getId() == cust.getId()) {
			customer.setId(cust.getId());
			customer.setCustName(cust.getCustName());
			customer.setPassword(cust.getPassword());
			try {
				getStatment().execute(SQLConstantsQuery.UPDATE_CUSTOMER_SET + customer.getId() + ", CUST_NAME = '"
						+ customer.getCustName() + "', PASSWORD = '" + customer.getPassword() + "' WHERE ID = "
						+ customer.getId());
				System.out.println("The Customer " + customer.getCustName() + " was updated");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new ProjectException("The Customer is not exist. You can add the Customer to DB.");
			} catch (ProjectException e) {
				e.printStackTrace();
				e.getMessage();
			}
		}
	}

	@Override
	public Customer getCustomer(long id) {
		Customer customer = new Customer();
		ResultSet rs;
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_CUSTOMER_BY_ID + id);
				while (rs.next()) {
					customer.setId(rs.getLong(SQLConstantsQuery.CUSTOMER_ID));
					customer.setCustName(rs.getString(SQLConstantsQuery.CUSTOMER_CUST_NAME).trim());
					customer.setPassword(rs.getString(SQLConstantsQuery.CUSTOMER_PASSWORD).trim());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public Collection<Customer> getAllCustomer() {
		ResultSet rs;
		ArrayList<Customer> customerList = new ArrayList<>();
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_ALL_CUSTOMERS);
				while (rs.next()) {
					Customer cust = new Customer();
					cust.setId(rs.getLong(SQLConstantsQuery.CUSTOMER_ID));
					cust.setCustName(rs.getString(SQLConstantsQuery.CUSTOMER_CUST_NAME).trim());
					cust.setPassword(rs.getString(SQLConstantsQuery.CUSTOMER_PASSWORD).trim());
					customerList.add(cust);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerList;
	}

	@Override
	public List<Coupon> getCoupons() {
		ResultSet rs;
		ArrayList<Coupon> customerList = new ArrayList<>();
		Statement st;
		String typeFromDB;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_ALL_CUSTOMER_COUPONS + "'" + CustomerFacade.getCustomerName() + "')");
				while (rs.next()) {
					Coupon coupon = new Coupon();
					coupon.setId(rs.getLong(SQLConstantsQuery.ID));
					coupon.setTitle(rs.getString(SQLConstantsQuery.COUPON_TITLE).trim());
					coupon.setStartDate(rs.getDate(SQLConstantsQuery.COUPON_START_DATE));
					coupon.setEndDate(rs.getDate(SQLConstantsQuery.COUPON_END_DATE));
					coupon.setAmount(rs.getInt(SQLConstantsQuery.COUPON_AMOUNT));
					typeFromDB = rs.getString(SQLConstantsQuery.COUPON_TYPE).trim();
					CouponType ct = CouponType.valueOf(typeFromDB.toUpperCase(Locale.ENGLISH));
					coupon.setType(ct);
					coupon.setMessage(rs.getString(SQLConstantsQuery.COUPON_MESSAGE).trim());
					coupon.setPrice(rs.getFloat(SQLConstantsQuery.COUPON_PRICE));
					coupon.setImage(rs.getString(SQLConstantsQuery.COUPON_IMAGE).trim());
					customerList.add(coupon);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerList;
	}

	@Override
	public boolean login(String custName, int password) {
		ResultSet rs;
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_CUSTOMER_PASSWORD_BY_NAME + "'" + custName + "'");
				while (rs.next()) {
					if (password == rs.getInt(SQLConstantsQuery.CUSTOMER_PASSWORD)) {
						System.out.println("Login success.");
						return true;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Login failed.");
		return false;
	}

}
