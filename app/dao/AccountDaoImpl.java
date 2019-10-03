package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import model.Account;


public class AccountDaoImpl implements AccountDao{
	
	private static Logger log = Logger.getLogger(AccountDaoImpl.class);
	private final static String q1 = "SELECT * FROM Account WHERE account_id = ? ";	
	private final static String q2 = "SELECT * FROM Account WHERE account_id = ? FOR UPDATE";
	private final static String q3 = "UPDATE Account SET Balance = ? WHERE account_id = ? ";
	
	
 

	@Override
	public Account findById(long accountId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Account acc = null;
		try {
			conn = H2DbConnection.getConnection();
//			H2DbConnection.populateTestData();
			stmt = conn.prepareStatement(q1);
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

		return acc;
	}

	@Override
	public int updateBalance(long accountId, BigDecimal amount) throws Exception {
		Connection conn = null;
		PreparedStatement lockStmt = null;
		PreparedStatement updateStmt = null;
		ResultSet rs = null;
		Account targetAccount = null;
		int updateCount = -1;
		
		try {
			conn = H2DbConnection.getConnection();
			conn.setAutoCommit(false);
			
			// lock account for writing:
			lockStmt = conn.prepareStatement(q2);
			lockStmt.setLong(1, accountId);
			rs = lockStmt.executeQuery();
			if (rs.next()) {
				targetAccount = new Account(rs.getLong("account_id"), rs.getString("user_name"), rs.getBigDecimal("balance"));
				if (log.isDebugEnabled())
					log.debug("update Account Balance from Account: " + targetAccount);
			}

			if (targetAccount == null) {
				throw new Exception("update Account Balance(): fail to lock account : " + accountId);
			}
			
			// update account upon success locking
			BigDecimal balance = targetAccount.getBalance().add(amount);
			
			if (balance.compareTo(new BigDecimal(0)) < 0) {
				throw new Exception("Not sufficient Fund for account: " + accountId);
			}

			updateStmt = conn.prepareStatement(q3);
			updateStmt.setBigDecimal(1, balance);
			updateStmt.setLong(2, accountId);
			updateCount = updateStmt.executeUpdate();
			conn.commit();
			if (log.isDebugEnabled())
				log.debug("New Balance after Update: " + targetAccount);
			return updateCount;
		} catch (SQLException se) {
			
			// rollback transaction if exception occurs
			log.error("updateAccountBalance(): User Transaction Failed, rollback initiated for: " + accountId, se);
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException re) {
				throw new Exception("Fail to rollback transaction", re);
			}
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(lockStmt);
			DbUtils.closeQuietly(updateStmt);
		}
		return updateCount;
	}

	@Override
	public int transferAmount(long fromAccountId, long toAccountId, BigDecimal transferAmount) throws Exception {
		int result = -1;
		Connection conn = null;
		PreparedStatement lockStmt = null;
		PreparedStatement updateStmt = null;
		ResultSet rs = null;
		Account fromAccount = null;
		Account toAccount = null;

		try {
			conn = H2DbConnection.getConnection();
			conn.setAutoCommit(false);
			
			// lock the credit and debit account for writing:
			lockStmt = conn.prepareStatement(q2);
			lockStmt.setLong(1, fromAccountId);
			rs = lockStmt.executeQuery();
			if (rs.next()) {
				fromAccount = new Account(rs.getLong("account_id"), rs.getString("user_name"), rs.getBigDecimal("balance"));
				if (log.isDebugEnabled())
					log.debug("transferAccountBalance from Account: " + fromAccount);
			}
			lockStmt = conn.prepareStatement(q2);
			lockStmt.setLong(1, toAccountId);
			rs = lockStmt.executeQuery();
			if (rs.next()) {
				toAccount = new Account(rs.getLong("account_id"), rs.getString("user_name"), rs.getBigDecimal("balance"));
				if (log.isDebugEnabled())
					log.debug("transferAccountBalance to Account: " + toAccount);
			}

			// check locking status
			if (fromAccount == null || toAccount == null) {
				throw new Exception("Fail to lock both accounts for write");
			}
			 	 

			// check enough fund in source account
			BigDecimal fromAccountLeftOver = fromAccount.getBalance().subtract(transferAmount);
			if (fromAccountLeftOver.compareTo(new BigDecimal(0)) < 0) {
				throw new Exception("Not enough Fund from source Account ");
			}
			
			 
			updateStmt = conn.prepareStatement(q3);
			updateStmt.setBigDecimal(1, fromAccountLeftOver);
			updateStmt.setLong(2, fromAccountId);
			updateStmt.addBatch();
			updateStmt.setBigDecimal(1, toAccount.getBalance().add(transferAmount));
			updateStmt.setLong(2,toAccountId);
			updateStmt.addBatch();
			int[] rowsUpdated = updateStmt.executeBatch();
			result = rowsUpdated[0] + rowsUpdated[1];
			
			if (log.isDebugEnabled()) {
				log.debug("Number of rows updated for the transfer : " + result);
			}
			 
			conn.commit();
		} catch (SQLException se) {
			// rollback transaction if exception occurs
			log.error("transferAccountBalance(): User Transaction Failed, rollback initiated for From Account Id: " + fromAccountId, se);
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException re) {
				throw new Exception("Fail to rollback transaction", re);
			}
		} finally {
			DbUtils.closeQuietly(conn);
			DbUtils.closeQuietly(rs);
			DbUtils.closeQuietly(lockStmt);
			DbUtils.closeQuietly(updateStmt);
		}
		return result;
	}

	
	
}
