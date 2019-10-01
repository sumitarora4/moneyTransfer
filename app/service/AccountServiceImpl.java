package service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.inject.Inject;

import dao.AccountDao;
import model.Account;
import model.Transaction;



public class AccountServiceImpl implements AccountService{
	
	private AccountDao accountDao;
	
	@Inject
	public AccountServiceImpl(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	

	@Override
	public Optional<Account> getAccount(long accountId){
		
		return Optional.ofNullable(accountDao.findById(accountId));
	}
	
	public  Optional<Account> deposit(Account account){ 
		
		long accountId = account.getAccountId();
		BigDecimal balance = account.getBalance();
		
		try {
			accountDao.updateBalance(accountId, balance);
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		return Optional.ofNullable(accountDao.findById(accountId));	
	}
	
	public  Optional<Account> withdraw(Account account){ 
		
		long accountId = account.getAccountId();
		BigDecimal balance = account.getBalance().negate();
		
		System.out.println("withdraw balance="+balance);
		
		try {
			accountDao.updateBalance(accountId, balance);
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		return Optional.ofNullable(accountDao.findById(accountId));	
	}
	
	
	public  Optional<Account> transferAmount(Transaction transaction){ 
		
		long fromAccountId = transaction.getFromAccountId();
		long toAccountId = transaction.getToAccountId();		
		BigDecimal transferAmount = transaction.getAmount();
		
		System.out.println(" transferAmount="+transferAmount);
		
		try {
			accountDao.transferAmount(fromAccountId, toAccountId, transferAmount);
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		return Optional.ofNullable(accountDao.findById(toAccountId));	
	}
	
	
}    
