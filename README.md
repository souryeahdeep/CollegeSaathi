# CollegeSaathi
The App that controlls everything in College. 
Divided into multiple microservices, it provides all the basic needs that a college system required.
1. QR Based Attendance System : Teacher can generate a qr (on basis of there class) and student will scan it for getting the attendance of the class. [Student needs to be in the college campus and connected to college wifi for scanning the qr code.]
2. Admins can add teachers and students. They can add the classes of the teacher.
3. Student can log into their account using ID no. They can update details, fetch result card of the last semester and basically everything that a student need to access.

Plans for Future: 
1. Library Service
2. Exam Enrollment

There can be lots of features in a college app. The app aims to comepelete secure digitalization of the events/ members of the college.

Note : This just a demo which I create this the picture of my college in mind. 

## Tech Stacks
Frontend : ReactJS, TailwindCSS.
Backend : SpringBoot, SpringDataJPA, Spring Security, Eureka Client and Server.

## Demo
👉 [Click here to watch the demo](https://drive.google.com/file/d/1ioaXySbreSNSi3ZjIN-GtFgbonAcaI4k/view?usp=drive_link)



## Prerequisites

Before setting up CollegeSaathi, ensure you have the following installed on your system:

- **Java Development Kit (JDK) 21** or higher
- **Apache Maven 3.8+** for building the project
- **PostgreSQL 14+** for the database
- **Node.js 18+** and **npm** (for frontend if applicable)
- **Git** for cloning the repository

## Installation

### Step 1: Clone the Repository

```bash
git clone https://github.com/souryeahdeep/CollegeSaathi.git
cd CollegeSaathi
```

### Step 2: Database Setup

1. Install and start PostgreSQL on your system
2. Create separate databases for each microservice:

```sql
CREATE DATABASE admin_service_db;
CREATE DATABASE student_service_db;
CREATE DATABASE teacher_service_db;
```

3. Create a database user (or use the default postgres user):

```sql
CREATE USER college_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE admin_service_db TO college_user;
GRANT ALL PRIVILEGES ON DATABASE student_service_db TO college_user;
GRANT ALL PRIVILEGES ON DATABASE teacher_service_db TO college_user;
```

### Step 3: Configure Application Properties

For each microservice, navigate to the `src/main/resources` directory and update the `application.properties` or `application.yml` file with your database credentials:

**Example configuration:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<database_name>
spring.datasource.username=college_user
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

Update this for:
- `admin-service/src/main/resources/application.properties`
- `student-service/src/main/resources/application.properties`
- `teacher-service/src/main/resources/application.properties`

### Step 4: Build the Microservices

Build each microservice using Maven:

```bash
# Build Service Registry (Eureka Server)
cd service-registry
mvn clean install
cd ..

# Build Admin Service
cd admin-service
mvn clean install
cd ..

# Build Student Service
cd student-service
mvn clean install
cd ..

# Build Teacher Service
cd teacher-service
mvn clean install
cd ..
```

### Step 5: Run the Microservices

Start the services in the following order:

#### 1. Start Service Registry (Eureka Server) - Port 8761
```bash
cd service-registry
mvn spring-boot:run
```

Wait for the Eureka Server to start completely (check http://localhost:8761)

#### 2. Start Admin Service
```bash
cd admin-service
mvn spring-boot:run
```

#### 3. Start Teacher Service
```bash
cd teacher-service
mvn spring-boot:run
```

#### 4. Start Student Service
```bash
cd student-service
mvn spring-boot:run
```

### Step 6: Verify Installation

1. **Eureka Dashboard**: Open http://localhost:8761 in your browser to see all registered services
2. **Check Service Health**: Verify that all microservices (admin-service, student-service, teacher-service) are registered with Eureka

### Step 7: Frontend Setup

The main repository contains a `Frontend` folder with two separate frontend applications:

#### Frontend Structure
- **student_frontend**: Student-facing web application
- **official_frontend**: Administrative and teacher-facing web application

Both frontends are built with React and Vite, and can be started independently.

#### Running Student Frontend

```bash
cd Frontend/student_frontend
npm install  # Install dependencies
npm run dev  # Start the development server
```

The student frontend will be accessible at http://localhost:5173 (or the next available port).

#### Running Official Frontend

```bash
cd Frontend/official_frontend
npm install  # Install dependencies
npm run dev  # Start the development server
```

The official frontend will be accessible at http://localhost:5173 (or the next available port).

**Note**: If you want to run both frontends simultaneously, you may need to modify the port configuration in the Vite config file for one of them, or Vite will automatically assign different ports.

## Alternative: Running with Docker (Optional)

If you prefer using Docker, you can create a `docker-compose.yml` file to orchestrate all services:

```bash
docker-compose up --build
```

## Troubleshooting

### Common Issues:

1. **Port Already in Use**: If you get port conflict errors, check which service is using the port and either stop it or change the port in the service's configuration file.

2. **Database Connection Failed**: 
   - Verify PostgreSQL is running: `sudo systemctl status postgresql`
   - Check database credentials in `application.properties`
   - Ensure databases are created

3. **Maven Build Failures**: 
   - Clear Maven cache: `mvn clean`
   - Delete `.m2/repository` and rebuild

4. **Services Not Registering with Eureka**:
   - Ensure Eureka Server is running first
   - Check network connectivity
   - Verify Eureka client configuration in each service

## Default Ports

- **Service Registry (Eureka)**: 8761
- **Admin Service**: Check application.properties
- **Student Service**: Check application.properties
- **Teacher Service**: Check application.properties

## Development Notes

- All services use Spring Boot 4.0.0
- Database migrations are handled by Hibernate (ddl-auto=update)
- WebSocket support is enabled for real-time features
- QR code generation uses ZXing library

## Support

For issues or questions, please open an issue on the GitHub repository.
