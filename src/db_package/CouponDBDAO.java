package db_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exceptions.DuplicateDataException;
import exceptions.NoDataException;
import exceptions.OverallException;
import facade.CompanyFacade;
import facade.CustomerFacade;
import utils.CompanyConstants;
import utils.CouponConstants;
import utils.CustomerConstants;
import utils.DateUtils;
import utils.SQLQueries;
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
	 * @throws DuplicateDataException 
	 */
	@Override
	public void createCoupon(Coupon coup) throws OverallException, SQLException, DuplicateDataException{
		ResultSet rsTitle = null;
		PreparedStatement prepStCreate = null;
		Statement stCreate;
		Connection con;
			con = getConnection();
			stCreate = con.createStatement();
			if (stCreate != null) {
				rsTitle = stCreate.executeQuery(SQLQueries.SELECT_COUPON_BY_TITLE + coup.getTitle() + "'");
				if(rsTitle.next()) {
						throw new DuplicateDataException(coup);
				}
				prepStCreate = con.prepareStatement(SQLQueries.INSERT_INTO_COUPON_VALUES, Statement.RETURN_GENERATED_KEYS);
				prepStCreate.setString(1, coup.getTitle());
				prepStCreate.setString(2, DateUtils.formatter.format(coup.getStartDate()));
				prepStCreate.setString(3, DateUtils.formatter.format(coup.getEndDate()));
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
				ResultSet rs = stCreate.executeQuery(SQLQueries.SELECT_COMPANY_ID_BY_NAME + "'" + CompanyFacade.getCompanyName() + "'");
				int companyID = 0;
				if (rs.next()) {
					companyID = rs.getInt(CompanyConstants.COMPANY_ID);
				}
				stCreate.execute(SQLQueries.INSERT_INTO_COMPANY_COUPON_VALUES + "(" + companyID + "," + coup.getId() + ")");
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
			stRemove = con.prepareStatement(SQLQueries.SELECT_COMPANY_ID_AND_COUPON_ID);
			stRemove.setString(1, CompanyFacade.getCompanyName());
			stRemove.setString(2, coup.getTitle());
			rsID = stRemove.executeQuery();
			if (stRemove != null) {
				if(rsID.next()){
					int coupID = 0;
					coupID = rsID.getInt(CouponConstants.COUPON_ID);
//					compID = rsID.getInt(SQLConstantsQuery.COMP_ID);
					st = con.createStatement();
					st.execute(SQLQueries.REMOVE_COUPON + coupID);
					st.execute(SQLQueries.DELETE_FROM_COMPANY_COUPON + coupID);
					st.execute(SQLQueries.DELETE_FROM_CUSTOMER_COUPON + coupID);
					System.out.println("The Coupon " + coup.getTitle() + " removed");
				}else{
					throw new NoDataException(coup);
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
		rs = stUpdate.executeQuery(SQLQueries.SELECT_COUPON_ID_BY_TITLE + "'" + coup.getTitle() + "'");
		if(rs.next()){
			coup.setId(rs.getLong(CouponConstants.ID));
		}else{
			throw new NoDataException(coup);
		}
		Coupon coupon = getCoupon((long) coup.getId());
		if (coupon.getId() == coup.getId()) {
				if (stUpdate != null) {
					coupon.setPrice(coup.getPrice());
					coupon.setEndDate(DateUtils.formatter.parse(DateUtils.formatter.format(coup.getEndDate())));
					stUpdate.execute(SQLQueries.UPDATE_COUPON_SET + DateUtils.formatter.format(coupon.getEndDate())
									+ "'" + ", PRICE = " + coupon.getPrice() + " WHERE ID = " + coupon.getId());
					System.out.println("The Coupon " + coupon.getTitle() + " was updated");
				}
			
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
			rs = stGet.executeQuery(SQLQueries.SELECT_COUPON_BY_ID + id);
			if (!rs.isBeforeFirst()) {
				throw new NoDataException("The Coupon with ID " + id + ", is not exist.");
			} else {
				while (rs.next()) {
					coupon.setId(rs.getLong(CouponConstants.ID));
					coupon.setTitle(rs.getString(CouponConstants.COUPON_TITLE));
					coupon.setStartDate(rs.getDate(CouponConstants.COUPON_START_DATE));
					coupon.setEndDate(rs.getDate(CouponConstants.COUPON_END_DATE));
					coupon.setAmount(rs.getInt(CouponConstants.COUPON_AMOUNT));
					typeFromDB = rs.getString(CouponConstants.COUPON_TYPE);
					for (CouponType ct : CouponType.values()) {
						if (typeFromDB.trim().equals(ct.toString())) {
							coupon.setType(ct);
						}
					}
					coupon.setMessage(rs.getString(CouponConstants.COUPON_MESSAGE));
					coupon.setPrice(rs.getDouble(CouponConstants.COUPON_PRICE));
					coupon.setImage(rs.getString(CouponConstants.COUPON_IMAGE));
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
				rs = stGetAll.executeQuery(SQLQueries.SELECT_ALL_COUPONS);
				if (!rs.next()) {
					return couponList;
				}
				rs.absolute(0);
				while (rs.next()) {
					Coupon coup = new Coupon();
					coup.setId(rs.getLong(CouponConstants.ID));
					coup.setTitle(rs.getString(CouponConstants.COUPON_TITLE));
					coup.setStartDate(rs.getDate(CouponConstants.COUPON_START_DATE));
					coup.setEndDate(rs.getDate(CouponConstants.COUPON_END_DATE));
					coup.setAmount(rs.getInt(CouponConstants.COUPON_AMOUNT));
					typeFromDB = rs.getString(CouponConstants.COUPON_TYPE);
					for (CouponType ct : CouponType.values()) {
						if (typeFromDB.trim().equals(ct)) {
							coup.setType(ct);
						}
					}
					coup.setMessage(rs.getString(CouponConstants.COUPON_MESSAGE));
					coup.setPrice(rs.getDouble(CouponConstants.COUPON_PRICE));
					coup.setImage(rs.getString(CouponConstants.COUPON_IMAGE));
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
				rs = stGetByType.executeQuery(SQLQueries.SELECT_ALL_COUPONS_BY_TYPE + coupType + "'");
				while (rs.next()) {
					Coupon coup = new Coupon();
					coup.setId(rs.getLong(CouponConstants.ID));
					coup.setTitle(rs.getString(CouponConstants.COUPON_TITLE));
					coup.setStartDate(rs.getDate(CouponConstants.COUPON_START_DATE));
					coup.setEndDate(rs.getDate(CouponConstants.COUPON_END_DATE));
					coup.setAmount(rs.getInt(CouponConstants.COUPON_AMOUNT));
					typeFromDB = rs.getString(CouponConstants.COUPON_TYPE);
					for (CouponType ct : CouponType.values()) {
						if (typeFromDB.trim().equals(ct)) {
							coup.setType(ct);
						}
					}
					coup.setMessage(rs.getString(CouponConstants.COUPON_MESSAGE));
					coup.setPrice(rs.getDouble(CouponConstants.COUPON_PRICE));
					coup.setImage(rs.getString(CouponConstants.COUPON_IMAGE));
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
			prpSt = con.prepareStatement(SQLQueries.CHECK_IF_COUPON_WAS_PURCHASED);
			prpSt.setString(1, coup.getTitle());
			rsCheck = prpSt.executeQuery();
			if(rsCheck.next()){
				throw new OverallException("This Coupon already purchased!");
			}else{
				st = con.createStatement();
				if(st != null){
					ResultSet rsCoup = st.executeQuery(SQLQueries.SELECT_AMOUNT_FROM_COUPON + "'" + coup.getTitle() + "'");
					if (rsCoup.next()) {
						coup.setId(rsCoup.getInt(CouponConstants.ID));
						if (rsCoup.getInt(CouponConstants.COUPON_AMOUNT) <= 0 || DateUtils.checkDates(rsCoup.getDate(CouponConstants.COUPON_END_DATE))) {
							throw new NoDataException("This coupon is not available.");
						}
					}else{
						throw new NoDataException(coup);
					}
					ResultSet rs = st.executeQuery(SQLQueries.SELECT_CUSTOMER_ID_BY_NAME + "'" + CustomerFacade.getCustomerName() + "'");
					int customerID = 0;
					if (rs.next()) {
						customerID = rs.getInt(CustomerConstants.CUSTOMER_ID);
					}
					st.execute(SQLQueries.INSERT_INTO_CUSTOMER_COUPON_VALUES + "(" + customerID + "," + coup.getId() + ")");
					updateAmountSt = con.prepareStatement(SQLQueries.UPDATE_AMOUNT_IN_COUPON);
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
