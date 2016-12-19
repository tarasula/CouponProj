package facade;

import java.util.Collection;

import db_package.Company;
import db_package.CompanyDBDAO;
import db_package.ConnectionPool;
import db_package.Customer;
import db_package.CustomerDBDAO;
import exceptions.ProjectException;

public class AdminFacade implements CouponClientFacade {

	private CompanyDBDAO companyDAO;
	private CustomerDBDAO customerDAO;
	private String ADMIN_NAME = "admin";
	private int PASSWORD = 1234;
	
	public AdminFacade() {
		
	}

	/*
	 *Company methods 
	 */
	public void createCompany(Company comp){
		companyDAO.createCompany(comp);
	}
	
	public void removeCompany(Company comp){
		companyDAO.removeCompany(comp);
	}
	
	public void updateCompany(Company comp){
		companyDAO.updateCompany(comp);
	}
	
	public Company getCompany(long id){
		return companyDAO.getCompany(id);
	}
	
	public Collection<Company> getAllCompanies(){
		return companyDAO.getAllCompanies();
	}
	
	/*
	 * Customer methods
	 */
	public void createCustomer(Customer cost){
		customerDAO.createCustomer(cost);
	}
	
	public void removeCustomer(Customer cost){
		customerDAO.removeCustomer(cost);
	}
	
	public void updateCustomer(Customer cost){
		customerDAO.updateCustomer(cost);
	}
	
	public Customer getCustomer(long id){
		return customerDAO.getCustomer(id);
	}
	
	public Collection<Customer> getAllCustomers(){
		return customerDAO.getAllCustomer();
	}
	
	//Which clientType should be on AdminFacade in login
	@Override
	public CouponClientFacade login(String name, int password, String clienType) {
		if(name.equalsIgnoreCase(ADMIN_NAME) && password == PASSWORD){			
			return (CouponClientFacade) ConnectionPool.getInstance();
		}
		try {
			throw new ProjectException("Login failed...");
		} catch (ProjectException e) {
			e.printStackTrace();
		}
		return null;
	}

}
