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

public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPool pool;

	public CompanyDBDAO() {

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
	public void createCompany(Company comp) {
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				st.execute(SQLConstantsQuery.INSERT_INTO_COMPANY_VALUES + "(" + comp.getId() + ",'" + comp.getCompName()
						+ "','" + comp.getPassword() + "','" + comp.getEmail() + "');");
				System.out.println("Company " + comp.getCompName() + " added to DB");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeCompany(Company comp) {
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				st.execute(SQLConstantsQuery.REMOVE_COMPANY + comp.getId());
				System.out.println("The Company " + comp.getCompName() + " removed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Update the company in DB by comp_ID, throw Exception if the Company not
	 * exist.
	 */
	@Override
	public void updateCompany(Company comp) {
		Company company = getCompany((int) comp.getId());
		if (company.getId() == comp.getId()) {
			company.setId(comp.getId());
			company.setCompName(comp.getCompName());
			company.setPassword(comp.getPassword());
			company.setEmail(comp.getEmail());
			try {
				getStatment().execute(SQLConstantsQuery.UPDATE_COMPANY_SET + company.getId() + ", COMP_NAME = '"
						+ company.getCompName() + "', PASSWORD = '" + company.getPassword() + "', EMAIL = '"
						+ company.getEmail() + "' WHERE ID = " + company.getId());
				System.out.println("The Company " + company.getCompName() + " was updated");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new ProjectException("The Company is not exist. You can add this Company to DB.");
			} catch (ProjectException e) {
				e.printStackTrace();
				e.getMessage();
			}
		}
	}

	@Override
	public Company getCompany(long l) {
		Company company = new Company();
		ResultSet rs;
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_COMPANY_BY_ID + l);
				while (rs.next()) {
					company.setId(rs.getLong(SQLConstantsQuery.COMPANY_ID));
					company.setCompName(rs.getString(SQLConstantsQuery.COMPANY_COMP_NAME).trim());
					company.setPassword(rs.getString(SQLConstantsQuery.COMPANY_PASSWORD).trim());
					company.setEmail(rs.getString(SQLConstantsQuery.COMPANY_EMAIL).trim());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}

	@Override
	public Collection<Company> getAllCompanies() {
		ResultSet rs;
		ArrayList<Company> companyList = new ArrayList<>();
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_ALL_COMPANIES);
				while (rs.next()) {
					Company comp = new Company();
					comp.setId(rs.getLong(SQLConstantsQuery.COMPANY_ID));
					comp.setCompName(rs.getString(SQLConstantsQuery.COMPANY_COMP_NAME).trim());
					comp.setPassword(rs.getString(SQLConstantsQuery.COMPANY_PASSWORD).trim());
					comp.setEmail(rs.getString(SQLConstantsQuery.COMPANY_EMAIL).trim());
					companyList.add(comp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyList;
	}

	@Override
	public List<Coupon> getCoupons() {
		ResultSet rs;
		ArrayList<Coupon> couponList = new ArrayList<>();
		Statement st;
		String typeFromDB;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_ALL_COMPANY_COUPONS + "'" + CompanyFacade.getCompanyName() + "')");
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return couponList;
	}

	@Override
	public boolean login(String compName, int password) {
		ResultSet rs;
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_COMPANY_PASSWORD_BY_NAME + "'" + compName + "'");
				while (rs.next()) {
					if (password == rs.getInt(SQLConstantsQuery.COMPANY_PASSWORD)) {
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
