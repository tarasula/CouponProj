/**
 * 
 */
package db_package;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Andrey Orlov
 *
 */
public class Coupon {
	private long id;
	private String title, message, image;
	private double price;
	private Date startDate, endDate;
	private int amount;
	
	private CouponType type;
	protected static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
	
	public Coupon(){
		
	}

	public Coupon(long id, String title, String message, String image, double price, 
			String startDate, String endDate, int amount, CouponType type) throws ParseException {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
		this.image = image;
		this.price = price;
		this.startDate = formatter.parse(startDate);
		this.endDate = formatter.parse(endDate);
		this.amount = amount;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStartDate() {
		return formatter.format(this.startDate);
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return formatter.format(this.endDate);
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", message=" + message + ", image=" + image + ", price="
				+ price + ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", type=" + type
				+ "]";
	}
}
