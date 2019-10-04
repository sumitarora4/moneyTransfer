# Money Transfer between Accounts

A Java RESTful API based application to deposit, withdraw and transfer money between accounts.

### Technologies
- Java
- PLAY Framework
- H2 in memory database
- Log4j
- POSTMAN REST Client Chrome Extension

### How to run
At root directory location of project where build.sbt file is avaialble, execute below command:

```sh
sbt
```
Now once sbt command prompt appears. Execute below command to run h2 db on the browser:

```sh
h2-browser
```
Execute below command to run PLAY application server. This will start Application on internal PLAY framework's Netty server at localhost default port 9000.

```sh
run
```

On h2 browser make connection from details given in conf/db.properties file. Here you will get "Account" table in h2 database which is automatically generated on Application startup.

You can find SQL scripts given at scripts/V1_db_script.sql to get more details.


### REST API Services & JSON

Hit below APIs on POSTMAN REST Client given at chrome browser:

- GET account details request

http://localhost:9000/accounts/101

##### ResponseJson:
````sh
{
    "accountId": 101,
    "userName": "Sumit",
    "balance": 1503.7
}
```
- Deposit POST Request

http://localhost:9000/accounts/deposit

Content-Type: application/json

##### RequestJson:
```sh
{
 "accountId": 101,
 "balance": 500.1
}
```

##### ResponseJson:
````sh
{
    "isSuccessful": true,
    "body": {
        "accountId": 101,
        "userName": "Sumit",
        "balance": 2003.8
    }
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
##### ResponseJson:
````sh
{
    "isSuccessful": true,
    "body": {
        "accountId": 102,
        "userName": "Nayan",
        "balance": 97.8
    }
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
##### ResponseJson:
````sh
{
    "isSuccessful": true,
    "body": {
        "accountId": 102,
        "userName": "Nayan",
        "balance": 197.8
    }
}
```

### Http Status
- 200 OK: The request has succeeded
- 400 Bad Request: The request could not be understood by the server 
- 404 Not Found: The requested resource cannot be found
- 500 Internal Server Error: The server encountered an unexpected condition 
