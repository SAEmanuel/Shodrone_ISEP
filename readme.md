# Project Shodrone

## 1. Description of the Project

Shodrone is a software system designed to support the back-office operations of Shodrone, a company specializing in customized drone multimedia shows. The system enables the management of clients, client show requests, and a library of drone figures, integrating functionalities across multiple roles (Admin, CRM Manager, CRM Collaborator, Show Designer, Drone Tech). It supports the creation, simulation, and testing of drone figures and shows using a Domain-Specific Language (DSL) to ensure scalability and avoid vendor lock-in. Key features include customer management (e.g., registration, status updates), show request handling (e.g., proposal generation, scheduling), and a robust simulation system to validate figures and full shows for safety (e.g., collision detection). Developed as part of the 4th semester of the Degree in Informatics Engineering (LEI) at ISEP, the project follows a Scrum methodology across three sprints, integrating knowledge from five course units: EAPLI, LPROG, RCOMP, SCOMP, and LAPR4.

## 2. Planning and Technical Documentation

[Planning and Technical Documentation](docs/planning/planning.md)

[FURPS+](docs/planning/global-artifacts/01.requirements-engineering/supplementary-specification.md)


## 3. How to Build

The Shodrone project is a multi-module Maven project with different modules: `shodrone-appX` . Follow these steps to build the system:

1. **Prerequisites**:
    - Java 11 (or higher) installed.
    - Maven 3.6+ (or use the provided `mvnw` wrapper).
    - Git for cloning the repository.
    - Ensure `plantuml.jar` is available for diagram generation (optional, for documentation).

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/Departamento-de-Engenharia-Informatica/sem4pi-2024-2025-sem4pi_2024_2025_g61.git
   cd sem4pi-2024-2025-sem4pi_2024_2025_g61_shodrone
   ```

3. **Build the Project**:
   - **Using Maven Directly**:
     ```bash
     mvn clean install --batch-mode --no-transfer-progress
     ```
     This command builds all modules, runs unit tests, and packages the applications into JAR files in each module’s `target/` directory (e.g., `shodrone-appX/target/shodrone-appX-0.1.0-SNAPSHOT.jar`). The `--batch-mode` flag ensures non-interactive execution, and `--no-transfer-progress` suppresses download progress logs for a cleaner output.

   - **Using Provided Scripts**:
     The provided scripts automate the build process, including environment checks for Maven and `JAVA_HOME`, and provide feedback on success or failure.
      - For Linux/Unix/macOS:
        ```bash
        ./build-all.sh
        ```
        This script:
         - Verifies that Maven is installed and in the system PATH.
         - Checks if `JAVA_HOME` is configured (manual verification recommended).
         - Executes `mvn clean install --batch-mode --no-transfer-progress`.
         - Displays a success message ("✅ Build concluído com sucesso!") or an error message ("❌ Erro durante a build!") based on the build outcome.

      - For Windows:
        ```cmd
        build-all.bat
        ```
        This script:
         - Verifies that Maven is installed and in the system PATH using the `where` command.
         - Prompts to ensure `JAVA_HOME` is configured for the correct JDK.
         - Executes `mvn clean install --batch-mode --no-transfer-progress`.
         - Outputs a success message ("✅ Build concluido com sucesso!") or an error message ("❌ Erro durante a build!") depending on the result.

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

The project includes unit tests for all modules, implemented with JUnit 4 (NFR03). Tests cover DSL parsing, simulation logic, client management, and more.

1. **Run All Tests**:
   From the project root, execute:
   ```bash
   mvn test
   ```
   This runs tests across all modules.

2. **Run Tests for a Specific Module**:
    - For `shodrone-appX` :
      ```bash
      cd shodrone-appX
      mvn test
      ```

3. **Run Specific Test Suite** (e.g., Simulation Tests):
   ```bash
   mvn test -Dtest=SimulationTest
   ```
   This targets simulation-related tests in `shodrone-appX`.

4. **View Results**:
    - Test reports are generated in each module’s `target/surefire-reports/` directory (e.g., `shodrone-appX/target/surefire-reports/`).
    - Continuous integration runs nightly tests, with metrics published in the GitHub repository (NFR05).

## 5. How to Run

The system consists of multiple applications: 

1. **Build the Project**:
   Follow the "How to Build" steps to generate the JAR files.

2. **Run `shodrone-apps`**:
   - **Using Provided Scripts**:
      - For Linux/Unix/macOS:
        ```bash
        ./run_shodrone_appX.sh
        ```
        This script executes the `shodrone-appX` application by running the main class `pt.isep.shodrone.appX.Main` using the JAR file `shodrone-appX-0.1.0.jar`.
      - For Windows:
        ```cmd
        run_shodrone_appX.bat
        ```
        This script performs the same action as the Linux script, adjusted for Windows path conventions.
   - **Manually**:
     ```bash
     cd shodrone-appX
     java -jar target/shodrone-appX-0.1.0.jar
     ```

## 6. How to Generate PlantUML Diagrams

To generate PlantUML diagrams for documentation, execute the script (for the moment, only for Linux/Unix/macOS):

    ./generate-plantuml-diagrams.sh

- **Prerequisites**: Ensure PlantUML and Java are installed.
- **Process**: The script processes `.puml` files in the `docs/` folder, generating PNG diagrams stored alongside the source files (NFR02).
- **Manual Generation**: Alternatively, run `java -jar plantuml.jar docs/*.puml` from the root if the script is unavailable or on unsupported OS.
- **Output**: Diagrams (e.g., use case, class diagrams) are saved in SVG format for scalability, with source files retained for edits.

