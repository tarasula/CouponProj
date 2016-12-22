package db_package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import exceptions.ProjectException;
import facade.CompanyFacade;
import util.SQLConstantsQuery;

public class CouponDBDAO implements CouponDAO {
	
	private ConnectionPool pool;

	public CouponDBDAO(){}
	
	protected Statement getStatment() {
		Connection con = null;
		Statement st = null;
		con = pool.getInstance().getConnection();
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}
	
	
	@Override
	public void createCoupon(Coupon coup) {
		Statement st;
		ResultSet rsType;
		try {
			st = getStatment();
			if(st != null){
				rsType = st.executeQuery(SQLConstantsQuery.SELECT_TYPES_OF_COUPONS);
				while(rsType.next()){
					if(rsType.getString(SQLConstantsQuery.COUPON_TYPE).toString().equals(coup.getType().toString())){
						throw new ProjectException("Can't insert coupon with this type. The type is exist!");
					}
				}
			st.execute(SQLConstantsQuery.INSERT_INTO_COUPON_VALUES
					+ "(" + coup.getId() + ",'" + coup.getTitle()
					+ "','" + coup.getStartDate() + "','" + coup.getEndDate() + "'," 
					+ coup.getAmount() + ",'" + coup.getType() + "','" + coup.getMessage()
					+ "'," + coup.getPrice() + ",'" + coup.getImage() + "');");
			ResultSet rs = st.executeQuery(SQLConstantsQuery.SELECT_COMPANY_ID_BY_NAME + "'" + CompanyFacade.getCompanyName() + "'");
			int companyID = rs.getInt(SQLConstantsQuery.COMPANY_ID);
			st.execute(SQLConstantsQuery.INSERT_INTO_COMPANY_COUPON_VALUES
					+ "(" + companyID + "," + coup.getId());
			System.out.println("Coupon " + coup.getTitle() + " added to Coupon DB and Conpany Coupon DB");
			}else{
				throw new ProjectException("The Statement is null...");
			}
		} catch (SQLException | ProjectException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeCoupon(Coupon coup) {
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				st.execute(SQLConstantsQuery.REMOVE_COUPON + coup.getId());
				st.execute(SQLConstantsQuery.DELETE_FROM_COMPANY_COUPON + coup.getId());
				System.out.println("The Coupon " + coup.getTitle().trim() + " removed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCoupon(Coupon coup) {
		Coupon coupon = getCoupon((long) coup.getId());
		if(coupon.getId() == coup.getId()){
			coupon.setId(coup.getId());
			coupon.setTitle(coup.getTitle());
			coupon.setAmount(coup.getAmount());
			coupon.setType(coup.getType());
			coupon.setMessage(coup.getMessage());
			coupon.setPrice(coup.getPrice());
			coupon.setImage(coup.getImage());
			try {
			coupon.setStartDate(Coupon.formatter.parse(coup.getStartDate()));
			coupon.setEndDate(Coupon.formatter.parse(coup.getEndDate()));
				getStatment().execute(SQLConstantsQuery.UPDATE_COUPON_SET
				+ coupon.getId() + ", TITLE = '" + coupon.getTitle() + "', START_DATE = '" 
				+ coupon.getStartDate() + "', END_DATE = '" + coupon.getEndDate()
				+ "', AMOUNT = " + coupon.getAmount() + ", TYPE = '" + coupon.getType()
				+ "', MESSAGE = '" + coupon.getMessage() + "', PRICE = " + coupon.getPrice() 
				+ ", IMAGE = '" + coupon.getImage() + "'" + " WHERE ID = " + coupon.getId());
				System.out.println("The Coupon " + coupon.getTitle()  + " was updated");
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			}
		}else{
			try {
				throw new ProjectException("The Coupon is not exist. You can add this Coupon to DB.");
			} catch (ProjectException e) {
				e.printStackTrace();
				e.getMessage();
			}
		}
		
	}

	@Override
	public Coupon getCoupon(long id) {
		Coupon coupon = new Coupon();
		ResultSet rs;
		String typeFromDB;
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_COUPON_BY_ID + id);
				while (rs.next()) {
					coupon.setId(rs.getLong(SQLConstantsQuery.ID));
					coupon.setTitle(rs.getString(SQLConstantsQuery.COUPON_TITLE).trim());
					coupon.setStartDate(rs.getDate(SQLConstantsQuery.COUPON_START_DATE));
					coupon.setEndDate(rs.getDate(SQLConstantsQuery.COUPON_END_DATE));
					coupon.setAmount(rs.getInt(SQLConstantsQuery.COUPON_AMOUNT));
					typeFromDB = rs.getString(SQLConstantsQuery.COUPON_TYPE).trim();
					CouponType ct = CouponType.valueOf(typeFromDB.toUpperCase(Locale.ENGLISH));
					coupon.setType(ct);
					coupon.setMessage(rs.getString(SQLConstantsQuery.COUPON_MESSAGE).trim());
					coupon.setPrice(rs.getDouble(SQLConstantsQuery.COUPON_PRICE));
					coupon.setImage(rs.getString(SQLConstantsQuery.COUPON_IMAGE).trim());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coupon;
	}

	@Override
	public Collection<Coupon> getAllCoupon() {
		ResultSet rs;
		String typeFromDB = null;
		ArrayList<Coupon> couponList = new ArrayList<>();
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_ALL_COUPONS);
				while (rs.next()) {
					Coupon coup = new Coupon();
					coup.setId(rs.getLong(SQLConstantsQuery.COMPANY_ID));
					coup.setTitle(rs.getString(SQLConstantsQuery.COUPON_TITLE).trim());
					coup.setStartDate(rs.getDate(SQLConstantsQuery.COUPON_START_DATE));
					coup.setEndDate(rs.getDate(SQLConstantsQuery.COUPON_END_DATE));
					coup.setAmount(rs.getInt(SQLConstantsQuery.COUPON_AMOUNT));
					typeFromDB = rs.getString(SQLConstantsQuery.COUPON_TYPE).trim();
					for (CouponType ct : CouponType.values()) {
						if (typeFromDB.equals(ct)) {
							coup.setType(ct);
						}
					}
					coup.setMessage(rs.getString(SQLConstantsQuery.COUPON_MESSAGE).trim());
					coup.setPrice(rs.getDouble(SQLConstantsQuery.COUPON_PRICE));
					coup.setImage(rs.getString(SQLConstantsQuery.COUPON_IMAGE).trim());
					couponList.add(coup);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return couponList;
	}

	@Override
	public List<Coupon> getCouponByType(CouponType coupType) {
		ResultSet rs;
		String typeFromDB = null;
		ArrayList<Coupon> couponListByType = new ArrayList<>();
		Statement st;
		try {
			st = getStatment();
			if (st != null) {
				rs = st.executeQuery(SQLConstantsQuery.SELECT_ALL_COUPONS_BY_TYPE + coupType + "'");
				while (rs.next()) {
					Coupon coup = new Coupon();
					coup.setId(rs.getLong(SQLConstantsQuery.COMPANY_ID));
					coup.setTitle(rs.getString(SQLConstantsQuery.COUPON_TITLE).trim());
					coup.setStartDate(rs.getDate(SQLConstantsQuery.COUPON_START_DATE));
					coup.setEndDate(rs.getDate(SQLConstantsQuery.COUPON_END_DATE));
					coup.setAmount(rs.getInt(SQLConstantsQuery.COUPON_AMOUNT));
					typeFromDB = rs.getString(SQLConstantsQuery.COUPON_TYPE).trim();
					for (CouponType ct : CouponType.values()) {
						if (typeFromDB.equals(ct)) {
							coup.setType(ct);
						}
					}
					coup.setMessage(rs.getString(SQLConstantsQuery.COUPON_MESSAGE).trim());
					coup.setPrice(rs.getDouble(SQLConstantsQuery.COUPON_PRICE));
					coup.setImage(rs.getString(SQLConstantsQuery.COUPON_IMAGE).trim());
					couponListByType.add(coup);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return couponListByType;
	}

}
