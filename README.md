# Universal Education

A java project written in Java 11, Spring Boot, Spring Security, Spring Data JPA, Spring MVC and Thymeleaf:

All the transactional data is stored in MySQL RDBMS 8.0.20.

The project is Student Record System (SRS) where there are three types of users:

* Student
* Teacher
* Admin

Authorities of each type of user are separate, which means they can only access their own resources.

The application has been deployed to AWS using AWS Elastic Beanstalk and AWS RDS [link](http://uniedu-env.eba-tp55hsj6.us-east-1.elasticbeanstalk.com/).