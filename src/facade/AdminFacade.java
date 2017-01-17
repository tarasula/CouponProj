package facade;

import java.util.Collection;

import db_package.ClientType;
import db_package.Company;
import db_package.CompanyDBDAO;
import db_package.Customer;
import db_package.CustomerDBDAO;
import exceptions.LogInException;
import exceptions.OverallException;
import utils.AdminConstants;

/**
 * This class is Administrator business-layer level that uses for speaking 
 * with administrator credentials with DAO-layer.
 * 
 * @author  Andrey Orlov
 * @version 1.0
 * 
 */
public class AdminFacade implements CouponClientFacade {
	/**Link to Company DAO-layer*/
	private CompanyDBDAO companyDAO;
	
	/**Link to Customer DAO-layer*/
	private CustomerDBDAO customerDAO;

	/**
	 * Create objects for speaking with DAO-layer
	 */
	public AdminFacade() {
		companyDAO = new CompanyDBDAO();
		customerDAO = new CustomerDBDAO();
	}

	/**
	 * Method for create company
	 * @param comp - Company object with field
	 */
	public void createCompany(Company comp) {
		try {
			companyDAO.createCompany(comp);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method for remove company
	 * @param comp - Company object with field
	 */
	public void removeCompany(Company comp) {
		try {
			companyDAO.removeCompany(comp);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method for update company
	 * @param comp - Company object with field
	 */
	public void updateCompany(Company comp) {
		try {
			companyDAO.updateCompany(comp);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method for get company by ID
	 * @param id - Company ID
	 * @return specified Company by ID
	 */
	public Company getCompany(long id) {
		try {
			return companyDAO.getCompany(id);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Method for get all companies
	 * @return list of Companies
	 */
	public Collection<Company> getAllCompanies() {
		try {
			return companyDAO.getAllCompanies();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Method for create customer
	 * @param cost - Customer object with field
	 */
	public void createCustomer(Customer cost) {
		try {
			customerDAO.createCustomer(cost);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method for remove customer
	 * @param cost - Customer object with field
	 */
	public void removeCustomer(Customer cost) {
		try {
			customerDAO.removeCustomer(cost);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method for update customer
	 * @param cost - Customer object with field
	 */
	public void updateCustomer(Customer cost) {
		try {
			customerDAO.updateCustomer(cost);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method for get customer by ID
	 * @param id - Customer ID
	 * @return specified Customer by ID
	 */
	public Customer getCustomer(long id) {
		try {
			return customerDAO.getCustomer(id);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	/**
	 * Method for get all customers
	 * @return list of Customers
	 */
	public Collection<Customer> getAllCustomers() {
		try {
			return customerDAO.getAllCustomer();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Method to login with Administration credential
	 * this method check only name and password of Administrator that contains this class
	 * @param name - Administrator name
	 * @param password - Administrator password
	 * @param clienType - Type of client (Administrator, Company, Customer)
	 * @return AdminFacade Object
	 */
	@Override
	public CouponClientFacade login(String name, String password, ClientType clienType) {
		if (name.equalsIgnoreCase(AdminConstants.ADMIN_NAME) && password.equalsIgnoreCase(AdminConstants.PASSWORD)) {
			System.out.println("Admin login success.");
			return this;
		}
		try {
			throw new LogInException(this);
		} catch (LogInException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

}
