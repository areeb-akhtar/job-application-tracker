# Job Application Tracker

A full-stack job application tracking system built with Spring Boot, PostgreSQL, and a lightweight web frontend.  
The application allows users to create, view, update, filter, and delete job applications through a clean REST API and a simple browser-based dashboard.

---

## Features

- Create job applications with company, position, and job URL
- View all applications in a table
- Update application status (APPLIED, INTERVIEW, OFFER, REJECTED)
- Delete applications
- Filter applications by status
- Persistent storage using PostgreSQL
- Simple frontend served directly by Spring Boot

---

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring Web (REST API)
- Spring Data JPA
- PostgreSQL

### Frontend
- HTML
- CSS
- Vanilla JavaScript (Fetch API)

### Tools
- Maven
- Postman (for API testing)

---

## Architecture Overview


The frontend communicates with the backend through REST endpoints.  
The backend handles business logic and persists data in PostgreSQL.

---

## API Endpoints

| Method | Endpoint | Description |
|------|---------|------------|
| GET | `/api/applications` | Get all applications |
| GET | `/api/applications?status=STATUS` | Filter by status |
| POST | `/api/applications` | Create a new application |
| PATCH | `/api/applications/{id}/status` | Update application status |
| DELETE | `/api/applications/{id}` | Delete an application |

---

## Running the Project Locally

### Prerequisites
- Java 17+
- PostgreSQL
- Maven

---

### Database Setup

Create a PostgreSQL database and user.

```sql
CREATE DATABASE jobtracker;
CREATE USER jobtracker WITH PASSWORD 'jobtracker';
GRANT ALL PRIVILEGES ON DATABASE jobtracker TO jobtracker;

## Testing the API

The API can be tested using Postman.

Example POST request:

POST http://localhost:8080/api/applications
```
## Start the Application

1. Make sure PostgreSQL is running.
2. Confirm your database credentials in `src/main/resources/application.properties` match your local setup.
3. Open a terminal in the project root folder, the folder that contains `pom.xml`.
4. Start the Spring Boot server using Maven Wrapper.

```bash
./mvnw spring-boot:run
````

5. Wait until you see a log line indicating the server started, typically showing Tomcat running on port 8080.

The application will be available at:

[http://localhost:8080](http://localhost:8080)

If port 8080 is already in use, stop the other process using that port or change the server port in `application.properties`.

---

## Using the Application

1. Open [http://localhost:8080](http://localhost:8080) in your browser.
2. In the “Add an application” form, enter the company name, position title, and job URL.
3. Click Add to save the application. The new record is stored in PostgreSQL through the backend API.
4. In the Applications table, use the status dropdown to choose a new status, then click Update to persist the change.
5. Click Delete to remove an application permanently.
6. Use the Filter control to view applications by status, then click Refresh to reload the latest data from the backend.

Tip: You can verify the backend is working by calling the API directly in a browser or Postman.

GET [http://localhost:8080/api/applications](http://localhost:8080/api/applications)

```

{
  "company": "Google",
  "position": "Backend Intern",
  "jobUrl": "https://example.com/job"
}
# job-application-tracker
