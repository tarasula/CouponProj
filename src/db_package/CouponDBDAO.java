package db_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exceptions.ProjectException;
import facade.CompanyFacade;
import facade.CustomerFacade;
import util.DateUtil;
import util.SQLConstantsQuery;

public class CouponDBDAO implements CouponDAO {

	public CouponDBDAO(){}
	
	private Statement getStatment() {
		Connection con = null;
		Statement st = null;
		con = ConnectionPool.getInstance().getConnection();
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
	}
	
	
	@Override
	public void createCoupon(Coupon coup) {
		Statement stCreate;
		ResultSet rsTitle;
		try {
			stCreate = getStatment();
			if (stCreate != null) {
				rsTitle = stCreate.executeQuery(SQLConstantsQuery.SELECT_TITLE_OF_COUPONS);
				while (rsTitle.next()) {
					if (rsTitle.getString(SQLConstantsQuery.COUPON_TITLE).trim().equals(coup.getTitle())) {
						throw new ProjectException("Can't insert coupon with this title. The Coupon " + coup.getTitle() + " is exist!");
					}
				}
				stCreate.execute(SQLConstantsQuery.INSERT_INTO_COUPON_VALUES + "(" + coup.getId() + ",'" + coup.getTitle()
						+ "','" + DateUtil.formatter.format(coup.getStartDate()) + "','"
						+ DateUtil.formatter.format(coup.getEndDate()) + "'," + coup.getAmount() + ",'"
						+ coup.getType() + "','" + coup.getMessage() + "'," + coup.getPrice() + ",'" + coup.getImage()
						+ "');");
				ResultSet rs = stCreate.executeQuery(
						SQLConstantsQuery.SELECT_COMPANY_ID_BY_NAME + "'" + CompanyFacade.getCompanyName() + "'");
				int companyID = 0;
				while (rs.next()) {
					companyID = rs.getInt(SQLConstantsQuery.COMPANY_ID);
				}
				stCreate.execute(SQLConstantsQuery.INSERT_INTO_COMPANY_COUPON_VALUES + "(" + companyID + "," + coup.getId()
						+ ")");
				System.out.println("Coupon " + coup.getTitle() + " was created");
				rs.close();
			} else {
				throw new ProjectException("The Statement is null...");
			}
				stCreate.close();
		} catch (SQLException | ProjectException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeCoupon(Coupon coup) {
		Statement stRemove;
		try {
			stRemove = getStatment();
			if (stRemove != null) {
				stRemove.execute(SQLConstantsQuery.REMOVE_COUPON + coup.getId());
				stRemove.execute(SQLConstantsQuery.DELETE_FROM_COMPANY_COUPON + coup.getId());
				stRemove.execute(SQLConstantsQuery.DELETE_FROM_CUSTOMER_COUPON + coup.getId());
				System.out.println("The Coupon " + coup.getTitle().trim() + " removed");
			}
			stRemove.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCoupon(Coupon coup) {
		Coupon coupon = getCoupon((long) coup.getId());
		Statement stUpdate;
		if (coupon.getId() == coup.getId()) {
			coupon.setPrice(coup.getPrice());
			try {
				stUpdate = getStatment();
				if (stUpdate != null) {
					coupon.setEndDate(DateUtil.formatter.parse(DateUtil.formatter.format(coup.getEndDate())));
					stUpdate.execute(SQLConstantsQuery.UPDATE_COUPON_SET + DateUtil.formatter.format(coupon.getEndDate())
									+ "'" + ", PRICE = " + coupon.getPrice() + " WHERE ID = " + coupon.getId());
					System.out.println("The Coupon " + coupon.getTitle() + " was updated");
				}
				stUpdate.close();
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			}
		} else {
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
		ResultSet rs = null;
		String typeFromDB;
		Statement stGet;
		try {
			stGet = getStatment();
			if (stGet != null) {
				rs = stGet.executeQuery(SQLConstantsQuery.SELECT_COUPON_BY_ID + id);
				if(!rs.isBeforeFirst()){
					System.out.println("The Coupon with ID " + id + ", is not exist.");
					return null;
				}
				while (rs.next()) {
					coupon.setId(rs.getLong(SQLConstantsQuery.ID));
					coupon.setTitle(rs.getString(SQLConstantsQuery.COUPON_TITLE).trim());
					coupon.setStartDate(rs.getDate(SQLConstantsQuery.COUPON_START_DATE));
					coupon.setEndDate(rs.getDate(SQLConstantsQuery.COUPON_END_DATE));
					coupon.setAmount(rs.getInt(SQLConstantsQuery.COUPON_AMOUNT));
					typeFromDB = rs.getString(SQLConstantsQuery.COUPON_TYPE).trim();
					for (CouponType ct : CouponType.values()) {
						if (typeFromDB.equals(ct.toString())) {
							coupon.setType(ct);
						}
					}
					coupon.setMessage(rs.getString(SQLConstantsQuery.COUPON_MESSAGE).trim());
					coupon.setPrice(rs.getDouble(SQLConstantsQuery.COUPON_PRICE));
					coupon.setImage(rs.getString(SQLConstantsQuery.COUPON_IMAGE).trim());
				}
			}
			rs.close();
			stGet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coupon;
	}

	@Override
	public Collection<Coupon> getAllCoupon() {
		ResultSet rs = null;
		String typeFromDB = null;
		ArrayList<Coupon> couponList = new ArrayList<>();
		Statement stGetAll;
		try {
			stGetAll = getStatment();
			if (stGetAll != null) {
				rs = stGetAll.executeQuery(SQLConstantsQuery.SELECT_ALL_COUPONS);
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
			rs.close();
			stGetAll.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return couponList;
	}

	@Override
	public List<Coupon> getCouponByType(CouponType coupType) {
		ResultSet rs = null;
		String typeFromDB = null;
		ArrayList<Coupon> couponListByType = new ArrayList<>();
		Statement stGetByType;
		try {
			stGetByType = getStatment();
			if (stGetByType != null) {
				rs = stGetByType.executeQuery(SQLConstantsQuery.SELECT_ALL_COUPONS_BY_TYPE + coupType + "'");
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
			rs.close();
			stGetByType.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return couponListByType;
	}
	
	public void purchaseCoupon(Coupon coup) {
		Connection con = ConnectionPool.getInstance().getConnection();
		PreparedStatement updateAmountSt = null;

		try {
			Statement st = con.createStatement();
			ResultSet rsAmount = st.executeQuery(SQLConstantsQuery.SELECT_AMOUNT_FROM_COUPON + coup.getId());
			while (rsAmount.next()) {
				if (rsAmount.getInt(SQLConstantsQuery.COUPON_AMOUNT) <= 0 || DateUtil.checkDates(rsAmount.getDate(SQLConstantsQuery.COUPON_END_DATE))) {
					throw new ProjectException("This coupon is not available.");
				}
			}
			ResultSet rs = st.executeQuery(
					SQLConstantsQuery.SELECT_CUSTOMER_ID_BY_NAME + "'" + CustomerFacade.getCustomerName() + "'");
			int customerID = 0;
			while (rs.next()) {
				customerID = rs.getInt(SQLConstantsQuery.CUSTOMER_ID);
			}
			rs = st.executeQuery(SQLConstantsQuery.SELECT_COUPON_ID_FROM_CUSTOMER_COUPON + customerID);
			while (rs.next()) {
				if (coup.getId() == rs.getInt(SQLConstantsQuery.COUPON_ID)) {
					throw new ProjectException("You have already purchased this coupon.");
				}
			}
			st.execute(
					SQLConstantsQuery.INSERT_INTO_CUSTOMER_COUPON_VALUES + "(" + customerID + "," + coup.getId() + ")");
			updateAmountSt = con.prepareStatement(SQLConstantsQuery.UPDATE_AMOUNT_IN_COUPON);
			updateAmountSt.setLong(1, coup.getId());
			updateAmountSt.setLong(2, coup.getId());
			updateAmountSt.execute();
			System.out.println("The coupon " + coup.getTitle() + " purchased");
			rsAmount.close();
			rs.close();
			st.close();
		} catch (SQLException | ProjectException e) {
			e.printStackTrace();
		}
	}

}
