# creditCardCheck

This project contains a Spring Boot REST API micro-service to do the processing of Credit Card Transactions.

Compile and Build
------------------

To fetch all gradle dependencies

```
gradle clean dependencies 
```

To get clean compile and build
```
gradle clean build
```

Running
-------

gradle bootRun

Problem
-------
Consider the following credit card fraud detection algorithm
A credit card transaction is comprised of the following elements.
hashed credit card number
timestamp - of format 'year-month-dayThour:minute:second'
amount - of format 'dollars.cents'
Transactions are to be received as a comma separated string of elements.

eg. 10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00
A credit card will be identified as fraudulent if the sum of amounts for a unique hashed credit card number
over a 24 hour sliding window period exceeds the price threshold.
Write a method, which when given a sequence of transactions in chronological order, and a price threshold,
returns the hashed credit card numbers that have been identified as fraudulent

Assumption
-----------
All transactions for a day are loaded in a single load and finds the fraud transaction for a given day with a given threshold value.

Postman collection is also available in src/test/resources folder.

## Example POST /find 

URL:
```
http://localhost:8090/find
```

Header:
```
Content-Type    application/json
threshold       10.0
day             2020-02-29
```

JSON (body):
```
{
	"credit_card_transactions": [
		"10d7ce2f43e35fa57d1bbf8b1e2, 2020-02-29T13:15:54, 10.00", 
		"10d7ce2f43e35fa57d1bbf8b1e2, 2020-02-29T13:15:54, 10.00"
		]
}
```

response:
```
{
    "fraud_credit_card_numbers": [
        "10d7ce2f43e35fa57d1bbf8b1e2"
    ]
}
```

## Example Validate transactions
URL:
```
http://localhost:8090/find
```

Header:
```
Content-Type    application/json
threshold       10.0
day             2020-02-29
```

JSON (body):
```
{
	"credit_card_transactions": [
		"10d7ce2f43e35fa57d1bbf8b1e2, 2020-02-29T13:15:54, 10.00", 
		"1rtd7ce2f43e35fa57d1bbf8b1e2, 2020-01-29T13:15:54, 10.00",
		"10d7ce2f43e35fa57d1bbf8b1e2, 2020-02-2903:15:54, 10.00", 
		"10d7ce2f43e35fa57d1bbf8b1e2, 2020-01-29T13:15:54, abc" 
		]
}
```

Response- ignores the invalid entry

```
{
    "fraud_credit_card_numbers": [
        "10d7ce2f43e35fa57d1bbf8b1e2"
    ]
}
```

## Example Validate header
URL:
```
http://localhost:8090/find
```

Header:
```
Content-Type    application/json
threshold       10.0
day             asd
```

JSON (body):
```
{
	"credit_card_transactions": [
		"10d7ce2f43e35fa57d1bbf8b1e2, 2020-02-29T13:15:54, 10.00", 
		"1rtd7ce2f43e35fa57d1bbf8b1e2, 2020-01-29T13:15:54, 10.00",
		"10d7ce2f43e35fa57d1bbf8b1e2, 2020-02-2903:15:54, 10.00", 
		"10d7ce2f43e35fa57d1bbf8b1e2, 2020-01-29T13:15:54, abc" 
		]
}
```

Response- ignores the invalid entry

```
{
    "message": "Day asd is not in valid format yyyy-mm-dd"
}
```