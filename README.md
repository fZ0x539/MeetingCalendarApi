# Calendar Meetings API

A Spring Boot backend API for managing calendar meetings and participants.

## Features
- Create, read, update, and delete meetings
- Add participants to meetings
- Unique constraint prevents duplicate email registrations for the same meeting
- Meeting levels (enum) categorization
- RESTful endpoints for all operations

## Technologies
- Java 24
- Spring Boot 3.x
- Spring Data JPA
- Hibernate
- MySQL
- Lombok
- Maven

## Entity Model

### Meeting
- id (auto-generated)
- title (required)
- dateTime (required)
- meetingLevel (enum: HIGH, MEDIUM, LOW)
- description (optional, max 500 chars)
- participants (list of Participant entities)

### Participant
- id (auto-generated)
- email (required)
- meeting (many-to-one relationship)
- Unique constraint on (email + meeting_id) combination

## Getting Started

### Prerequisites
- Java 24 JDK installed
- Maven installed
- Git installed (optional)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/fZ0x539/MeetingCalendarApi.git
   cd MeetingCalendarApi
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will start on port 8080 by default.

## API Endpoints

### Meetings
- GET /api/meetings - Get all meetings
- GET /api/meetings/{id} - Get meeting by ID
- POST /api/meetings - Create a new meeting
- PUT /api/meetings/{id} - Update a meeting
- DELETE /api/meetings/{id} - Delete a meeting


## Sample Request

### Create Meeting
POST /api/meetings
```JSON
{
    "title": "Test 1",
    "date": "2025-06-30",
    "time": "23:39",
    "meetingLevel": "Team",
    "participants": [
        "participant1@gmail.com",
        "participant2@gmail.com",
    ],
    "description": "Test test"
}
```

## Database
The application uses a MySQL database by default. Which can be accessed at:

Connection details:
- JDBC URL: jdbc:mysql://localhost:3306/meetings_api
- Username: root
- Password: 1234

## Configuration
To modify default settings, edit the application.yaml file:
- Server port: server.port=8080
- Database settings **(username & password!)**

## Testing
Run the tests with:
```bash
mvn test
```


## License
MIT License (see LICENSE file)
