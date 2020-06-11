##Customer Service Repository

###Introduction
Customer Service repository provides REST APIs to perform basic CRUD operations on Customer data.

####Security
 Security is implemented using JWT. /authenticate API is used to login and generate JWT. JWT is required for every Customer's REST API
 
####Caching
Redis is used to cache DB queries output.

####Run Test cases

`mvn test`
####Run Application
`mvn spring-boot:run`


