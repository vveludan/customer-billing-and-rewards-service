# customer-billing-and-rewards-service

JDK Version: 11
Data Storage: H2

**Instructions to run project**:

Once downloaded and imported as mvn project, using git bash navigate into customer-rewards-service directory.
Run ls command to view files under customer-rewards-service directory.
Run mvn clean install.
After build is done, run the following command from git bash to bring up app. 
docker-compose up

Once the app is up and running. Check app status using: http://localhost:8080/actuator/health
View customer rewards api using swagger at: http://localhost:8080/swagger-ui/

Refer to the folder project-artifacts to view:
1. Class Diagrams and Sequence Diagrams
2. Customers, Transaction, Monthly reports and Quarterly Reports in json format. These files are downloaded by running API from swagger-ui

Domain Model:
Customer: contains basic customer profile.
Transaction: captures monthly transaction info with billing amount and billing date. It has Many to One relationship with Customer.
Before persisting Transaction, reward points are computed and set to Transaction.

CustomerController and TransactionController exposes crud operations on Customer and Transaction domain.
RewardSummaryController exposes the following API:
monthlyreport: takes month as request parameter. This parameter values are month names (january thru december). Case insensitive

quarterlyreport: takes quarter as request parameter. This parameter values quarter names: first/second/third/fourth. Case insensitive.
Based on quarter value, RewardSummaryService figure out the start and end of quarter dates which in turn used by TransactionRepo to retrieve Transactions.
java streams used to compute the sum of reward points from each transaction and the output is set into CustomerRewardPointsReport domain class.

Performance: quarterly report reward accumulation is embedded with parallelstream to handle large number of transactions.
Benchmark performance for quarterly reporting using sequential and parallelstream options
If millions of transaction domain classes are loaded from DB then the server configuration in terms of memory should be
benchmarked while running performance testing.

Domain in this project is trivial. As customer domain and billing/transaction domain involves more tables then this service
should be broken into two services: customer-service and billing-service.

Monthly and quarterly reports are not persisted to DB. If requires then it could be enhanced further to persist.
Local/Centralized caching should be used instead of retrieving transactions from DB while computing monthly/quarterly reports.

NOTE: Transforming domain classes to DTOs is not done.

**Create Customer End Point**: http://localhost:8080/rewards/api/v1/customers Method: POST

**Create Customer Request Example**:
{
"id": "111-11-1111",
"firstName": "Peter",
"lastName": "Pan",
"address": "200 Oak Crest Rd, Lafayette, LA 11111"
}

**Create Customer Response**:
{
"id": "111-11-1111",
"firstName": "Peter",
"lastName": "Pan",
"address": "200 Oak Crest Rd, Lafayette, LA 11111"
}

**Get All Customers Endpoint**: http://localhost:8080/rewards/api/v1/customers Method: GET

**Get All Customers Response**:

[
{
"id": "111-11-1111",
"firstName": "Peter",
"lastName": "Pan",
"address": "200 Oak Crest Rd, Lafayette, LA 11111"
},
{
"id": "222-22-2222",
"firstName": "Chris",
"lastName": "Tipton",
"address": "300 Oak Crest Rd, Lafayette, LA 11111"
}
]

**Create Transaction Endpoint**: http://localhost:8080/rewards/api/v1/transactions Method: POST

**Create Transaction Request Example**:
{
"customer": {
"id": "111-11-1111"
},
"billingDate": [2022, 2, 28],
"billingAmount": 75.0
}

**Create Transaction Response**:
{
"id": "4688a4a6-4228-42bf-8bf1-ddaeaa4b5d4f",
"customer": {
"id": "111-11-1111",
"firstName": "Peter",
"lastName": "Pan",
"address": "200 Oak Crest Rd, Lafayette, LA 11111"
},
"billingDate": "2022-02-28",
"billingAmount": 75.0,
"rewardPoints": 25
}

**Get Monthly Report Endpoint**: http://localhost:8080/rewards/api/v1/monthlyreport?month=february Method: GET

**Get Monthly Report Response**:

[
{
"customer": {
"id": "111-11-1111",
"firstName": "Peter",
"lastName": "Pan",
"address": "200 Oak Crest Rd, Lafayette, LA 11111"
},
"totalPoints": 25
},
{
"customer": {
"id": "222-22-2222",
"firstName": "Chris",
"lastName": "Tipton",
"address": "300 Oak Crest Rd, Lafayette, LA 11111"
},
"totalPoints": 40
}
]

**Get Quartery Report EndPoint**: http://localhost:8080/rewards/api/v1/quarterlyreport?quarter=first Method: GET

**Get Quarterly Report Response**:

[
{
"customer": {
"id": "222-22-2222",
"firstName": "Chris",
"lastName": "Tipton",
"address": "300 Oak Crest Rd, Lafayette, LA 11111"
},
"totalPoints": 110
},
{
"customer": {
"id": "111-11-1111",
"firstName": "Peter",
"lastName": "Pan",
"address": "200 Oak Crest Rd, Lafayette, LA 11111"
},
"totalPoints": 115
}
]

NOTE: Once the app is up and running, view all **contracts/endpoints** using: localhost:8080/swagger-ui/

Please refer to **https://github.com/vveludan/customer-rewards/tree/main/project-artifacts** for:
1. Class Diagrams and Sequence Diagrams
2. Response for various endpoints

