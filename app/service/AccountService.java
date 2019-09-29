package service;

import java.util.Optional;

import model.Account;

public interface AccountService {
	
	Optional<Account> getAccount(long accountId);

}
