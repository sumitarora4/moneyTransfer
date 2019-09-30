// create script for account table
CREATE TABLE account ( 
   account_id LONG NOT NULL, 
   user_name VARCHAR(50) NOT NULL, 
   balance DECIMAL(20,2),     
);

// insert table script
insert into account values(101,'Sumit', 200.20);
insert into account values(102,'Nayan', 500.50);
insert into account values(103,'Chandan', 800.90);

// GET account details request
localhost:9000/accounts/101

// deposit POST Request
localhost:9000/accounts/deposit
Json: 
{
 "accountId": 101,
 "balance": 500.9
}

// withdraw POST Request
localhost:9000/accounts/withdraw
Json:
{
 "accountId": 102,
 "balance": 400
}

// transfer amount POST request
localhost:9000/accounts/transferAmount
Json:
{
 "fromAccountId": 101,
 "toAccountId": 102,  
 "amount": 100.00
}
