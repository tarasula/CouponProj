package db_package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import exceptions.CreateCompanyException;
import util.SQLConstantsQuery;

public class CompanyDBDAO implements CompanyDAO {

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
	public void createCompany(Company comp){
		try {
			getStatment().executeQuery(SQLConstantsQuery.INSERT_INTO_COMPANY_VALUES 
					+ "(" + comp.getId() + "," + comp.getCompName() 
					+ "," + comp.getPassword() + "," + comp.getEmail() + ");");
		} catch (SQLException e) {
//			throw new CreateCompanyException("The Company already exist");
		}
	}

	@Override
	public void removeCompany(Company comp) {
		try {
			getStatment().executeQuery(SQLConstantsQuery.REMOVE_COMPANY + comp.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//The question is what exactly we want to update in Company table?
	@Override
	public void updateCompany(Company comp) {
		ResultSet rs;
		Company company = getCompany((int) comp.getId());
		company.setId(comp.getId());
		company.setCompName(comp.getCompName());
		company.setPassword(comp.getPassword());
		company.setEmail(comp.getEmail());
			try {
				rs = getStatment().executeQuery(SQLConstantsQuery.UPDATE_COMPANY_SET );
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	@Override
	public Company getCompany(int id) {
		Company company = new Company();
		ResultSet rs;
		try {
			rs = getStatment().executeQuery(SQLConstantsQuery.SELECT_COMPANY_BY_ID + id );
			while(rs.next()){
				company.setId(rs.getLong(SQLConstantsQuery.COMPANY_ID));
				company.setCompName(rs.getString(SQLConstantsQuery.COMPANY_COMP_NAME));
				company.setPassword(rs.getString(SQLConstantsQuery.COMPANY_PASSWORD));
				company.setEmail(rs.getString(SQLConstantsQuery.COMPANY_EMAIL));
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
		try {
			rs = getStatment().executeQuery(SQLConstantsQuery.SELECT_ALL_COMPANIES);
			while (rs.next()) {
				Company comp = new Company();
				comp.setId(rs.getLong(SQLConstantsQuery.COMPANY_ID));
				comp.setCompName(rs.getString(SQLConstantsQuery.COMPANY_COMP_NAME));
				comp.setPassword(rs.getString(SQLConstantsQuery.COMPANY_PASSWORD));
				comp.setEmail(rs.getString(SQLConstantsQuery.COMPANY_EMAIL));
				companyList.add(comp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyList;
	}

	@Override
	public Collection<Coupon> getCoupons() {
		//TODO
		return null;
	}

	@Override
	public boolean login(String compName, int password) {
		ResultSet rs;
		try {
			rs = getStatment().executeQuery(SQLConstantsQuery.SELECT_COMPANY_PASSWORD_BY_NAME + compName);
			int psswrd = rs.getInt(SQLConstantsQuery.COMPANY_PASSWORD);
			if(password == psswrd){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
