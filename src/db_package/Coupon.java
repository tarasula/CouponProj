/**
 * 
 */
package db_package;

import java.util.Date;

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
	
	public Coupon(){
		
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
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
