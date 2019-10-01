package dao;

import java.math.BigDecimal;

import model.Account;

public interface AccountDao {
	
	Account findById(long id);
	int updateBalance(long id, BigDecimal amount) throws Exception;
	int transferAmount(long fromAccountId, long toAccountId, BigDecimal transferAmount) throws Exception;

}
