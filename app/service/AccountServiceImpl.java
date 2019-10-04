package service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import dao.AccountDao;
import dao.AccountDaoImpl;
import model.Account;
import model.Transaction;



public class AccountServiceImpl implements AccountService{
	
	private static Logger log = Logger.getLogger(AccountServiceImpl.class);
	private AccountDao accountDao;
	
	@Inject
	public AccountServiceImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	

	@Override
	public Optional<Account> getAccount(long accountId){
		
		log.debug("inside getAccount method");
		return Optional.ofNullable(accountDao.findById(accountId));
	}
	
	public  Optional<Account> deposit(Account account){ 
		
		log.debug("inside deposit method");
		long accountId = account.getAccountId();
		BigDecimal balance = account.getBalance();
		
		try {
			accountDao.updateBalance(accountId, balance);
		} catch (Exception e) {
			 
			log.debug(e.getStackTrace());
		}
		
		return Optional.ofNullable(accountDao.findById(accountId));	
	}
	
	public  Optional<Account> withdraw(Account account){ 
		
		log.debug("inside withdraw method");
		long accountId = account.getAccountId();
		BigDecimal balance = account.getBalance().negate();
		
		log.debug("withdraw balance="+balance);
		
		try {
			accountDao.updateBalance(accountId, balance);
		} catch (Exception e) {
			log.debug(e.getStackTrace());
		}
		
		return Optional.ofNullable(accountDao.findById(accountId));	
	}
	
	
	public  Optional<Account> transferAmount(Transaction transaction){ 
		
		log.debug("inside transferAmount method");
		long fromAccountId = transaction.getFromAccountId();
		long toAccountId = transaction.getToAccountId();		
		BigDecimal transferAmount = transaction.getAmount();
		
		try {
			int resultCount = accountDao.transferAmount(fromAccountId, toAccountId, transferAmount);
			log.debug("query executed for "+resultCount+" rows.");
		} catch (Exception e) {
			 
			log.debug(e.getStackTrace());
		}
		
		return Optional.ofNullable(accountDao.findById(toAccountId));	
	}
	
	
}    
