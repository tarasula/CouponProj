package facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import db_package.Company;
import db_package.CompanyDBDAO;
import db_package.Coupon;
import db_package.CouponDBDAO;
import db_package.CouponType;
import exceptions.ProjectException;
/**
 * This class is Company business-layer level that uses for speaking 
 * with Company credentials with DAO-layer.
 * 
 * @author  Andrey Orlov
 * @version 1.0
 * 
 */
public class CompanyFacade implements CouponClientFacade {

	/**Link to Coupon DAO-layer*/
	private CouponDBDAO couponDAO;
	
	/**Link to Company DAO-layer*/
	private CompanyDBDAO companyDAO;
	
	/**Field Company name*/
	private static String companyName;

	/**
	 * Create objects for speaking with DAO-layer
	 */
	public CompanyFacade() {
		couponDAO = new CouponDBDAO();
		companyDAO = new CompanyDBDAO();
	}

	/**
	 * Method for create specific Coupon
	 * @param coup - Coupon object with fields
	 */
	public void createCoupon(Coupon coup) {
		try {
			couponDAO.createCoupon(coup);
		} catch (ProjectException | SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method for remove specific Coupon
	 * @param coup - Coupon object with fields
	 */
	public void removeCoupon(Coupon coup) {
		try {
			couponDAO.removeCoupon(coup);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method for update specific Coupon
	 * @param coup - Coupon object with fields
	 */
	public void updateCoupon(Coupon coup) {
		try {
			couponDAO.updateCoupon(coup);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method for get Coupons by ID
	 * @param id - Coupon ID
	 * @return Coupon Object 
	 */
	public Coupon getCoupon(long id) {
		try {
			return couponDAO.getCoupon(id);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Method for get all Coupons
	 * @return list of all Coupons 
	 */
	public Collection<Coupon> getAllCoupons() {
		try {
			return companyDAO.getCoupons();
		} catch (ProjectException | SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Method for get Coupons by type
	 * @param coupType - Coupon type 
	 * @return list of Coupons with specific type
	 */
	public Collection<Coupon> getCouponsByType(CouponType coupType) {
		ArrayList<Coupon> couponByType = new ArrayList<>();
		ArrayList<Coupon> allCoupons = null;
		try {
			allCoupons = (ArrayList<Coupon>) companyDAO.getCoupons();
			for (int i = 0; i < allCoupons.size(); i++) {
				if (allCoupons.get(i).getType().toString().trim().equals(coupType.toString().trim())) {
					couponByType.add(allCoupons.get(i));
				}
			}
			if (couponByType.isEmpty()) {
				throw new ProjectException("Coupon with " + coupType.toString() + " type is not exist!");
			}
		} catch (ProjectException | SQLException e) {
			System.err.println(e.getMessage());
		}
		return couponByType;
	}

	/**
	 * Method for get Coupons till specific price
	 * @param price - Price of Coupon
	 * @return List of Coupon till specific price
	 */
	public Collection<Coupon> getCouponsTillPrice(float price) {
		ArrayList<Coupon> couponTillPrice = new ArrayList<>();
		ArrayList<Coupon> allCoupons = null;
		
			try {
				allCoupons = (ArrayList<Coupon>) companyDAO.getCoupons();
				for(Coupon coup : allCoupons){
					if(price <= coup.getPrice()){
						couponTillPrice.add(coup);
					}
				}
			} catch (ProjectException | SQLException e) {
				System.err.println(e.getMessage());
			}
		return couponTillPrice;
	}
	
	/**
	 * Method for get Coupons till specific date
	 * @param date - Date of Coupon
	 * @return List of Coupons till specific date
	 */
	public Collection<Coupon> getCouponsTillDate(Date date) {
		ArrayList<Coupon> couponTillDate = new ArrayList<>();
		ArrayList<Coupon> allCoupons = null;
			try {
				allCoupons = (ArrayList<Coupon>) companyDAO.getCoupons();
				for(Coupon coup : allCoupons){
					if(date.equals(coup.getEndDate()) || coup.getEndDate().before(date)){
						couponTillDate.add(coup);
					}
				}
			} catch (ProjectException | SQLException e) {
				System.err.println(e.getMessage());
			}
		return couponTillDate;
	}
	
	/**
	 * Method for get Company name that logged in.
	 * @return Company name
	 */
	public static String getCompanyName() {
		return companyName;
	}

	/**
	 * Method for set Company name that logged in.
	 * @param companyName - Company name for set
	 */
	public static void setCompanyName(String companyName) {
		CompanyFacade.companyName = companyName;
	}
	
	/**
	 * Method for get specific Company by ID
	 * @param compId - ID of Company
	 * @return Specific company
	 */
	public Company getCompany(long compId){
		try {
			return companyDAO.getCompany(compId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to login with Company credential
	 * @param name - Company name
	 * @param password - Company password
	 * @param clienType - Type of client (Company, Customer, Administrator)
	 * @return CompanyFacade Object
	 */
	@Override
	public CouponClientFacade login(String name, String password, String clienType) {
		CompanyFacade.setCompanyName(name);
		try {
			if (companyDAO.login(name, password)) {
				return this;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

}
