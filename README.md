# Employee Management App

A comprehensive full-stack solution for managing employees and their work orders. Tailored with three distinct access roles - Admin, Operator, and Engineer - it streamlines various functionalities essential for modern businesses.
Main purpose of the application is ensuring that tasks are efficiently managed and productivity is tracked in a user-friendly manner.

# Demonstration

[![Video presentation](http://img.youtube.com/vi/rKXFXcX8Xfw/0.jpg)](https://www.youtube.com/watch?v=rKXFXcX8Xfw)

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation steps](#installation-steps)
- [Usage](#usage)
  - [API Documentation](#api-documentation)
  - [React App](#react-app)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Features

### Timetable
- **Logging Times**: Employees can log their check-in and check-out times.
- **Daily Overview**: Visual representation of daily worked hours in a tabular format.

### Work Orders
- **Creation & Assignment**: Operators have the authority to create work orders and assign them to employees.
- **Status Update**: Employees have the capability to mark the work orders as completed once the task is finished.
- **Tracking**: Monitor the status, start date, and completion of each work order.

### Payroll
- **Profit Calculation**: A robust system to compute the profit generated by each employee through the completion of work orders.
- **Monthly Data**: Monthly aggregation of data, showing both the profit made and the total hours worked.

### Dashboard
- **Profit Graph**: A graphical representation of total profit.
- **Activity Log**: A central hub showing the latest actions.
- **Top Performers**: A leaderboard highlighting the top-performing employees.

### Security
- **JWT Protection**: Both the REST API and frontend are fortified using JWT tokens.
- **Role-based Access**: Distinct access levels are implemented based on user roles, ensuring fine-grained control over application functionalities.

## Prerequisites

To successfully run and develop the Employee Management App, ensure the following are installed:

### Backend Prerequisites:

1. **Java**: Version 17 or higher.
2. **Maven**: Ensure you have a recent version of Maven installed.
3. **MySQL**: Ensure you have MySQL set up and accessible, as the project uses `mysql-connector-j`.

### Frontend Prerequisites:

1. **Node.js**: Ensure you have a recent version of Node.js installed.
2. **npm**: Ensure npm is installed.

### Database and Environment Configuration:

1. **Database**: Set up your MySQL instance and create a database for the project.
2. **Environment File**: 
   - Create an `.env` file in the root directory of the backend project.
   - Populate it with your database details:
     ```
     DB_URL=your_database_url
     DB_USERNAME=your_database_username
     DB_PASSWORD=your_database_password
     PROFILE=default
     ```
   - For the first run and dummy data initialization, set `PROFILE=data-init`.

### Installation Steps:

1. **Backend**:
   - Navigate to the backend directory.
   - Run `mvn clean install` to install the required dependencies from the `pom.xml`.
   - Start the Spring Boot application.

2. **Frontend**:
   - Navigate to the frontend directory.
   - Run `npm install` to fetch and install the required packages from `package.json`.
   - Use `npm start` to start the frontend development server.


## Usage

## API documentation

<details>
  <summary>Click to expand/collapse Authentication API</summary>

## Authentication API

**Base URL**: `/api/v1/auth`

- **Endpoint**: `/login`
  - **HTTP Method**: POST
  - **Description**: Authenticate a user and retrieve JWT token.
  - **Request Body**: 
    ```json
    {
      "emailId": "user@email.com",
      "password": "password123"
    }
    ```
  - **Response**: AuthResponseDTO containing the JWT token.

</details>
<details>
  <summary>Click to expand/collapse Customers API</summary>
  
## Customers API

**Base URL**: `/api/v1/customers`

### Endpoints:

- **Endpoint**: `/`
  - **HTTP Method**: GET
  - **Description**: Retrieve all customers.
  
- **Endpoint**: `/`
  - **HTTP Method**: POST
  - **Description**: Save a new customer.
  - **Request Body**: 
    ```json
    {
      "emailId": "...",
      "firstName": "...",
      "lastName": "...",
      "companyName": "..."
    }
    ```
  
- **Endpoint**: `/{id}`
  - **HTTP Method**: PUT
  - **Description**: Update an existing customer.
  - **Path Variable**: `id` (Customer ID)
  - **Request Body**: 
    ```json
    {
      "customerId": 1,
      "emailId": "...",
      "firstName": "...",
      "lastName": "...",
      "companyName": "..."
    }
    ```
</details>
<details>
  <summary>Click to expand/collapse Payroll API</summary>
  
## Payroll API

**Base URL**: `/api/v1/payroll`

- **Endpoint**: `/`
  - **HTTP Method**: GET
  - **Description**: Retrieve filtered payrolls based on userId and/or payrollMonth.
  - **Query Parameters**: `userId` (optional), `payrollMonth` (optional, format: `YYYY-MM-DD`)

</details>
<details>
  <summary>Click to expand/collapse Payroll Statistics API</summary>
  
## Payroll Statistics API

**Base URL**: `/api/v1/statistics`

### Endpoints:

- **Endpoint**: `/lastMonthProfit`
  - **HTTP Method**: GET
  - **Description**: Retrieve the total profit for the last month.

- **Endpoint**: `/totalProfit`
  - **HTTP Method**: GET
  - **Description**: Retrieve the total profit.

- **Endpoint**: `/top3EmployeesByHours/{year}/{month}`
  - **HTTP Method**: GET
  - **Description**: Retrieve the top 3 employees by hours worked for a specific month.
  - **Path Variables**: `year` (Year, e.g., 2023), `month` (Month, e.g., 12 for December)

- **Endpoint**: `/top3EmployeesByMoney/{year}/{month}`
  - **HTTP Method**: GET
  - **Description**: Retrieve the top 3 employees by money generated for a specific month.
  - **Path Variables**: `year` (Year, e.g., 2023), `month` (Month, e.g., 12 for December)

- **Endpoint**: `/monthly-earnings/last-two-years`
  - **HTTP Method**: GET
  - **Description**: Retrieve monthly earnings for the last two years.

- **Endpoint**: `/yearly/{year}`
  - **HTTP Method**: GET
  - **Description**: Retrieve yearly statistics for a specific year.
  - **Path Variable**: `year` (Year, e.g., 2023)

- **Endpoint**: `/monthly/{year}/{month}`
  - **HTTP Method**: GET
  - **Description**: Retrieve monthly statistics for a specific month of a specific year.
  - **Path Variables**: `year` (Year, e.g., 2023), `month` (Month, e.g., 12 for December)
</details>
<details>
  <summary>Click to expand/collapse Timetables API</summary>
  
## Timetables API
**Base URL**: `/api/v1/timetables`

### Endpoints:

- **Endpoint**: `""`
  - **HTTP Method**: GET
  - **Description**: Retrieve timetables based on filters provided.
  - **Query Parameters**: 
    - `userId` (optional): User's ID
    - `after` (optional): Retrieve timetables after this date
    - `before` (optional): Retrieve timetables before this date

- **Endpoint**: `"{userId}/checkin"`
  - **HTTP Method**: PUT
  - **Description**: Check in for a specific user.
  - **Path Variable**: `userId` (User's ID)

- **Endpoint**: `"{userId}/checkout"`
  - **HTTP Method**: PUT
  - **Description**: Check out for a specific user.
  - **Path Variable**: `userId` (User's ID)

- **Endpoint**: `"{userId}/checkin"`
  - **HTTP Method**: GET
  - **Description**: Retrieve check-in time for a specific user.
  - **Path Variable**: `userId` (User's ID)

- **Endpoint**: `"{userId}/checkout"`
  - **HTTP Method**: GET
  - **Description**: Retrieve check-out time for a specific user.
  - **Path Variable**: `userId` (User's ID)

- **Endpoint**: `"{userId}/{date}/editTimes"`
  - **HTTP Method**: PUT
  - **Description**: Edit check-in and check-out times for a specific user on a specific date.
  - **Path Variables**: 
    - `userId` (User's ID)
    - `date` (Date, e.g., 2023-09-21)
  - **Query Parameters**: 
    - `checkIn` (Check-in time, e.g., 09:00)
    - `checkOut` (Check-out time, e.g., 17:00)
</details>   
<details>
  <summary>Click to expand/collapse Users API</summary>
  
## Users API

**Base URL**: `/api/v1/users`

### Endpoints:

- **Endpoint**: `/amount`
  - **HTTP Method**: GET
  - **Description**: Retrieve the count of users excluding those with the role `Admin`.

- **Endpoint**: `""`
  - **HTTP Method**: GET
  - **Description**: Fetch a list of users.

- **Endpoint**: `/{userId}`
  - **HTTP Method**: GET
  - **Description**: Fetch a specific user by their ID.
  - **Path Variable**: `userId` (User's ID)

- **Endpoint**: `""`
  - **HTTP Method**: POST
  - **Description**: Save a new user.
  - **Request Body**: User details in JSON format.

- **Endpoint**: `/{userId}/generateTimetable`
  - **HTTP Method**: POST
  - **Description**: Generate a timetable for a specific user for the past year up to the next year.
  - **Path Variable**: `userId` (User's ID)

- **Endpoint**: `/{userId}`
  - **HTTP Method**: PUT
  - **Description**: Update user details.
  - **Path Variable**: `userId` (User's ID)
  - **Request Body**: User details in JSON format.

- **Endpoint**: `/by-email`
  - **HTTP Method**: GET
  - **Description**: Fetch a user by their email address.
  - **Query Parameter**: `emailId` (User's email address)
    
</details>
<details>
  <summary>Click to expand/collapse Work orders API</summary>
  
## Work orders API

**Base URL**: `/api/v1/workorders`

### Endpoints:

- **Endpoint**: `/amount`
  - **HTTP Method**: GET
  - **Description**: Retrieve the count of work orders. Optionally filter by `OrderStatus`.
  - **Query Parameter**: `status` (optional, `OrderStatus` enum value)

- **Endpoint**: `""`
  - **HTTP Method**: GET
  - **Description**: Fetch a list of filtered work orders.
  - **Query Parameters**:
    - `userId` (optional, User ID)
    - `after` (optional, Date filter, ISO format)
    - `before` (optional, Date filter, ISO format)
    - `status` (optional, `OrderStatus` enum value)

- **Endpoint**: `/active`
  - **HTTP Method**: GET
  - **Description**: Fetch a list of active work orders.
  - **Query Parameter**: `userId` (optional, User ID)

- **Endpoint**: `/{orderId}`
  - **HTTP Method**: GET
  - **Description**: Fetch a specific work order by its order ID.
  - **Path Variable**: `orderId` (Order's ID)

- **Endpoint**: `/batch`
  - **HTTP Method**: GET
  - **Description**: Fetch a batch of work orders by their order IDs.
  - **Query Parameter**: `orderIds` (List of order IDs)

- **Endpoint**: `/assign`
  - **HTTP Method**: PUT
  - **Description**: Assign a user to a specific work order.
  - **Query Parameters**:
    - `userId` (required, User ID)
    - `orderId` (required, Order ID)

- **Endpoint**: `/{orderId}`
  - **HTTP Method**: DELETE
  - **Description**: Delete a work order by its order ID.
  - **Path Variable**: `orderId` (Order's ID)

- **Endpoint**: `""`
  - **HTTP Method**: POST
  - **Description**: Create a new work order.
  - **Request Body**: Work order details in JSON format (`WorkOrderDTO`).

- **Endpoint**: `/{orderId}`
  - **HTTP Method**: PUT
  - **Description**: Update a specific work order by its order ID.
  - **Path Variable**: `orderId` (Order's ID)
  - **Request Body**: Work order details in JSON format (`WorkOrderDTO`).

- **Endpoint**: `/{orderId}/complete`
  - **HTTP Method**: PUT
  - **Description**: Complete a work order by its order ID.
  - **Path Variable**: `orderId` (Order's ID)

- **Endpoint**: `/recent`
  - **HTTP Method**: GET
  - **Description**: Fetch a list of recent work orders.

- **Endpoint**: `/profit-per-orderType`
  - **HTTP Method**: GET
  - **Description**: Get the profit per order type.
</details>
<details>
  <summary>Click to expand/collapse Work order status API</summary>
  
## WorkOrderStatus API

**Base URL**: `/api/v1/statustypes`

### Endpoints:

- **Endpoint**: `""`
  - **HTTP Method**: GET
  - **Description**: Retrieve all the available `OrderStatus` values.

---
</details>
<details>
  <summary>Click to expand/collapse Work order type API</summary>
  
## WorkOrderType API

**Base URL**: `/api/v1/ordertypes`

### Endpoints:

- **Endpoint**: `""`
  - **HTTP Method**: GET
  - **Description**: Fetch a list of all work order types.

- **Endpoint**: `""`
  - **HTTP Method**: POST
  - **Description**: Create a new work order type.
  - **Request Body**: Work order type details in JSON format (`OrderType`).

- **Endpoint**: `/{id}`
  - **HTTP Method**: PUT
  - **Description**: Edit an existing work order type by its ID.
  - **Path Variable**: `id` (OrderType's ID)
  - **Request Body**: Updated work order type details in JSON format (`OrderType`).
---
</details>

## React App

<details>
  <summary>Click to expand/collapse Logging in</summary>

### Logging In 

### Step 1: Navigate to the Login Page.
- Start by accessing the main page of our application.

### Step 2: Choose the User Role.
Select one of the user profiles based on your intended role:

#### Admin User:
- **Email**: `admin@yourcompany.com`
- **Password**: `adminPass`

#### Operator User:
- **Email**: `operator@yourcompany.com`
- **Password**: `operatorPass`

#### Engineer User:
- **Email**: `engineer@yourcompany.com`
- **Password**: `engineerPass`

### Step 3: Enter the Credentials.
Input the email and password for the user role you've chosen.

### Step 4: Click the "Login" Button.
You should now be redirected to the dashboard or main view of your chosen role.

> **Note**: These credentials are for demonstration and testing.
> 
</details>

### Video demonstration:

[![Video presentation](http://img.youtube.com/vi/rKXFXcX8Xfw/0.jpg)](https://www.youtube.com/watch?v=rKXFXcX8Xfw)

## Acknowledgments

The dashboard used in our application is adapted from the **Modernize Free React MUI Dashboard**. 

- License: **MIT**
- Attribution is kindly provided to the original creators.

[View the original template here](https://adminmart.com/product/modernize-free-react-mui-dashboard/)

This project uses several open-source libraries and tools:

- [**MUI**](https://mui.com/): A popular React UI framework.
- [**react-bootstrap**](https://react-bootstrap.github.io/): Bootstrap components built with React.
- [**react-table**](https://react-table.tanstack.com/): Hooks for building lightweight, fast, and extendable data-grid for React.
- [**Bootstrap**](https://getbootstrap.com/): The world's most popular front-end toolkit.

I am grateful to the developers and maintainers of these tools for their invaluable contributions to the community.

## License

### The MIT License (MIT)

Copyright (c) 2023 Jakub Rurak

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
