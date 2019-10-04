package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.h2.tools.RunScript;


public class H2DbConnection { 
	
	private static Logger log = Logger.getLogger(H2DbConnection.class);
 
	private static final String h2_driver = getValue("h2_driver");
	private static final String h2_connection_url = getValue("h2_connection_url");
	private static final String h2_user = getValue("h2_user");
	private static final String h2_password = getValue("h2_password");
	
	
	/*
	 *  get value in memory database details from properties file
	 */	
	public static String getValue(String key){
	 FileReader reader = null;
	 String value = null;
	try {
		reader = new FileReader("conf/db.properties");
		 Properties p=new Properties();
		    p.load(reader);
		    value =  p.getProperty(key);
		    log.info("Keyvalue="+value);
		    
		    		
	} catch (FileNotFoundException e) {
		log.error(e.getStackTrace());
	} catch (IOException e) {
		log.error(e.getStackTrace());
	}
	return value;       
	   
	}
		 
	/*
	 *  load in memory h2 database driver
	 */	
	
	H2DbConnection() {
		
		log.info("inside H2DBConnection method");
		DbUtils.loadDriver(h2_driver);
	}
	

	/*
	 *  get connection on the basis for database details
	 */	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(h2_connection_url, h2_user, h2_password);

	}
	
	
	/*
	 *  Intent of this method to populate test data for test case scenario
	 *  this will create and insert record into the table after executing script file 
	 *  from a location
	 */	
	public static void populateTestData() {
		log.info("Populating data for test case scenarios.");
		Connection conn = null;
		try {
			conn = H2DbConnection.getConnection();
			RunScript.execute(conn, new FileReader("scripts/V1_db_script.sql"));
		} catch (SQLException e) {
			log.error("populateTestData(): Error populating data: ", e);
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			log.error("populateTestData(): Error finding test script file ", e);
			throw new RuntimeException(e);
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}
}