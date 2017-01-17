package utils;

import java.text.DecimalFormat;
import java.util.List;

import db_package.Coupon;
/**
 * This class should to fix price issue.
 * When the price data fetched from DB the price with type Double comes with many numbers after point.
 * For example: 45.786624424443442
 * @author Andrey Orlov
 *
 */
public class CheckCouponPrice {
	/**Constant - One number after point in price*/
	private final static int ONE_NUMBER_AFTER_POINT = 1;
	/**Constant - Two number after point in price*/
	private final static int TWO_NUMBER_AFTER_POINT = 2;
	
/**
 * This Method check how many numbers should be after point and return correct price 
 * @param numFromDB - this is the price with many numbers after point
 * @param countOfNumbersAfterPoint - this parameter tell us how many numbers should be after point. 
 * @return correct price (Double type)
 */
	public static double getCorrectPrice(double numFromDB, int countOfNumbersAfterPoint) {
		String formattedDouble;
		if (countOfNumbersAfterPoint == TWO_NUMBER_AFTER_POINT) {
			formattedDouble = new DecimalFormat("#0.00").format(numFromDB);
			return Double.parseDouble(formattedDouble);
		} else if (countOfNumbersAfterPoint == ONE_NUMBER_AFTER_POINT) {
			formattedDouble = new DecimalFormat("#0.0").format(numFromDB);
			return Double.parseDouble(formattedDouble);
		}
		return 0;
	}

	/**
	 * This method get number (price) and return count of numbers after point
	 * @param num - Number (price) for thinking how many number it have after point
	 * @return count of numbers after point
	 */
	public static int countOfNumersAfterPoint(double num) {
		return String.valueOf(num).split("\\.")[1].length();
	}
	
}