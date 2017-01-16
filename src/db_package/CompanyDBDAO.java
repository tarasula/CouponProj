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
import facade.CompanyFacade;
import util.SQLConstantsQuery;
/**
 * This class is Company DAO-layer that uses for speaking 
 * with DB and relevant tables.
 * 
 * @author  Andrey Orlov
 * @version 1.0
 */
public class CompanyDBDAO implements CompanyDAO {

	/**
	 * Create object for speaking with DB
	 */
	public CompanyDBDAO() {

	}

	/**
	 * Get connection method from ConnectionPool class for speaking with DB
	 * @return connection
	 */
	private Connection getConnection() {
		Connection con = null;
		con = ConnectionPool.getInstance().getConnection();
		return con;
	}

	/**
	 * Method for create Company in Company table in DB.
	 * Checking if this (Company comp) exist in DB.
	 * @param comp - Company object with filled fields
	 * @exception - throw ProjectException
	 */
	@Override
	public void createCompany(Company comp) throws Exception {
		Statement st;
		ResultSet rs = null;
		Connection con;
			con = getConnection();
			st = con.createStatement();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_COMPANY_BY_NAME + comp.getCompName() + "'" );
				if(!rs.next()){
					st.execute(SQLConstantsQuery.INSERT_INTO_COMPANY_VALUES + "(" + "'" + comp.getCompName()
							+ "','" + comp.getPassword() + "','" + comp.getEmail() + "');");
					System.out.println("Company " + comp.getCompName() + " was created");
				}else{
					throw new ProjectException("Can't create " + comp.getCompName() + " company, because is already exist.");
				}
			}
			st.close();
			rs.close();
			ConnectionPool.getInstance().returnConnection(con);
	}

	/**
	 * Method for remove Company from Company table in DB.
	 * Firstly get all companies and check if this company is exist
	 * @param comp - Company object with filled fields
	 * @exception - throw ProjectException
	 */
	@Override
	public void removeCompany(Company comp) throws Exception {
		Statement st;
		ResultSet rs = null;
		Connection con;
			con = getConnection();
			st = con.createStatement();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_COMPANY_ID_BY_NAME + "'" + comp.getCompName() + "'");
				if(rs.next()){
					comp.setId(rs.getInt(SQLConstantsQuery.ID));
					st.execute(SQLConstantsQuery.REMOVE_COMPANY_COUPONS + comp.getId() + ")");
					st.execute(SQLConstantsQuery.REMOVE_FROM_CUSTOMER_COUPONS + comp.getId() + ")");
					st.execute(SQLConstantsQuery.REMOVE_FROM_COMPANY_COUPONS + comp.getId());
					st.execute(SQLConstantsQuery.REMOVE_COMPANY + comp.getId());
					System.out.println("The Company " + comp.getCompName() + " removed");
				}else{
					throw new ProjectException("This Company is not exist.");
				}
			}
			st.close();
			rs.close();
			ConnectionPool.getInstance().returnConnection(con);
	}

	/**
	 * Method for Update the company in DB by comp_ID, throw Exception if the Company not exist.
	 * Firstly get all companies and check if this company is exist
	 * @param comp - Company object with filled fields
	 */
	@Override
	public void updateCompany(Company comp) throws Exception {
		Statement st;
		ResultSet rs = null;
		Connection con;
		con = getConnection();
		st = con.createStatement();
		if (st != null) {
			rs = st.executeQuery(SQLConstantsQuery.SELECT_COMPANY_ID_BY_NAME + "'" + comp.getCompName() + "'");
				if (rs.next()) {
					st.execute(SQLConstantsQuery.UPDATE_COMPANY_SET + "'" + comp.getPassword() + "', EMAIL = '" + comp.getEmail() + "' WHERE COMP_NAME = '" + comp.getCompName() + "'");
					System.out.println("The Company " + comp.getCompName() + " was updated");
				}else {
					throw new ProjectException("The Company is not exist. You can created this Company.");
				}
		}
		st.close();
		rs.close();
		ConnectionPool.getInstance().returnConnection(con);
	}

	/**
	 * Method for get Company by ID from Company table in DB.
	 * @param id - Company ID 
	 * @return Company object with filled fields
	 */
	@Override
	public Company getCompany(long l) throws Exception {
		Company company = new Company();
		ResultSet rs = null;
		ResultSet rsCoup = null;
		Statement st;
		Connection con;
		ArrayList<Coupon> coupons = new ArrayList<>();
			con = getConnection();
			st = con.createStatement();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_COMPANY_BY_ID + l);
				company.setCompName(SQLConstantsQuery.EMPTY);
				while (rs.next()) {
					company.setId(rs.getLong(SQLConstantsQuery.COMPANY_ID));
					company.setCompName(rs.getString(SQLConstantsQuery.COMPANY_COMP_NAME).trim());
					company.setPassword(rs.getString(SQLConstantsQuery.COMPANY_PASSWORD).trim());
					company.setEmail(rs.getString(SQLConstantsQuery.COMPANY_EMAIL).trim());
					
				}
				rsCoup = st.executeQuery(SQLConstantsQuery.SELECT_COMPANY_COUPONS + l + ")");
				while(rsCoup.next()){
					Coupon coupon = new Coupon();
					coupon.setId(rsCoup.getLong(SQLConstantsQuery.ID));
					coupon.setTitle(rsCoup.getString(SQLConstantsQuery.COUPON_TITLE).trim());
					coupon.setStartDate(rsCoup.getDate(SQLConstantsQuery.COUPON_START_DATE));
					coupon.setEndDate(rsCoup.getDate(SQLConstantsQuery.COUPON_END_DATE));
					coupon.setAmount(rsCoup.getInt(SQLConstantsQuery.COUPON_AMOUNT));
					String typeFromDB = rsCoup.getString(SQLConstantsQuery.COUPON_TYPE).trim();
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
				company.setCoupon(coupons);
			}
			rs.close();
			st.close();
			ConnectionPool.getInstance().returnConnection(con);
		return company;
	}
	
	

	/**
	 * Method for get all Companies from Company table in DB.
	 * @return Company list with filled fields
	 */
	@Override
	public Collection<Company> getAllCompanies() throws Exception {
		ResultSet rs = null;
		ResultSet rsCoup = null;
		ArrayList<Company> companyList = new ArrayList<>();
		ArrayList<Coupon> coupons;
		Statement st;
		Statement stCoup;
		Connection con;
			con = getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_ALL_COMPANIES);
				if(!rs.next()){
					return companyList;
				}
				rs.absolute(0);
				while (rs.next()) {
					coupons = new ArrayList<>();
					Company comp = new Company();
					comp.setId(rs.getLong(SQLConstantsQuery.COMPANY_ID));
					comp.setCompName(rs.getString(SQLConstantsQuery.COMPANY_COMP_NAME).trim());
					comp.setPassword(rs.getString(SQLConstantsQuery.COMPANY_PASSWORD).trim());
					comp.setEmail(rs.getString(SQLConstantsQuery.COMPANY_EMAIL).trim());
					stCoup = con.createStatement();
					rsCoup = stCoup.executeQuery(SQLConstantsQuery.SELECT_COMPANY_COUPONS + comp.getId() + ")");
					while(rsCoup.next()){
						Coupon coupon = new Coupon();
						coupon.setId(rsCoup.getLong(SQLConstantsQuery.ID));
						coupon.setTitle(rsCoup.getString(SQLConstantsQuery.COUPON_TITLE).trim());
						coupon.setStartDate(rsCoup.getDate(SQLConstantsQuery.COUPON_START_DATE));
						coupon.setEndDate(rsCoup.getDate(SQLConstantsQuery.COUPON_END_DATE));
						coupon.setAmount(rsCoup.getInt(SQLConstantsQuery.COUPON_AMOUNT));
						String typeFromDB = rsCoup.getString(SQLConstantsQuery.COUPON_TYPE).trim();
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
					comp.setCoupon(coupons);
					companyList.add(comp);
				}
			}
			rs.close();
			st.close();
			ConnectionPool.getInstance().returnConnection(con);
		return companyList;
	}

	/**
	 * Method for get all Coupon that Company was created.
	 * @return Coupon list with filled fields
	 */
	@Override
	public List<Coupon> getCoupons() throws ProjectException, SQLException {
		ResultSet rs = null;
		ArrayList<Coupon> couponList = new ArrayList<>();
		String typeFromDB;
		Statement stGetAll;
		Connection con;
			con = getConnection();
			stGetAll = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (stGetAll != null) {
				rs = stGetAll.executeQuery(SQLConstantsQuery.SELECT_ALL_COMPANY_COUPONS + "'" + CompanyFacade.getCompanyName() + "')");
				if(!rs.next()){
					if(couponList.isEmpty()){
						throw new ProjectException("Company does not have Coupons!");
					}
					return couponList;
				}
				rs.absolute(0);
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
					couponList.add(coupon);
				}
			}
			rs.close();
			stGetAll.close();
			ConnectionPool.getInstance().returnConnection(con);
		
		return couponList;
	}

	/**
	 * Method to login with Company credential
	 * @param name - Company name
	 * @param password - Company password
	 * @return CompanyFacade Object
	 * @exception if log in faled
	 */
	@Override
	public boolean login(String compName, String password) throws Exception {
		ResultSet rs = null;
		Statement st;
		Connection con;
			con = getConnection();
			st = con.createStatement();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_COMPANY_PASSWORD_BY_NAME + "'" + compName + "'");
				while (rs.next()) {
					if (password.equals(rs.getString(SQLConstantsQuery.COMPANY_PASSWORD).trim())) {
						System.out.println("Company login success.");
						return true;
					}
				}
			}
			rs.close();
			st.close();
			ConnectionPool.getInstance().returnConnection(con);
			throw new ProjectException("Company Login failed.");
	}

}
