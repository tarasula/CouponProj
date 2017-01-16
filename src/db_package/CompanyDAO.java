/**
 * 
 */
package db_package;

import java.util.Collection;

/**
 * Company Interface for speaking with DB  
 * @author Andrey Orlov
 * @version 1.0
 */
public interface CompanyDAO {
	
		/**
		 * Create Company method 
		 * @param crComp - Company for creating
		 * @throws Exception if Company already exist
		 */
	public void createCompany(Company crComp)throws Exception;
		/**
		 * Remove Company method 
		 * @param rmComp - Company for removing
		 * @throws Exception if no Company for removing
		 */
	public void removeCompany(Company rmComp)throws Exception;
		/**
		 * Update Company method 
		 * @param upComp - Company for updating
		 * @throws Exception if no Company for update 
		 */
	public void updateCompany(Company upComp)throws Exception;
		/**
		 * Get Company by specific ID
		 * @param id - Company id
		 * @return Company object from DB
		 * @throws Exception if Company with specific ID does not exist
		 */
	public Company getCompany(long id)throws Exception;
		/**
		 * Get all Companies method
		 * @return list with all Companies
		 * @throws Exception if the method have SQL exceptions
		 */
	public Collection<Company> getAllCompanies()throws Exception;
		/**
		 * Get specific company coupons method
		 * @return list of company coupons 
		 * @throws Exception if company does not have coupons
		 */
	public Collection<Coupon> getCoupons() throws Exception;
		/**
		 * Login method 
		 * @param custName - Company name
		 * @param password - Company password
		 * @return true or false
		 * @throws Exception if the login is failed
		 */
	public boolean login(String compName, String password)throws Exception;
}
