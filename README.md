# Employee Management System

## Overview

The Employee Management System is a Java-based application developed using Spring Boot 3.x. This application provides functionalities to manage employee data, including the ability to save employee details, retrieve eligible employees for bonuses, and more.

## Technologies Used

- **Java Version:** 17 (minimum requirements)
- **Framework:** Spring Boot 3.x
- **Build Tool:** Gradle

## Features

- Add new employee details.
- Retrieve eligible employees for bonuses based on specified criteria.
- Manage employee information efficiently.

## Prerequisites

To run this application, make sure you have the following installed:

- Java JDK 17
- Gradle
- IDE (such as IntelliJ IDEA or Eclipse)
- mysql database server

## Getting Started
## if Java 17 and Gradle is not installed on your system 
use these commands for linux (for other OS go to official websites)
	java	- sudo apt-get install openjdk-17-jdk
	gradle  - sudo apt-get install gradle
  
## API Endpoints

    POST /employee/saveEmployeeDetails
        Description: Saves employee details.
        Request Body: JSON containing employee information.

    GET /employee/getEligibleEmployeesForBonus
        Description: Retrieves eligible employees for bonuses based on the provided date.
        Parameters:
            date (format: MMM-dd-yyyy)

### datebase queries
CREATE DATABASE `employee_db`;
### Clone the Repository

```bash
git clone https://github.com/vivek-goyal12/employeeManagement.git
cd employeeManagement

To build the project, run the following command:
./gradlew build

You can run the application using:
./gradlew bootRun


To run the tests, execute the following command:
./gradlew test
# employeeManagement
```
Feel free to modify it according to your project's specific details and requirements!

