package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import model.Account;


public class AccountDaoImpl implements AccountDao{
	
	private static Logger log = Logger.getLogger(AccountDaoImpl.class);
	private final static String SQL_GET_ACC_BY_ID = "SELECT * FROM Account WHERE account_id = ? ";

	@Override
	public Account findById(long accountId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Account acc = null;
		try {
			conn = H2DbConnection.getConnection();
			stmt = conn.prepareStatement(SQL_GET_ACC_BY_ID);
			stmt.setLong(1, accountId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				acc = new Account(rs.getLong("account_id"), rs.getString("user_name"), rs.getBigDecimal("balance"));
				if (log.isDebugEnabled())
					log.debug("Retrieve Account By Id: " + acc);
			}
			return acc;
		} catch (SQLException e) {
			log.debug(e.getStackTrace());
		} finally {
			DbUtils.closeQuietly(conn, stmt, rs);
		}

		return null;
	}

}
