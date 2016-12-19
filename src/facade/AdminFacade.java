package facade;

import java.util.Collection;

import db_package.Company;
import db_package.CompanyDBDAO;
import db_package.Customer;
import db_package.CustomerDBDAO;

public class AdminFacade implements CouponClientFacade {

	private CompanyDBDAO companyDAO;
	private CustomerDBDAO customerDAO;
	
	
	public AdminFacade() {
		
	}

	/*
	 *Company methods 
	 */
	public void createCompany(Company comp){
		
	}
	
	public void removeCompany(Company comp){
		
	}
	
	public void upfateCompany(Company comp){
		
	}
	
	public Company getCompany(int id){
		
		return null;
	}
	
	public Collection<Company> getAllCompanies(){
		
		return null;
	}
	
	/*
	 * Customer methods
	 */
	public void createCustomer(Customer cost){
		
	}
	
	public void removeCustomer(Customer cost){
		
	}
	
	public void upfateCustomer(Customer cost){
		
	}
	
	public Customer getCustomer(int id){
		
		return null;
	}
	
	public Collection<Customer> getAllCustomers(){
		
		return null;
	}
	
	@Override
	public CouponClientFacade login(String name, int password, String clienType) {

		return null;
	}

}
