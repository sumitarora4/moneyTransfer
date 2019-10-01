# Money Transfer

A Java RESTful API for money transfers between accounts.

### Technologies
- PLAY Framework
- H2 in memory database
- Log4j
- POSTMAN Chrome Extension

### How to run
At root directory location of project where build.sbt file is avaialble, execute below command:

```sh
sbt
```
Now once sbt command prompt appears. Execute below command to run h2 db on the browser:

```sh
h2-browser
```
On h2 browser make connection from details given in conf/db.properties file. And hit SQL scripts given at scripts/V1_db_script.sql to generate table and data.

Once table is created and data is restored, execute below command to run PLAY application server:

```sh
run
```

Application starts an internal PLAY framework's Netty server on localhost default port 9000.

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
