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


public class H2DbConnection { 
 
	public static String getValue(String key){
	 FileReader reader = null;
	 String value = null;
	try {
		reader = new FileReader("conf/db.properties");
		 Properties p=new Properties();
		    p.load(reader);
		    value =  p.getProperty(key);
//		    System.out.println(p.getProperty(key));  
		    
		    		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return value;       
	   
	}
	
	/*private static final String h2_driver = "org.h2.Driver";
	private static final String h2_connection_url = "jdbc:h2:mem:moneyTransfer;DB_CLOSE_DELAY=-1";
	private static final String h2_user = "sa";
	private static final String h2_password = "";*/
	
	private static final String h2_driver = getValue("h2_driver");
	private static final String h2_connection_url = getValue("h2_connection_url");
	private static final String h2_user = getValue("h2_user");
	private static final String h2_password = getValue("h2_password");
	
	private static Logger log = Logger.getLogger(H2DbConnection.class);
	

	H2DbConnection() {
		// load driver
		DbUtils.loadDriver(h2_driver);
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(h2_connection_url, h2_user, h2_password);

	}
}