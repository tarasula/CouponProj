package util;

import java.text.DecimalFormat;
import java.util.List;

import db_package.Coupon;

public class CheckCouponPrice {

	public static double getCorrectPrice(double numFromDB, int count) {
		String formattedDouble;
		if (count == 2) {
			formattedDouble = new DecimalFormat("#0.00").format(numFromDB);
			return Double.parseDouble(formattedDouble);
		} else if (count == 1) {
			formattedDouble = new DecimalFormat("#0.0").format(numFromDB);
			return Double.parseDouble(formattedDouble);
		}
		return 0;
	}

	public static int countOfNumersAfterPoint(double num) {
		return String.valueOf(num).split("\\.")[1].length();
	}
	
	public static List<Coupon> fixDBPrice(List<Coupon> list,double price ){
		int pCount = countOfNumersAfterPoint(price);
		String newPrice;
		for(int i=0; i<list.size(); i++){
			if(pCount == 1){
				newPrice = new DecimalFormat("#0.0").format(list.get(i).getPrice());
				list.get(i).setPrice(Double.parseDouble(newPrice));
			}else if(pCount == 2){
				newPrice = new DecimalFormat("#0.00").format(list.get(i).getPrice());
				list.get(i).setPrice(Double.parseDouble(newPrice));
			}
		}
		return list;
	}
}