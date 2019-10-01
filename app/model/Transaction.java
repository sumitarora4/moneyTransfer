package model;

import java.math.BigDecimal;

public class Transaction {
	
	private BigDecimal amount;	 
	private Long fromAccountId;	 
	private Long toAccountId;
	
	public Transaction(){}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Long getFromAccountId() {
		return fromAccountId;
	}
	public void setFromAccountId(Long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	public Long getToAccountId() {
		return toAccountId;
	}
	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}
	public Transaction(BigDecimal amount, Long fromAccountId, Long toAccountId) {
		super();
		this.amount = amount;
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
	}
	
	
	
	


}
