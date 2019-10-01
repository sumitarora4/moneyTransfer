CREATE TABLE account ( 
   account_id LONG NOT NULL primary key, 
   user_name VARCHAR(50) NOT NULL, 
   balance DECIMAL(20,2),     
);

insert into account values(101,'Sumit', 200.20);
insert into account values(102,'Nayan', 500.50);
insert into account values(103,'Chandan', 800.90);