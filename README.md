# Stadium Rewards System

A full-stack Spring Boot web application that models and executes a stadium reward allocation system. The platform captures structured fan preference data, applies deterministic and weighted reward algorithms, and generates on-demand reward summaries through a layered backend architecture and interactive dashboard.

## Project Purpose

The goal of this project was to design a modular, data-driven rewards platform capable of:

- Designing RESTful endpoints to capture and validate fan preference submissions
- Implementing deterministic and weighted reward allocation strategies
- Persisting relational data using PostgreSQL with enforced integrity constraints
- Aggregating and visualizing reward distribution metrics through structured queries

## Tech Stack

- **Backend:** Java, Spring Boot  
- **Persistence:** MyBatis (XML mappers)  
- **Database:** PostgreSQL  
- **Architecture:** Layered architecture (Controller → Service → Mapper)  
- **Frontend:** Thymeleaf templates + RESTful JSON endpoints  
- **Testing:** Service-level component testing  
- **Version Control:** Git  

## System Architecture

The application follows a modular layered architecture:

- **Controller Layer:** Handles HTTP requests and response formatting
- **Service Layer:** Implements business logic and validation
- **Mapper Layer:** Manages database interaction
- **Database Layer:** Relational schema enforcing referential integrity and data consistency

Service-layer validation and structured exception handling ensure predictable API behavior and consistent error responses.

## Database Design

The PostgreSQL schema was designed to ensure:

- Enforced referential integrity through foreign key constraints linking Preference and Reward records to Fan, Stand, and Seat tables, with cascading delete behavior to prevent orphaned records
- Maintained relational consistency using primary key identity columns across core entities (Fan, Preference, Reward, Seat)
- Applied database-level validation constraints including UNIQUE (email), NOT NULL fields, and CHECK constraints to ensure data accuracy and enforce valid occupation codes

<img width="995" height="631" alt="Stadium_Rewards_ER_Diagram - Sam Lato" src="https://github.com/user-attachments/assets/9282ea2b-0744-44a5-af01-3bae37bb5e98" />

## Design Decisions

- Enforced database-level constraints (UNIQUE, NOT NULL, CHECK) to maintain data integrity independently of application logic
- Used ON DELETE CASCADE on foreign keys to prevent orphaned records and simplify dependent record cleanup
- Structured the application using a layered architecture (Controller → Service → Mapper) to promote separation of concerns and maintainability
- Implemented a weighted selection algorithm for reward allocation to prioritize specific fan categories while maintaining fairness constraints
- Encapsulated SQL operations within MyBatis mappers to reinforce separation of concerns and maintain a clean boundary between business logic and the persistence layer

## Lessons Learned

- Importance of modular system design for maintainability
- Structured debugging through logging and validation
- Designing APIs that are predictable and easy to integrate
- Writing testable service-layer logic

## Future Improvements

- Extend the system with a concessions reservation feature that records structured preference data and feeds inventory forecasting models to optimize stock allocation
- Add authentication and role-based access control for management users to secure stadium preference operations
- Introduce CI/CD automation (GitHub Actions or Jenkins) for build validation, testing, and containerized deployment

## Running the Application

1. Clone the repository
2. Configure PostgreSQL database
3. Update application.properties with database credentials
4. Run the Spring Boot application
5. Access endpoints via Postman or browser
