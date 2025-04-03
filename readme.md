# Project Shodrone

## 1. Description of the Project

Shodrone is a software system designed to support the back-office operations of Shodrone, a company specializing in customized drone multimedia shows. The system enables the management of clients, client show requests, and a library of drone figures, integrating functionalities across multiple roles (Admin, CRM Manager, CRM Collaborator, Show Designer, Drone Tech). It supports the creation, simulation, and testing of drone figures and shows using a Domain-Specific Language (DSL) to ensure scalability and avoid vendor lock-in. Key features include customer management (e.g., registration, status updates), show request handling (e.g., proposal generation, scheduling), and a robust simulation system to validate figures and full shows for safety (e.g., collision detection). Developed as part of the 4th semester of the Degree in Informatics Engineering (LEI) at ISEP, the project follows a Scrum methodology across three sprints, integrating knowledge from five course units: EAPLI, LPROG, RCOMP, SCOMP, and LAPR4.

## 2. Planning and Technical Documentation

[Planning and Technical Documentation](docs/planning/planning.md)

[FURPS+](docs/planning/global-artifacts/01.requirements-engineering/supplementary-specification.md)


## 3. How to Build

The Shodrone project is a multi-module Maven project with three main modules: `shodrone-app1` (client and show request management), `shodrone-app2` (simulation and testing), and `shodrone-util` (shared utilities). Follow these steps to build the system:

1. **Prerequisites**:
    - Java 11 (or higher) installed.
    - Maven 3.6+ (or use the provided `mvnw` wrapper).
    - Git for cloning the repository.
    - Ensure `plantuml.jar` is available for diagram generation (optional, for documentation).

2. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd sem4pi-2024-2025-sem4pi_2024_2025_g61_shodrone
   ```

3. **Build the Project**:
    - **Using Maven Directly**:
      ```bash
      mvn clean install
      ```
      This command builds all modules (`shodrone-util`, `shodrone-app1`, `shodrone-app2`), runs unit tests, and packages the applications into JAR files in each module’s `target/` directory (e.g., `shodrone-app1/target/shodrone-app1-1.0-SNAPSHOT.jar`).
    - **Using Provided Scripts**:
        - For Linux/Unix/macOS:
          ```bash
          ./build-all.sh
          ```
        - For Windows:
          ```cmd
          build-all.bat
          ```
      These scripts execute `mvn clean install` across all modules and handle platform-specific configurations.

4. **Quick Build Option** (Skip Tests for Faster Compilation):
    - For Linux/Unix/macOS:
      ```bash
      ./quickbuild.sh
      ```
    - For Windows:
      ```cmd
      quickbuild.bat
      ```
   These scripts run `mvn clean install -DskipTests` to skip unit tests, useful for rapid iteration during development.

5. **Verify Build**:
    - Check the console output for successful compilation.
    - Nightly builds are automated via GitHub Actions (NFR05), with results and metrics available in the repository.

## 4. How to Execute Tests

The project includes unit tests for all modules, implemented with JUnit 5 (NFR03). Tests cover DSL parsing, simulation logic, client management, and more.

1. **Run All Tests**:
   From the project root, execute:
   ```bash
   mvn test
   ```
   This runs tests across all modules (`shodrone-util`, `shodrone-app1`, `shodrone-app2`).

2. **Run Tests for a Specific Module**:
    - For `shodrone-app1` (client/show request management):
      ```bash
      cd shodrone-app1
      mvn test
      ```
    - For `shodrone-app2` (simulation):
      ```bash
      cd shodrone-app2
      mvn test
      ```
    - For `shodrone-util` (shared utilities):
      ```bash
      cd shodrone-util
      mvn test
      ```

3. **Run Specific Test Suite** (e.g., Simulation Tests):
   ```bash
   mvn test -Dtest=SimulationTest
   ```
   This targets simulation-related tests in `shodrone-app2`.

4. **View Results**:
    - Test reports are generated in each module’s `target/surefire-reports/` directory (e.g., `shodrone-app1/target/surefire-reports/`).
    - Continuous integration runs nightly tests, with metrics published in the GitHub repository (NFR05).

## 5. How to Run

The system consists of two main applications: `shodrone-app1` (client and show request management) and `shodrone-app2` (simulation and testing). Both use a JavaFX 11 interface.

1. **Build the Project**:
   Follow the "How to Build" steps to generate the JAR files.

2. **Configure the System**:
    - Edit `config.properties` in each module’s `src/main/resources/` directory (e.g., `shodrone-app1/src/main/resources/config.properties`).
    - Set the persistence mode (`persistence=in-memory` or `persistence=rdbms`).
    - For RDBMS, specify connection details (e.g., `db.url`, `db.user`, `db.password`) as per NFR07.

3. **Run `shodrone-app1` (Client and Show Request Management)**:
   ```bash
   cd shodrone-app1
   java -jar target/shodrone-app1-1.0-SNAPSHOT.jar
   ```
   This launches the JavaFX interface for managing clients, show requests, and proposals.

4. **Run `shodrone-app2` (Simulation and Testing)**:
   ```bash
   cd shodrone-app2
   java -jar target/shodrone-app2-1.0-SNAPSHOT.jar
   ```
   This launches the simulation interface for testing figures and shows.

5. **Default Data**:
   On first run, the system initializes default data (e.g., sample customers, figures) if configured in `config.properties` (NFR07).

6. **Access**:
   Log in using a Shodrone email (e.g., `admin@showdrone.com`) and default credentials listed in `docs/readme.md`.

## 6. How to Install/Deploy into Another Machine (or Virtual Machine)

To deploy the Shodrone system on another machine or VM:

1. **Prerequisites**:
    - Install Java 11, Maven, and a supported RDBMS (e.g., PostgreSQL) on the target machine (Linux or Windows).
    - Ensure network connectivity for remote database access.

2. **Transfer the Project**:
    - Clone the repository:
      ```bash
      git clone <repository-url>
      cd sem4pi-2024-2025-sem4pi_2024_2025_g61_shodrone
      ```
    - Alternatively, copy the project folder via SCP or ZIP.

3. **Build on the Target Machine**:
    - Run the build process:
      ```bash
      ./build-all.sh  # Linux/Unix/macOS
      # OR
      build-all.bat   # Windows
      ```
    - This ensures compatibility with the target environment.

4. **Set Up the Database**:
    - Configure the RDBMS (e.g., create a database and user in PostgreSQL).
    - Update `config.properties` in both `shodrone-app1` and `shodrone-app2` with the database connection details (NFR07).

5. **Deploy the Applications**:
    - Copy the JAR files to a deployment directory (e.g., `/opt/shodrone/` on Linux or `C:\shodrone\` on Windows).
    - Use the provided scripts to automate deployment:
        - For Linux/Unix/macOS:
          ```bash
          ./build-all.sh  # Includes deployment steps
          ```
        - For Windows:
          ```cmd
          build-all.bat
          ```
    - The scripts handle JAR placement and service setup (if applicable).

6. **Run the Applications**:
    - Start `shodrone-app1` and `shodrone-app2` as described in "How to Run".
    - Optionally, set up as services:
        - On Linux: Create systemd services for each app.
        - On Windows: Use a tool like NSSM to run the JARs as services.

7. **Verify Deployment**:
    - Access the JavaFX interfaces and confirm functionality (e.g., run a simulation, create a show request).
    - Check logs in each module’s `target/` directory for errors.

## 7. How to Generate PlantUML Diagrams

To generate PlantUML diagrams for documentation, execute the script (for the moment, only for Linux/Unix/macOS):

    ./generate-plantuml-diagrams.sh

- **Prerequisites**: Ensure PlantUML and Java are installed.
- **Process**: The script processes `.puml` files in the `docs/` folder, generating PNG diagrams stored alongside the source files (NFR02).
- **Manual Generation**: Alternatively, run `java -jar plantuml.jar docs/*.puml` from the root if the script is unavailable or on unsupported OS.
- **Output**: Diagrams (e.g., use case, class diagrams) are saved in SVG format for scalability, with source files retained for edits.

