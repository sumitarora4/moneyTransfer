# Money Transfer

A Java RESTful API for money transfers between accounts

### Technologies
- PLAY Framework
- H2 in memory database
- Log4j
- POSTMAN Chrome Extension

### How to run
```sh
sbt run
```

Application starts an internal PLAY framework's Netty server on localhost port 9000. An c To view.

### REST API Services & JSON

- GET account details request

http://localhost:9000/accounts/101

- Deposit POST Request

http://localhost:9000/accounts/deposit

##### RequestJson:
```sh
{
 "accountId": 101,
 "balance": 500.9
}
```

- Withdraw POST Request

http://localhost:9000/accounts/withdraw

##### RequestJson:
```sh
{
 "accountId": 102,
 "balance": 400
}
```

- Transfer Amount POST request

http://localhost:9000/accounts/transferAmount

##### RequestJson:
```sh
{
 "fromAccountId": 101,
 "toAccountId": 102,  
 "amount": 100.00
}
```

### Http Status
- 200 OK: The request has succeeded
- 400 Bad Request: The request could not be understood by the server 
- 404 Not Found: The requested resource cannot be found
- 500 Internal Server Error: The server encountered an unexpected condition 
