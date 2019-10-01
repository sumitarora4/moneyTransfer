package service;

import java.util.Optional;

import model.Account;
import model.Transaction;

public interface AccountService {
	
	Optional<Account> getAccount(long accountId);
	
	Optional<Account> deposit(Account account);
	
	Optional<Account> withdraw(Account account);
	
	Optional<Account> transferAmount(Transaction transaction);
	
}
