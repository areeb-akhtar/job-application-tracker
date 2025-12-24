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

{
  "company": "Google",
  "position": "Backend Intern",
  "jobUrl": "https://example.com/job"
}
# job-application-tracker
