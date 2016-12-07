package db_package;

import java.sql.Connection;
import java.util.Set;

public class ConnectionPool {
	
	/*
	 * @author Andrey Orlov
	 * Singleton patern.
	 * For multithreading system need to change the Singleton class
	 */
	
	private static ConnectionPool instance = null;
	
	Set connectionSet ;
	
	private ConnectionPool(){		
	}
	
	public ConnectionPool getInstance(){
		if(instance == null){
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	// If not exist free connection this method is lock... (What exactly is lock?)
	public void getConnection() {
		// TODO
	}

	// Return connection and unlocking who is waiting for unlock
	public Connection returnConnection(Connection con) {
		// TODO
		return con;
	}

	// Close all connections and close the system
	public void closeAllConnection() {
		// TODO Connection close
	}
	
}
