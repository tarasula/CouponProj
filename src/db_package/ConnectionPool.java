package db_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ConnectionPool {

	/**
	 * Singleton pattern class, use for getting connection
	 * to DB, return connection and close all connections.
	 * @author Andrey Orlov 
	 */

	/**Instance of ConnectionPool class*/
	private static ConnectionPool instance = null;

	/**Field connection URL of DB */
	private final String connectionUrl = "jdbc:sqlserver://L1000118470:1433;databaseName=CouponProject;integratedSecurity=true;";
	
	/**Field JDBC Driver*/
	private final String driverString = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	/**Object link for synchronize*/
	private Object key = new Object();
	
	/**Set of connections*/
	private Set<Connection> connections = new HashSet<>();
	
	/**
	 * Count number of maximum connections
	 */
	private final int MAX_CONNECTIONS = 10;
	
	public static boolean ifShutDownClicked = false;
	/**
	 * Create object constructor
	 */
	private ConnectionPool() {}

	/**
	 * Synchronized get Instance method
	 * @return instance
	 */
	public static synchronized ConnectionPool getInstance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	/**
	 * Get connection method 
	 * @return connection
	 */
	public Connection getConnection() {
		Connection con = null;
		try {
			if(!ifShutDownClicked){
			synchronized (key) {
				while (true) {
					if (connections.size() < MAX_CONNECTIONS) {
						Class.forName(driverString);
						con = DriverManager.getConnection(connectionUrl);
						connections.add(con);
						return con;
					} else {
						System.out.println("No free connection please wait...");
						key.wait();
					}
				}
			}
			}else{
				System.out.println("The sysmet is in ShutDown state!");
				return null;
			}
		} catch (SQLException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * Return connection method 
	 * @param con - specific connection for return
	 */
	public void returnConnection(Connection con) {
		try {
			synchronized (key) {
				connections.remove(con);
				key.notify();
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close all connection method
	 */
	@SuppressWarnings("static-access")
	public void closeAllConnection() {
		try {
			while(connections.size() <= MAX_CONNECTIONS){
				Thread.currentThread().sleep(2000);
			}
			synchronized (key) {
				for (Connection c : connections) {
					c.close();
				}
				key.notifyAll();
				System.exit(0);
			}
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
