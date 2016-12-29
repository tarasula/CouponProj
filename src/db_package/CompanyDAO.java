/**
 * 
 */
package db_package;

import java.util.Collection;

/**
 * @author P0021787
 *
 */
public interface CompanyDAO {

	public void createCompany(Company crComp);
	public void removeCompany(Company rmComp);
	public void updateCompany(Company upComp);
	public Company getCompany(long id);
	public Collection<Company> getAllCompanies();
	public Collection<Coupon> getCoupons();
	public boolean login(String compName, String password);
}
