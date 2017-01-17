/**
 * 
 */
package db_package;

import java.util.Date;

import exceptions.OverallException;
import utils.DateUtils;

/**
 * class for create Coupon 
 * @author Andrey Orlov
 */

public class Coupon {
	/**Field ID for Coupon identity */
	private long id;
	
	/**Fields title - The title of Coupon, message - the message of Coupon
	 * image - the image of Coupon */
	private String title, message, image;
	
	/**Field price - the price of Coupon*/
	private double price;
	
	/**Field startDate - the start Date of Coupon, endDate - the end Date of Coupon*/
	private Date startDate, endDate;
	
	/**Field amount - the amount of Coupon*/
	private int amount;
	
	/**Field type - the type of Coupon
	 * @see CouponType class*/
	private CouponType type;
	
	/**
	 * Create object constructor
	 */
	public Coupon(){
		
	}

	/**
	 * Get Coupon ID method
	 * @return id of Coupon
	 */
	public long getId() {
		return id;
	}

	/**
	 * Set Coupon ID method
	 * @param id - for set Coupon id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Get Coupon title method
	 * @return title of Coupon
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set Coupon title method
	 * @param title - for set Coupon title
	 */
	public void setTitle(String title) {
		this.title = title.trim();
	}

	/**
	 * Get Coupon message method
	 * @return message of Coupon
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set Coupon message method
	 * @param message - for set Coupon message
	 */
	public void setMessage(String message) {
		this.message = message.trim();
	}

	/**
	 * Get Coupon image method
	 * @return image of Coupon
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Set Coupon image method
	 * @param image - for set Coupon image
	 */
	public void setImage(String image) {
		this.image = image.trim();
	}

	/**
	 * Get Coupon price method
	 * @return price of Coupon
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Set Coupon price method
	 * @param price - for set Coupon price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Get Coupon startDate method
	 * @return startDate of Coupon
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Set Coupon startDate method
	 * @param startDate - for set Coupon startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get Coupon endDate method
	 * @return endDate of Coupon
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Set Coupon endDate method
	 * @param endDate - for set Coupon endDate
	 * @throws OverallException - The End Date of Coupon is expired!
	 */
	public void setEndDate(Date endDate) throws OverallException {
		if(!DateUtils.checkDates(endDate)){			
			this.endDate = endDate;
		}else{
			throw new OverallException("The End Date of Coupon is expired!");
		}
	}

	/**
	 * Get Coupon amount method
	 * @return amount of Coupon
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Set Coupon amount method
	 * @param amount - for set Coupon amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Get Coupon type method
	 * @return type of Coupon
	 */
	public CouponType getType() {
		return type;
	}

	/**
	 * Set Coupon type method
	 * @param type - for set Coupon type
	 */
	public void setType(CouponType type) {
		this.type = type;
	}

	/**
	 * Method for print Coupon
	 */
	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", message=" + message + ", image=" + image + ", price="
				+ price + ", startDate=" + startDate + ", endDate=" + endDate + ", amount=" + amount + ", type=" + type
				+ "]";
	}
}
