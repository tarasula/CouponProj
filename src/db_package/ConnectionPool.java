package db_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ConnectionPool {

	/*
	 * @author Andrey Orlov Singleton patern.
	 * 
	 */

	private static ConnectionPool instance = null;

	private final String connectionUrl = "jdbc:sqlserver://L1000118470:1433;databaseName=CouponProject;integratedSecurity=true;";
	private final String driverString = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private Object key = new Object();
	
	private Set<Connection> connections = new HashSet<>();
	private ConnectionPool() {}

	public static synchronized ConnectionPool getInstance() {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	
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
