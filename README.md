# Current-Account for Existing Customer
API to be used for opening a new account

### Spring Boot Application

---
This project provides to create account for existing customers.

### Summary
The assessment consists of an API to be used for opening a new “current account” of already existing
customers.

#### Requirements

• The API will expose an endpoint which accepts the user information (customerID,
initialCredit).

• Once the endpoint is called, a new account will be opened connected to the user whose ID is
customerID.

• Also, if initialCredit is not 0, a transaction will be sent to the new account.

• Another Endpoint will output the user information showing Name, Surname, balance, and
transactions of the accounts.

### Build and run instructions:

* First Clean-Compile-Install with Maven

> mvn spring-boot:run

Alternatively, a jar package can be obtained with: mvn clean package 
 and run with java -jar <jar name>
 Either way, afterwards you can start

> To check application health <U>http://localhost:8083/actuator/health</U> API Documentation <U>http://localhost:8083/swagger-ui.html#</U>
>
From the swagger interface you can see the 2 endpoint exposed 
under account-controller and you can test them. 
There are 5 customers already inserted in the DB (id 1-5)

## Features
>
<B>Tech Stack Used</B><br/>
>
* Java8/Java11 --> tried to optimise the code as much as possible using lambda and more features.
* Using ModelMapper implemented DTO design pattern<br/>
* Junit test cases as TDD approached is followed --> Integration test cases are also included
* Basic exception handling --> Added controller advice for Exception handler provided by Rest Controller for various exceptions

>
<B> Maven Dependencies</B><br/>
>
* spring-boot-starter-data-jpa web
* com.h2database
* Sonar and spring-boot-devtools for local testing
* Spring-boot-starter-test for Junit test cases
* open pojo for Pojo class tests
* Junit jupiter and Mockito
* lombok dependency for bean classes
* Swagger API springfox
* Actuator for health check

# Future Scope
>
* UI can be added using Angular and ReadJS for displaying user information and creating account on click of any button or image.




## Author
- Ishwari Doshi
