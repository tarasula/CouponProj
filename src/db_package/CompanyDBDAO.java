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

	public CompanyDBDAO() {

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
	public void createCompany(Company comp) {
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				List<Company> allCopmanys = (List<Company>) getAllCompanies();
				boolean flag = false;
				for(int i=0; i<allCopmanys.size(); i++){
					if(comp.getCompName().equalsIgnoreCase(allCopmanys.get(i).getCompName())){
						flag = true;
					}
				}
				if(!flag){
				st.execute(SQLConstantsQuery.INSERT_INTO_COMPANY_VALUES + "(" + comp.getId() + ",'" + comp.getCompName()
						+ "','" + comp.getPassword() + "','" + comp.getEmail() + "');");
				System.out.println("Company " + comp.getCompName() + " was created");
				}else{
					throw new ProjectException("Can't create " + comp.getCompName() + " company, because is already exist.");
				}
			}
			st.close();
		} catch (SQLException | ProjectException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeCompany(Company comp) {
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				st.execute(SQLConstantsQuery.REMOVE_COMPANY_COUPONS + comp.getId() + ")");
				st.execute(SQLConstantsQuery.REMOVE_FROM_CUSTOMER_COUPONS + comp.getId() + ")");
				st.execute(SQLConstantsQuery.REMOVE_FROM_COMPANY_COUPONS + comp.getId());
				st.execute(SQLConstantsQuery.REMOVE_COMPANY + comp.getId());
				System.out.println("The Company " + comp.getCompName() + " removed");
			}
			st.close();
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
		ArrayList<Company> allCompanys = new ArrayList<>();
		boolean flag = false;
		allCompanys = (ArrayList<Company>) getAllCompanies();
		for (int i = 0; i < allCompanys.size(); i++) {
			if (comp.getCompName().equals(allCompanys.get(i).getCompName())) {
				flag = true;
			}
		}
		Statement st;
		if (flag) {
			try {
				st = getStatment();
				if (st != null) {
					st.execute(SQLConstantsQuery.UPDATE_COMPANY_SET + comp.getId() + ", PASSWORD = '"
							+ comp.getPassword() + "', EMAIL = '" + comp.getEmail() + "' WHERE COMP_NAME = '" + comp.getCompName() + "'");
					System.out.println("The Company " + comp.getCompName() + " was updated");
				}
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new ProjectException("The Company is not exist. You can created this Company.");
			} catch (ProjectException e) {
				e.printStackTrace();
				e.getMessage();
			}
		}
	}

	@Override
	public Company getCompany(long l) {
		Company company = new Company();
		ResultSet rs = null;
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_COMPANY_BY_ID + l);
				company.setCompName(SQLConstantsQuery.EMPTY);
				while (rs.next()) {
					company.setId(rs.getLong(SQLConstantsQuery.COMPANY_ID));
					company.setCompName(rs.getString(SQLConstantsQuery.COMPANY_COMP_NAME).trim());
					company.setPassword(rs.getString(SQLConstantsQuery.COMPANY_PASSWORD).trim());
					company.setEmail(rs.getString(SQLConstantsQuery.COMPANY_EMAIL).trim());
				}
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}

	@Override
	public Collection<Company> getAllCompanies() {
		ResultSet rs = null;
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
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyList;
	}

	@Override
	public List<Coupon> getCoupons() {
		ResultSet rs = null;
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
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return couponList;
	}

	@Override
	public boolean login(String compName, String password) {
		ResultSet rs = null;
		Statement st;
		try {
			st = getStatment();
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Login failed.");
		return false;
	}

}
