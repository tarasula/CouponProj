package facade;

import java.util.ArrayList;
import java.util.Collection;

import db_package.CompanyDBDAO;
import db_package.Coupon;
import db_package.CouponDBDAO;
import db_package.CouponType;

public class CompanyFacade implements CouponClientFacade {

	private CouponDBDAO couponDAO;
	private CompanyDBDAO companyDAO;
	private static String companyName;
	//= "Ness"

	public CompanyFacade() {
		couponDAO = new CouponDBDAO();
		companyDAO = new CompanyDBDAO();
	}

	public void createCoupon(Coupon coup) {
		couponDAO.createCoupon(coup);
	}

	public void removeCoupon(Coupon coup) {
		couponDAO.removeCoupon(coup);
	}

	public void updateCoupon(Coupon coup) {
		couponDAO.updateCoupon(coup);
	}

	public Coupon getCoupon(long id) {
		return couponDAO.getCoupon(id);
	}

	public Collection<Coupon> getAllCoupons() {
		return companyDAO.getCoupons();
	}

	public Collection<Coupon> getCouponsByType(CouponType coupType) {
		ArrayList<Coupon> couponByType = new ArrayList<>();
		ArrayList<Coupon> allCoupons = (ArrayList<Coupon>) companyDAO.getCoupons();
		for (int i = 0; i < allCoupons.size(); i++) {
			if (allCoupons.get(i).getType().toString().trim().equals(coupType.toString().trim())) {
				couponByType.add(allCoupons.get(i));
			}
		}
		return couponByType;
	}

	public static String getCompanyName() {
		return companyName;
	}

	public static void setCompanyName(String companyName) {
		CompanyFacade.companyName = companyName;
	}

	@Override
	public CouponClientFacade login(String name, String password, String clienType) {
		CompanyFacade.setCompanyName(name);
		if (companyDAO.login(name, password)) {
			return this;
		}
		return null;
	}

}
