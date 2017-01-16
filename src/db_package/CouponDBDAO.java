package db_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exceptions.ProjectException;
import facade.CompanyFacade;
import facade.CustomerFacade;
import util.DateUtil;
import util.SQLConstantsQuery;
/**
 * This class is Coupon DAO-layer that uses for speaking 
 * with DB and relevant tables.
 * @author Andrey Orlov
 *
 */
public class CouponDBDAO implements CouponDAO {
	/**
	 * Create object for speaking with DB
	 */
	public CouponDBDAO(){}
	
	/**
	 * Get connection method from ConnectionPool class for speaking with DB
	 * @return connection
	 */
	private Connection getConnection() {
		Connection con = null;
		con = ConnectionPool.getInstance().getConnection();
		return con;
	}
	
	/**
	 * Method for create specific Coupon in Coupon DB, and in Company_Coupon DB 
	 * @param coup - Specific Coupon for creating
	 */
	@Override
	public void createCoupon(Coupon coup) throws ProjectException, SQLException{
		ResultSet rsTitle = null;
		PreparedStatement prepStCreate = null;
		Statement stCreate;
		Connection con;
			con = getConnection();
			stCreate = con.createStatement();
			if (stCreate != null) {
				rsTitle = stCreate.executeQuery(SQLConstantsQuery.SELECT_COUPON_BY_TITLE + coup.getTitle() + "'");
				if(rsTitle.next()) {
						throw new ProjectException("Can't insert coupon with this title. The Coupon " + coup.getTitle() + " is exist!");
				}
				prepStCreate = con.prepareStatement(SQLConstantsQuery.INSERT_INTO_COUPON_VALUES, Statement.RETURN_GENERATED_KEYS);
				prepStCreate.setString(1, coup.getTitle());
				prepStCreate.setString(2, DateUtil.formatter.format(coup.getStartDate()));
				prepStCreate.setString(3, DateUtil.formatter.format(coup.getEndDate()));
				prepStCreate.setInt(4, coup.getAmount());
				prepStCreate.setString(5, coup.getType().toString());
				prepStCreate.setString(6, coup.getMessage());
				prepStCreate.setDouble(7, coup.getPrice());
				prepStCreate.setString(8, coup.getImage());
				prepStCreate.executeUpdate();

				ResultSet keys = prepStCreate.getGeneratedKeys();
				long id = 0;
				if (keys.next()) {
					id = keys.getLong(1);
					coup.setId(id);
				}
				ResultSet rs = stCreate.executeQuery(SQLConstantsQuery.SELECT_COMPANY_ID_BY_NAME + "'" + CompanyFacade.getCompanyName() + "'");
				int companyID = 0;
				if (rs.next()) {
					companyID = rs.getInt(SQLConstantsQuery.COMPANY_ID);
				}
				stCreate.execute(SQLConstantsQuery.INSERT_INTO_COMPANY_COUPON_VALUES + "(" + companyID + "," + coup.getId() + ")");
				System.out.println("Coupon " + coup.getTitle() + " was created");
				rs.close();
			}
			stCreate.close();
			rsTitle.close();
			prepStCreate.close();
			ConnectionPool.getInstance().returnConnection(con);
	}

	/**
	 * Method for remove specific Coupon from Coupon DB, Company_Coupon DB and Customer_Coupon DB
	 * @param coup - Specific Coupon for removing
	 */
	@Override
	public void removeCoupon(Coupon coup) throws Exception {
		PreparedStatement stRemove;
		Statement st;
		Connection con;
		ResultSet rsID = null;
			con = getConnection();
			stRemove = con.prepareStatement(SQLConstantsQuery.SELECT_COMPANY_ID_AND_COUPON_ID);
			stRemove.setString(1, CompanyFacade.getCompanyName());
			stRemove.setString(2, coup.getTitle());
			rsID = stRemove.executeQuery();
			if (stRemove != null) {
				if(rsID.next()){
					int coupID = 0;
					coupID = rsID.getInt(SQLConstantsQuery.COUPON_ID);
//					compID = rsID.getInt(SQLConstantsQuery.COMP_ID);
					st = con.createStatement();
					st.execute(SQLConstantsQuery.REMOVE_COUPON + coupID);
					st.execute(SQLConstantsQuery.DELETE_FROM_COMPANY_COUPON + coupID);
					st.execute(SQLConstantsQuery.DELETE_FROM_CUSTOMER_COUPON + coupID);
					System.out.println("The Coupon " + coup.getTitle().trim() + " removed");
				}else{
					throw new ProjectException("The Coupon is not exist!");
				}
			}
			stRemove.close();
			rsID.close();
			ConnectionPool.getInstance().returnConnection(con);
	}

