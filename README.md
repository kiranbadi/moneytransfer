# moneytransfer
Money Transfer API
=======
Sample Money Transfer API's

Money transfer API contains below rest end points.Content Type header(application/json) needs to be passed for requests. Sample requests body is shown below request url. Remove the brackets and use request body for each sample.

1. POST -  http://localhost:8080/rest/customer/createcustomer  ---> Creates new Customer
  (Post Body - {"name":"Test","email":"kiranbadi@yahoo.com","status":"Active"} )

2  GET - http://localhost:8080/rest/customer/isCustomerNumberValid/{CustomerNumber) -- > Checks if customer exists

3. POST - http://localhost:8080/rest/accounts/openaccount    ---> Opens new account for registered customer
   ({"accountType":"SB","accountInitialBalance":121.121,"customerNumber":9638465})
   
4. GET -- http://localhost:8080/rest/accounts/isAccountNumberValid/{accountnumber} --> Checks if account number is valid and exists
  
5. POST -- http://localhost:8080/rest/customer/payee/add  ---> registers a new payee to account and customer for funds transfer
   {"accountNumber":61950,"customerNumber":9638465,"payeeName":"KIRAN    BADI","payeeAccountNumber":"A1002",
    "payeeCustomerNumber":"C1200","payeeEmail":"kiranbadi@yahoo.com","payeePhone":"6462013101","payeeNickName":"test",
    "payeeNotes":"Testing for Payee"}
    
6. POST -- http://http://localhost:8080/rest/transaction/payee/isValid --> Checks if payee is valid
   {{"accountNumber":61950,"customerNumber":9638465,"payeeAccountNumber":"A1002","payeeCustomerNumber":"C1200"})
  
7. GET -- http://http://localhost:8080/rest/transaction/balance/{customernumber}/{accountnumber} --> Gets balance information 

8. POST -- http://localhost:8080/rest/transaction/transferfunds --> Transfer funds between 2 accounts
  ({"fromAccountNumber":38330,"fromCustomerNumber":2495439,"toAccountNumber":18914,"toCustomerNumber":5437263,"transferAmount":10.000,
  "transId":"abcdada"})
  
9. POST -- http://localhost:8080/rest/transaction/action  --> Deposit or Withdrawal transactions for the account.
  ({"accountNumber":61950,"customerNumber":9638465,"amount":100.9900,"accountType":"SB","transactionId":32321,
  "transactionType":"WITHDRAWAL"} )  -- Withdrawal transaction
  ({"accountNumber":18914,"customerNumber":5437263,"amount":12000.0000,"accountType":"SB","transactionType":"DEPOSIT"})
  
  
Tech Stack used is 

- JDK 10
- TOMCAT 9
- Jersey Rest
- H2 Database
- Hikari
- Sample Integration tests with Restassured API with failsafe maven plugin
- Sample Junit tests with surefire plugin
