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

## Getting Started

## API Endpoints

    POST /employee/saveEmployeeDetails
        Description: Saves employee details.
        Request Body: JSON containing employee information.

    GET /employee/getEligibleEmployeesForBonus
        Description: Retrieves eligible employees for bonuses based on the provided date.
        Parameters:
            date (format: MMM-dd-yyyy)
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