	/**
	 * Method for update specific Coupon from Coupon DB
	 * @param coup - Specific Coupon for updating
	 */
	@Override
	public void updateCoupon(Coupon coup) throws Exception {
		Statement stUpdate;
		Connection con;
		ResultSet rs = null;
		con = getConnection();
		stUpdate = con.createStatement();
		rs = stUpdate.executeQuery(SQLConstantsQuery.SELECT_COUPON_ID_BY_TITLE + "'" + coup.getTitle() + "'");
		if(rs.next()){
			coup.setId(rs.getLong(SQLConstantsQuery.ID));
		}else{
			throw new ProjectException("The Coupon is not exist!");
		}
		Coupon coupon = getCoupon((long) coup.getId());
		if (coupon.getId() == coup.getId()) {
				if (stUpdate != null) {
					coupon.setPrice(coup.getPrice());
					coupon.setEndDate(DateUtil.formatter.parse(DateUtil.formatter.format(coup.getEndDate())));
					stUpdate.execute(SQLConstantsQuery.UPDATE_COUPON_SET + DateUtil.formatter.format(coupon.getEndDate())
									+ "'" + ", PRICE = " + coupon.getPrice() + " WHERE ID = " + coupon.getId());
					System.out.println("The Coupon " + coupon.getTitle() + " was updated");
				}
			
		} else {
				throw new ProjectException("The Coupon is not exist. You can add this Coupon to DB.");
		}
		stUpdate.close();
		rs.close();
		ConnectionPool.getInstance().returnConnection(con);
	}

	/**
	 * Method for get specific Coupon from DB
	 * @param id - Specific Coupon ID
	 * @return Coupon object
	 */
	@Override
	public Coupon getCoupon(long id) throws Exception {
		Coupon coupon = new Coupon();
		String typeFromDB;
		Statement stGet;
		Connection con;
		ResultSet rs = null;
		con = getConnection();
		stGet = con.createStatement();
		if (stGet != null) {
			rs = stGet.executeQuery(SQLConstantsQuery.SELECT_COUPON_BY_ID + id);
			if (!rs.isBeforeFirst()) {
				throw new ProjectException("The Coupon with ID " + id + ", is not exist.");
			} else {
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
		}
		rs.close();
		stGet.close();
		ConnectionPool.getInstance().returnConnection(con);
		return coupon;
	}

	/**
	 * Method for get all Coupons
	 * @return list of Coupons
	 */
	@Override
	public Collection<Coupon> getAllCoupon() throws Exception {
		ResultSet rs = null;
		String typeFromDB = null;
		ArrayList<Coupon> couponList = new ArrayList<>();
		Statement stGetAll;
		Connection con;
			con = getConnection();
			stGetAll = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (stGetAll != null) {
				rs = stGetAll.executeQuery(SQLConstantsQuery.SELECT_ALL_COUPONS);
				if (!rs.next()) {
					return couponList;
				}
				rs.absolute(0);
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
			ConnectionPool.getInstance().returnConnection(con);
		return couponList;
	}

	/**
	 * Method for get specific Coupons by type
	 * @param coupType - Specific Coupon type
	 * @return list of Coupon with specific type
	 */
	@Override
	public List<Coupon> getCouponByType(CouponType coupType) throws Exception {
		ResultSet rs = null;
		String typeFromDB = null;
		ArrayList<Coupon> couponListByType = new ArrayList<>();
		Statement stGetByType;
		Connection con;
			con = getConnection();
			stGetByType = con.createStatement();
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
			ConnectionPool.getInstance().returnConnection(con);
		return couponListByType;
	}
	
	/**
	 * Method for purchase Coupon 
	 * @param coup - Specific Coupon for purchase
	 */
	@Override
	public void purchaseCoupon(Coupon coup) throws Exception {
		Connection con = ConnectionPool.getInstance().getConnection();
		PreparedStatement updateAmountSt = null;
		Statement st ;
		PreparedStatement prpSt;
		ResultSet rsCheck = null;
			prpSt = con.prepareStatement(SQLConstantsQuery.CHECK_IF_COUPON_WAS_PURCHASED);
			prpSt.setString(1, coup.getTitle());
			rsCheck = prpSt.executeQuery();
			if(rsCheck.next()){
				throw new ProjectException("This Coupon already purchased!");
			}else{
				st = con.createStatement();
				if(st != null){
					ResultSet rsCoup = st.executeQuery(SQLConstantsQuery.SELECT_AMOUNT_FROM_COUPON + "'" + coup.getTitle() + "'");
					if (rsCoup.next()) {
						coup.setId(rsCoup.getInt(SQLConstantsQuery.ID));
						if (rsCoup.getInt(SQLConstantsQuery.COUPON_AMOUNT) <= 0 || DateUtil.checkDates(rsCoup.getDate(SQLConstantsQuery.COUPON_END_DATE))) {
							throw new ProjectException("This coupon is not available.");
						}
					}else{
						throw new ProjectException("This Coupon is not exist!");
					}
					ResultSet rs = st.executeQuery(SQLConstantsQuery.SELECT_CUSTOMER_ID_BY_NAME + "'" + CustomerFacade.getCustomerName() + "'");
					int customerID = 0;
					if (rs.next()) {
						customerID = rs.getInt(SQLConstantsQuery.CUSTOMER_ID);
					}
					st.execute(SQLConstantsQuery.INSERT_INTO_CUSTOMER_COUPON_VALUES + "(" + customerID + "," + coup.getId() + ")");
					updateAmountSt = con.prepareStatement(SQLConstantsQuery.UPDATE_AMOUNT_IN_COUPON);
					updateAmountSt.setLong(1, coup.getId());
					updateAmountSt.setLong(2, coup.getId());
					updateAmountSt.execute();
					System.out.println("The coupon " + coup.getTitle() + " purchased");
					rsCoup.close();
				}
			}
			prpSt.close();
			rsCheck.close();
			st.close();
			ConnectionPool.getInstance().returnConnection(con);
	}
}
