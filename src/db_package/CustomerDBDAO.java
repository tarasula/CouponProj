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

	public CustomerDBDAO() {
	}

	private Statement getStatment() {
		Connection con = null;
		Statement st = null;
		con = ConnectionPool.getInstance().getConnection();
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
		ArrayList<Customer> custList = (ArrayList<Customer>) getAllCustomer();
		boolean flag = false;
		for(int i=0; i<custList.size(); i++){
			if(custList.get(i).getCustName().equals(cust.getCustName())){
				flag = true;
			}
		}
			if(!flag){
				try {
					st = getStatment();
					if (st != null) {
						st.execute(SQLConstantsQuery.INSERT_INTO_CUSTOMER_VALUES + "(" + cust.getId() + ",'"
								+ cust.getCustName() + "','" + cust.getPassword() + "');");
						System.out.println("Customer " + cust.getCustName() + " was created");
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{
				try {
					throw new ProjectException("The Customer " + cust.getCustName() + " already exist.");
				} catch (ProjectException e) {
					e.printStackTrace();
				}
			}
		
		
	}

	@Override
	public void removeCustomer(Customer cust) {
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				st.execute(SQLConstantsQuery.REMOVE_CUSTOMER + cust.getCustName() + "'");
				System.out.println("The Customer " + cust.getCustName() + " removed");
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCustomer(Customer cust) {
		ArrayList<Customer> allCustomers = new ArrayList<>();
		boolean flag = false;
		allCustomers = (ArrayList<Customer>) getAllCustomer();
		for (int i = 0; i < allCustomers.size(); i++) {
			if (cust.getCustName().equals(allCustomers.get(i).getCustName())) {
				flag = true;
			}
		}
		Statement st;
		if (flag) {
			try {
				st = getStatment();
				if (st != null) {
					st.execute(SQLConstantsQuery.UPDATE_CUSTOMER_SET + cust.getId() + ", PASSWORD = '" + cust.getPassword() + "' WHERE CUST_NAME = '"
							+ cust.getCustName() + "'");
					System.out.println("The Customer " + cust.getCustName() + " was updated");
				}
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new ProjectException("The Customer " + cust.getCustName() + "is already exist.");
			} catch (ProjectException e) {
				e.printStackTrace();
				e.getMessage();
			}
		}
	}

	@Override
	public Customer getCustomer(long id) {
		Customer customer = new Customer();
		ResultSet rsCust = null;
		ResultSet rsCoup = null;
		Statement st;
		ArrayList<Coupon> coupons = new ArrayList<>();
		String typeFromDB;
		try {
			st = getStatment();
			if (st != null) {
				rsCust = st.executeQuery(SQLConstantsQuery.SELECT_CUSTOMER_BY_ID + id);
				while (rsCust.next()) {
					customer.setId(rsCust.getLong(SQLConstantsQuery.CUSTOMER_ID));
					customer.setCustName(rsCust.getString(SQLConstantsQuery.CUSTOMER_CUST_NAME).trim());
					customer.setPassword(rsCust.getString(SQLConstantsQuery.CUSTOMER_PASSWORD).trim());
				}
				rsCoup = st.executeQuery(SQLConstantsQuery.SELECT_CUSTOMER_COUPONS + id + ")");
				while(rsCoup.next()){
					Coupon coupon = new Coupon();
					coupon.setId(rsCoup.getLong(SQLConstantsQuery.ID));
					coupon.setTitle(rsCoup.getString(SQLConstantsQuery.COUPON_TITLE).trim());
					coupon.setStartDate(rsCoup.getDate(SQLConstantsQuery.COUPON_START_DATE));
					coupon.setEndDate(rsCoup.getDate(SQLConstantsQuery.COUPON_END_DATE));
					coupon.setAmount(rsCoup.getInt(SQLConstantsQuery.COUPON_AMOUNT));
					typeFromDB = rsCoup.getString(SQLConstantsQuery.COUPON_TYPE).trim();
					for (CouponType ct : CouponType.values()) {
						if (typeFromDB.equals(ct.toString())) {
							coupon.setType(ct);
						}
					}
					coupon.setMessage(rsCoup.getString(SQLConstantsQuery.COUPON_MESSAGE).trim());
					coupon.setPrice(rsCoup.getDouble(SQLConstantsQuery.COUPON_PRICE));
					coupon.setImage(rsCoup.getString(SQLConstantsQuery.COUPON_IMAGE).trim());
					coupons.add(coupon);
				}
				customer.setCoupons(coupons);
			}
			rsCust.close();
			rsCoup.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public Collection<Customer> getAllCustomer() {
		ResultSet rs = null;
		ResultSet rsCoup = null;
		ArrayList<Customer> customerList = new ArrayList<>();
		ArrayList<Coupon> coupons;
		Statement st;
		Statement stCoup;
		String typeFromDB;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_ALL_CUSTOMERS);
				while (rs.next()) {
					coupons = new ArrayList<>();
					Customer cust = new Customer();
					cust.setId(rs.getLong(SQLConstantsQuery.CUSTOMER_ID));
					cust.setCustName(rs.getString(SQLConstantsQuery.CUSTOMER_CUST_NAME).trim());
					cust.setPassword(rs.getString(SQLConstantsQuery.CUSTOMER_PASSWORD).trim());
					stCoup = getStatment();
					rsCoup = stCoup.executeQuery(SQLConstantsQuery.SELECT_CUSTOMER_COUPONS + cust.getId() + ")");
					while(rsCoup.next()){
						Coupon coupon = new Coupon();
						coupon.setId(rsCoup.getLong(SQLConstantsQuery.ID));
						coupon.setTitle(rsCoup.getString(SQLConstantsQuery.COUPON_TITLE).trim());
						coupon.setStartDate(rsCoup.getDate(SQLConstantsQuery.COUPON_START_DATE));
						coupon.setEndDate(rsCoup.getDate(SQLConstantsQuery.COUPON_END_DATE));
						coupon.setAmount(rsCoup.getInt(SQLConstantsQuery.COUPON_AMOUNT));
						typeFromDB = rsCoup.getString(SQLConstantsQuery.COUPON_TYPE).trim();
						for (CouponType ct : CouponType.values()) {
							if (typeFromDB.equals(ct.toString())) {
								coupon.setType(ct);
							}
						}
						coupon.setMessage(rsCoup.getString(SQLConstantsQuery.COUPON_MESSAGE).trim());
						coupon.setPrice(rsCoup.getDouble(SQLConstantsQuery.COUPON_PRICE));
						coupon.setImage(rsCoup.getString(SQLConstantsQuery.COUPON_IMAGE).trim());
						coupons.add(coupon);
					}
					cust.setCoupons(coupons);
					customerList.add(cust);
				}
			}
			rsCoup.close();
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerList;
	}

	@Override
	public List<Coupon> getCoupons() {
		ResultSet rs = null;
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
					for (CouponType ct : CouponType.values()) {
						if (typeFromDB.equals(ct.toString())) {
							coupon.setType(ct);
						}
					}
					coupon.setMessage(rs.getString(SQLConstantsQuery.COUPON_MESSAGE).trim());
					coupon.setPrice(rs.getDouble(SQLConstantsQuery.COUPON_PRICE));
					coupon.setImage(rs.getString(SQLConstantsQuery.COUPON_IMAGE).trim());
					customerList.add(coupon);
				}
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerList;
	}

	@Override
	public boolean login(String custName, String password) {
		ResultSet rs = null;
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_CUSTOMER_PASSWORD_BY_NAME + "'" + custName + "'");
				while (rs.next()) {
					if (password.equals(rs.getString(SQLConstantsQuery.CUSTOMER_PASSWORD).trim())) {
						System.out.println("Customer login success.");
						return true;
					}
				}
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Login failed.");
		return false;
	}

}
