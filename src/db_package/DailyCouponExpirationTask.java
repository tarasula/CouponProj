package db_package;

public class DailyCouponExpirationTask implements Runnable {

	private CouponDBDAO couponDAO;
	private boolean quit;
	
	
	public DailyCouponExpirationTask() {
		super();
	}

	@Override
	public void run() {
		
	}

	public void stopTask(){
		
	}
}
