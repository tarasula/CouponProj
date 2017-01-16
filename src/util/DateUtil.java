package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * This is utility class that uses for format and parse date.
 * @author  Andrey Orlov
 * @version 1.0
 * 
 */
public class DateUtil {
	
	/**SimpleDateFormat object for manipulation with Date */
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
	
	/**
	 * Method for check if the DB (Coupon) Date after current Date
	 * @param dateDB - Date from DB (Coupon)
	 * @return true/false
	 */
	public static boolean checkDates(Date dateDB) {
		Calendar cal = Calendar.getInstance();
		Date currentDate = null;
		currentDate = cal.getTime();
		if (dateDB.before(currentDate)) {
			return true;
		} else {
			return false;
		}
	}
}
