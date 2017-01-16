package db_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.omg.CORBA.INITIALIZE;

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
			synchronized (key) {
				while (true) {
					if (connections.size() <= 10) {
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
	public void closeAllConnection() {
		try {
			synchronized (key) {
				for (Connection c : connections) {
					c.close();
				}
				key.notifyAll();
				System.exit(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
