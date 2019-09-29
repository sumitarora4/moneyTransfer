package service;

import java.util.Optional;

import javax.inject.Inject;

import model.Account;

import dao.AccountDao;



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
}    
