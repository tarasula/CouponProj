package db_package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import utils.CouponConstants;
import utils.SQLQueries;
/**
 * This class that uses for expiration task that check every day, dates of coupons.
 * @author  Andrey Orlov
 * @version 1.0
 * 
 */
public class DailyCouponExpirationTask implements Runnable {

	/**Link to Coupon DAO-layer*/
	private CouponDBDAO couponDAO;
	
	/**
	 * Create object for speaking with DAO-layer
	 */
	public DailyCouponExpirationTask() 
	{
		couponDAO = new CouponDBDAO();		
	}

	/**
	 * Method that check end dates of all coupons in DB, if coupon end date after current date, 
	 * the specified coupon will be removed.
	 */
	@Override
	public void run() {
		Connection con;
		Statement statement;
		
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		
		while (true) 
		{
			con = ConnectionPool.getInstance().getConnection();
			try 
			{
				statement = con.createStatement();
				ResultSet rs = statement.executeQuery(SQLQueries.SELECT_END_DATE_OF_COUPONS);
				while (rs.next()) 
				{
					if (rs.getDate(CouponConstants.COUPON_END_DATE).before(today)) 
					{
						Coupon couponForDelete = new Coupon();
						couponForDelete.setId(rs.getInt(CouponConstants.ID));
						couponForDelete.setTitle(rs.getString(CouponConstants.COUPON_TITLE));
						couponDAO.removeCoupon(couponForDelete);
					}
				}
				statement.close();
				rs.close();
				ConnectionPool.getInstance().returnConnection(con);
				TimeUnit.DAYS.sleep(1);
			} 
			catch (Exception e) 
			{
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * Method for stop expiration task
	 */
	public void stopTask()
	{
		Thread.currentThread().interrupt();
		System.out.println("Deily task is stopped.");
	}
}
