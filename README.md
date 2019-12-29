Beauty Saloon Schedule System
=====================

Task #17:
-----------------------------------

Beauty Saloon Schedule System
 
### Requirements
    • JDK 1.8 or higher
    • MySQL 8.0.15 or higher
### Installation
* Clone project from GitHub (git clone https://github.com/)
* Go to /src/main/resources/DBConnectionConfig.properties and fill in connection parameters to your database (url/login/password). 
Please, be sure that mysql user filled in property file has enough rights for creating and changing databases.
* Execute script /src/main/resources/schema.sql to create database schema
* Execute script /src/main/resources/data.sql to populate database with demo data
    
### Running
* cd to root project folder and execute command *mvnw clean tomcat7:run*
* After server start, application will be available by URL [http://localhost:8080/](http://localhost:8080/) 