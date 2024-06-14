# User balance management

## Description

Detailed description of the project, including its background, goals, and any specific context relevant to understanding
the project's purpose.

## Features

- Endpoint for setting users' balance based on a map of user IDs and balances.

## Improvements Suggestions

### 1. User Management Endpoint

#### Description

Implement CRUD operations for managing users.

#### Implementation Steps

- **Create Endpoint**: Implement endpoints (`POST /users`, `DELETE /users/{userId}`, `PUT /users/{userId}`, etc.) for
  user management.
- **Service Layer**: Implement service layer methods for creating, updating, deleting users.
- **Repository Layer**: Implement repository layer methods for interacting with the database.

### 2. Increase efficiency

#### Description

Implement parallel balance update execution.
It will require threads synchronization and may be suitable when big amount of user balance should be updated

#### Implementation Steps

- **Break down input data into chunks**
- **Process each chunk of data in separate thread**
- **Wait for all threads to end work**

## Steps to Run

1. Clone the repository:
   ```git clone https://github.com/your-username/your-repo.git```
2. Navigate to the project directory: ```cd your-project-directory```
2. Configure [application.yml](src%2Fmain%2Fresources%2Fapplication.yml)
3. Build the project using Maven: ```mvn clean install```
4. Run the application: ```java -jar target/your-project.jar```

## Access

- Application will run on: ```http://localhost:8080``` by default
- Sample request: ```POST /api/v1/users/balance``` Body:

```json
   {
  "1": 100,
  "2": 200,
  "3": 300
}
```

