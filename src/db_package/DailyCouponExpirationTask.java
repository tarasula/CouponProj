package db_package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import util.SQLConstantsQuery;

public class DailyCouponExpirationTask extends TimerTask implements Runnable {

	private CouponDBDAO couponDAO;
	private boolean quit;
	
//	Date date ;
//	Timer timer ;
	
	public DailyCouponExpirationTask() {
		super();
//		timer = new Timer();
		
	}

	@Override
	public void run() {
		Connection con;
		Statement st;
		ResultSet rs;
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		
		con = ConnectionPool.getInstance().getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(SQLConstantsQuery.SELECT_END_DATE_OF_COUPONS);
			while(rs.next()){
				if(rs.getDate(SQLConstantsQuery.COUPON_END_DATE).before(today)){
					Coupon couponForDelete = new Coupon();
					couponForDelete.setId(rs.getInt(SQLConstantsQuery.COUPON_ID));
					couponForDelete.setTitle(rs.getString(SQLConstantsQuery.COUPON_TITLE));
					couponDAO.removeCoupon(couponForDelete);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void stopTask(){
		
	}
}
