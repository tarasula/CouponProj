package db_package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import util.SQLConstantsQuery;

public class CustomerDBDAO implements CustomerDAO {

private ConnectionPool pool;
	
	
	private Statement getStatment(){
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
		try {
			getStatment().executeQuery(SQLConstantsQuery.INSERT_INTO_CUSTOMER_VALUES
					+ "(" + cust.getId() + "," + cust.getCustName() 
					+ "," + cust.getPassword() + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeCustomer(Customer cust) {
		try {
			getStatment().executeQuery(SQLConstantsQuery.REMOVE_CUSTOMER + cust.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCustomer(Customer cust) {
		ResultSet rs;
		Customer customer = getCustomer((int) cust.getId());
		customer.setId(cust.getId());
		customer.setCustName(cust.getCustName());
		customer.setPassword(cust.getPassword());
			try {
				rs = getStatment().executeQuery(SQLConstantsQuery.UPDATE_CUSTOMER_SET );
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	@Override
	public Customer getCustomer(int id) {
		Customer customer = new Customer();
		ResultSet rs;
		try {
			rs = getStatment().executeQuery(SQLConstantsQuery.SELECT_CUSTOMER_BY_ID + id );
			while(rs.next()){
				customer.setId(rs.getLong(SQLConstantsQuery.CUSTOMER_ID));
				customer.setCustName(rs.getString(SQLConstantsQuery.CUSTOMER_CUST_NAME));
				customer.setPassword(rs.getString(SQLConstantsQuery.CUSTOMER_PASSWORD));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public Collection<Customer> getAllCustomer() {
		ResultSet rs;
		ArrayList<Customer> companyList = new ArrayList<>();
		try {
			rs = getStatment().executeQuery(SQLConstantsQuery.SELECT_ALL_CUSTOMERS);
			while (rs.next()) {
				Customer cust = new Customer();
				cust.setId(rs.getLong(SQLConstantsQuery.CUSTOMER_ID));
				cust.setCustName(rs.getString(SQLConstantsQuery.CUSTOMER_CUST_NAME));
				cust.setPassword(rs.getString(SQLConstantsQuery.CUSTOMER_PASSWORD));
				companyList.add(cust);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyList;
	}

	@Override
	public Collection<Coupon> getCoupons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean login(String custName, int password) {
		ResultSet rs;
		try {
			rs = getStatment().executeQuery(SQLConstantsQuery.SELECT_CUSTOMER_PASSWORD_BY_NAME + custName);
			int psswrd = rs.getInt(SQLConstantsQuery.CUSTOMER_PASSWORD);
			if(password == psswrd){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
