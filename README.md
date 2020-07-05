# Universal Education

A java project written in Java 11, Spring Boot, Spring Security, Spring Data JPA, Spring MVC and Thymeleaf:

All the transactional data is stored in MySQL RDBMS 8.0.20.

The project is Student Record System (SRS) where there are three types of users:

* Student
* Teacher
* Admin

Authorities of each type of user are separate, which means they can only access their own resources.

* Students can see their profile, financial status, marks and download materials, etc.
* Teachers can upload new materials, put marks, etc.
* Admins can create courses, users, enroll students to courses, record finnacial transactions, etc.

The application has been deployed to AWS using AWS Elastic Beanstalk and AWS RDS [link](http://uniedu-env.eba-tp55hsj6.us-east-1.elasticbeanstalk.com/).