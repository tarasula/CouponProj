package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
	
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
