package model;

import java.math.BigDecimal;

public class Account {

	private long accountId;
	private String userName;
	private BigDecimal balance;
	
	public Account(){}
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Account(long accountId, String userName, BigDecimal balance) {
		super();
		this.accountId = accountId;
		this.userName = userName;
		this.balance = balance;
	}
	
	
	
	
}
