# Stadium Rewards System

A full-stack web application designed to manage and track fan reward activity within a stadium environment. The system captures and validates structured fan preference data, applies reward calculation logic, and produces aggregated reward summaries through a modular backend architecture and user-friendly dashboard.

## Project Purpose

The goal of this project was to design a scalable, data-driven rewards platform capable of:

- Capturing preference data through RESTful APIs
- Applying structured reward logic
- Storing and validating data in a relational database
- Supporting structured data retrieval and reward summary visualization

## Tech Stack

- **Backend:** Java, Spring Boot
- **Database:** PostgreSQL
- **Architecture:** Layered architecture (Controller → Service → Repository)
- **API:** RESTful endpoints with JSON payloads
- **Testing:** Unit and integration testing
- **Version Control:** Git

## System Architecture

The application follows a modular layered architecture:

- **Controller Layer:** Handles HTTP requests and response formatting
- **Service Layer:** Implements business logic and validation
- **Repository Layer:** Manages database interaction
- **Database Layer:** Relational schema enforcing referential integrity and data consistency

Error handling and input validation are implemented to ensure system reliability.

## Database Design

The PostgreSQL schema was designed to ensure:

- Enforced referential integrity through foreign key constraints linking Preference and Reward records to Fan, Stand, and Seat tables, with cascading delete behavior to prevent orphaned records
- Maintained relational consistency using primary key identity columns across core entities (Fan, Preference, Reward, Seat)
- Applied database-level validation constraints including UNIQUE (email), NOT NULL fields, and CHECK constraints to ensure data accuracy and enforce valid occupation codes

<img width="995" height="631" alt="Stadium_Rewards_ER_Diagram - Sam Lato" src="https://github.com/user-attachments/assets/9282ea2b-0744-44a5-af01-3bae37bb5e98" />

## Design Decisions

- Enforced database-level constraints (UNIQUE, NOT NULL, CHECK) to maintain data integrity independently of application logic
- Used ON DELETE CASCADE on foreign keys to prevent orphaned records and simplify dependent record cleanup
- Structured the application using a layered architecture (Controller → Service → Repository) to promote separation of concerns and maintainability
- Encapsulated SQL operations within MyBatis mappers to reinforce separation of concerns and maintain a clean boundary between business logic and the persistence layer

## Lessons Learned

- Importance of modular system design for maintainability
- Structured debugging through logging and validation
- Designing APIs that are predictable and easy to integrate
- Writing testable service-layer logic

## Future Improvements

- Extend the system with a concessions reservation feature that records structured preference data and feeds inventory forecasting models to optimize stock allocation
- Add authentication and role-based access control for management users to secure stadium preference operations
- Utilize Jenkins to implement an automated CI/CD pipeline

## Running the Application

1. Clone the repository
2. Configure PostgreSQL database
3. Update application.properties with database credentials
4. Run the Spring Boot application
5. Access endpoints via Postman or browser
