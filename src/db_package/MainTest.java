package db_package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;

import util.SQLConstantsQuery;

public class MainTest{

	public static void main(String[] args) throws ParseException, SQLException {

		ConnectionPool.getInstance().getConnection();

		
//		CustomerDBDAO c1 = new CustomerDBDAO();
//		Customer cos = new Customer(19, "Shlomi", "666");
//		c1.createCustomer(cos);
//		c1.removeCustomer(cos);
//		c1.updateCustomer(cos);
//		System.out.println(c1.getCustomer(cos.getId()));
//		ArrayList<Customer> list = (ArrayList) c1.getAllCustomer();
//		checkList(list);
//		c1.login("Andrey", 665);
		
//		CompanyDBDAO c2 = new CompanyDBDAO();
//		Company com = new Company(2, "Andrey", "131", "email1");
//		checkList(c2.getCoupons());
//		c2.createCompany(com);
//		c2.removeCompany(com);
//		c2.updateCompany(com);
//		System.out.println(c2.getCompany(com.getId()));
//		ArrayList<Company> list = (ArrayList) c2.getAllCompanies();
//		checkList(list);
//		c2.login("Andrey", 131);
//		checkList();
		
//		SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss"); 
		
		Connection con;
		Statement st;
		ResultSet rs;
		con = ConnectionPool.getInstance().getConnection();
		try {
			st = con.createStatement();
			rs = st.executeQuery(SQLConstantsQuery.SELECT_END_DATE_OF_COUPONS);
			while(rs.next()){
				System.out.println(rs.getDate("END_DATE"));
//				System.out.println(rs.getInt("ID"));
//				System.out.println(rs.getString("TITLE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		Calendar calendar = Calendar.getInstance();
//		Date date2 = calendar.getTime();
//		System.out.println(date2);
//		CouponDBDAO c = new CouponDBDAO();
//		Coupon coupon = new Coupon(2, "title2", "message2", "image", 3.4, new Date(2012,1,1), new Date(2013,2,2), 123, CouponType.FOOD);
//		Coupon coupon = new Coupon(2, "title17", "message17", "17", 44.2, "2015-11-23", "2015-12-23", 7732, CouponType.HEALTH);
		
//		c.createCoupon(coupon);
//		c.removeCoupon(coupon);
//		c.updateCoupon(coupon);
//		System.out.println(c.getCoupon(coupon.getId()));
//		ArrayList<Coupon> list = (ArrayList) c.getAllCoupon();
//		checkList(list);
//		checkList(c.getCouponByType(CouponType.HEALTH));
		
		
//		ResultSet rs;
//		//Get Coupon by ID
//		rs = c.getStatment().executeQuery(SQLConstantsQuery.SELECT_COUPON_BY_ID + 2 );
//		while(rs.next()){
//			//Get Coupon type
//			String s = rs.getString(SQLConstantsQuery.COUPON_TYPE);
//			System.out.println(s);
//			for(CouponType cp : CouponType.values()){
//				String str2 = cp.toString();
//				System.out.println(str2);
//				if(s.equals(str2))
//					System.out.println(true);
//			}
//			System.out.println(false);
//		}
//		
//		String str = CouponType.CAMPING.toString();
//		for(CouponType cp : CouponType.values()){
//			String str2 = cp.toString();
//			if(str.equals(str2))
//				System.out.println(true);
//		}
//		checkDates();
	}
	
	public static void checkDates() {
		String date_s = "2018-01-31";
		SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-mm-dd");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date date = null, date2 = null;
		try {
			date = dt1.parse(date_s);
			String dateStr = dateFormat.format(cal.getTime());
			date2 = dt1.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (date.after(date2)) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
	}
	
	private static void checkList(List<Coupon> list) {
		for(int i=0; i<list.size(); i++){
			System.out.print(list.get(i));
		}
	}
	
	// every night at 2am the task will be run
	public void startDailyCouponExpirationTask(){
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 2);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);

		Timer timer = new Timer();
		Thread expirationThread = new Thread(new DailyCouponExpirationTask());
//		timer.schedule(expirationThread, today.getTime());
	}
}
