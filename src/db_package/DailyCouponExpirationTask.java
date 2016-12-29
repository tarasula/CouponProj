package db_package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import util.SQLConstantsQuery;

public class DailyCouponExpirationTask implements Runnable {

	private CouponDBDAO couponDAO;
	private Object key = new Object();
	public DailyCouponExpirationTask() {
		couponDAO = new CouponDBDAO();		
	}

	@Override
	public void run() {
		Connection con;
		Statement statement;
		
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		con = ConnectionPool.getInstance().getConnection();
		
		while (true) {
			try {
				statement = con.createStatement();
				ResultSet rs = statement.executeQuery(SQLConstantsQuery.SELECT_END_DATE_OF_COUPONS);
				while (rs.next()) {
					if (rs.getDate(SQLConstantsQuery.COUPON_END_DATE).before(today)) {
						Coupon couponForDelete = new Coupon();
						couponForDelete.setId(rs.getInt(SQLConstantsQuery.ID));
						couponForDelete.setTitle(rs.getString(SQLConstantsQuery.COUPON_TITLE));
//						couponForDelete.setAmount(0);
//						couponForDelete.setImage("");
//						couponForDelete.setEndDate(null);
//						couponForDelete.setStartDate(null);
//						couponForDelete.setType(null);
//						couponForDelete.setMessage("");
//						couponForDelete.setPrice(0);
						couponDAO.removeCoupon(couponForDelete);
					}
				}
				statement.close();
				rs.close();
				TimeUnit.DAYS.sleep(1);
			} catch (SQLException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
	public void stopTask(){
		Thread.currentThread().interrupt();
	}
}
