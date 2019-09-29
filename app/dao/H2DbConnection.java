package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;

import org.apache.log4j.Logger;


public class H2DbConnection { 
 
	private static final String h2_driver = "org.h2.Driver";
	private static final String h2_connection_url = "jdbc:h2:mem:moneyTransfer;DB_CLOSE_DELAY=-1";
	private static final String h2_user = "sa";
	private static final String h2_password = "";
	private static Logger log = Logger.getLogger(H2DbConnection.class);

//	private final UserDAOImpl userDAO = new UserDAOImpl();
//	private final AccountDAOImpl accountDAO = new AccountDAOImpl();

	H2DbConnection() {
		// init: load driver
		DbUtils.loadDriver(h2_driver);
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(h2_connection_url, h2_user, h2_password);

	}
}