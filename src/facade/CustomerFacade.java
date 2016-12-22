
package facade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import db_package.ConnectionPool;
import db_package.Coupon;
import db_package.CouponDBDAO;
import db_package.CustomerDBDAO;
import util.SQLConstantsQuery;

public class CustomerFacade implements CouponClientFacade {

	
	private CustomerDBDAO customerDAO;
	private CouponDBDAO couponDAO;
	private static String customerName;

	public CustomerFacade() {
		couponDAO = new CouponDBDAO();
		customerDAO = new CustomerDBDAO();
	}

	public void purchaseCoupon(Coupon coup) {
		Connection con = ConnectionPool.getInstance().getConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(
					SQLConstantsQuery.SELECT_CUSTOMER_ID_BY_NAME + "'" + CustomerFacade.customerName + "'");
			int customerID = rs.getInt(SQLConstantsQuery.CUSTOMER_ID);
			st.execute(SQLConstantsQuery.INSERT_INTO_CUSTOMER_COUPON_VALUES + "(" + customerID + "," + coup.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Collection<Coupon> getAllPurchasedCoupons() {
		return customerDAO.getCoupons();
	}

	public Collection<Coupon> getAllPurchasedCouponsByType(String type) {
		ArrayList<Coupon> purchasedCouponByType = new ArrayList<>();
		ArrayList<Coupon> allPurchasedCoupons = (ArrayList<Coupon>) customerDAO.getCoupons();
		for (int i = 0; i < allPurchasedCoupons.size(); i++) {
			if (allPurchasedCoupons.get(i).getType().toString().equals(type)) {
				purchasedCouponByType.add(allPurchasedCoupons.get(i));
			}
		}
		return purchasedCouponByType;
	}

	public Collection<Coupon> getAllPurchasedCouponsByPrice(int price) {
		ArrayList<Coupon> purchasedCouponByPrice = new ArrayList<>();
		ArrayList<Coupon> allPurchasedCoupons = (ArrayList<Coupon>) customerDAO.getCoupons();
		for (int i = 0; i < allPurchasedCoupons.size(); i++) {
			if (allPurchasedCoupons.get(i).getPrice() == price) {
				purchasedCouponByPrice.add(allPurchasedCoupons.get(i));
			}
		}
		return purchasedCouponByPrice;
	}

	public static String getCustomerName() {
		return customerName;
	}

	public static void setCustomerName(String customerName) {
		CustomerFacade.customerName = customerName;
	}

	@Override
	public CouponClientFacade login(String name, int password, String clienType) {
		CustomerFacade.setCustomerName(name);
		if (customerDAO.login(name, password)) {
			return (CouponClientFacade) customerDAO;
		}
		return null;
	}

}
