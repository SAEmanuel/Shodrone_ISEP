<div align="center">

  # 🚁 Shodrone
  ### Drone Multimedia Show Management

  <p align="center">
    A comprehensive back-office system for designing, managing, and simulating large-scale drone light shows using a custom <b>Domain-Specific Language (DSL)</b> and <b>3D Collision Detection</b>.
  </p>

  <p align="center">
    <a href="https://skillicons.dev">
      <img src="https://skillicons.dev/icons?i=java,c,maven,git,linux,idea,vscode&theme=dark" />
    </a>
  </p>

  ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
  ![C](https://img.shields.io/badge/C-00599C?style=for-the-badge&logo=c&logoColor=white)
  ![ANTLR](https://img.shields.io/badge/ANTLR-4-red?style=for-the-badge)
  ![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)

</div>

---

## 📘 Project Overview

Developed within the scope of the **Integrative Project (4th Semester - LEI/ISEP)**, this solution supports **Shodrone**, a company specializing in customized drone multimedia shows.

The system is a distributed solution that integrates **Enterprise Application logic**, **Compiler Design**, and **Operating System concepts**. It enables the creation of flight patterns using a custom language (DSL), manages the drone fleet, and simulates shows to ensure safety before physical deployment.

---

## ✨ Key Features

### ⚙️ Simulation & Safety (C & Systems Programming)
* **3D Collision Detection:** A high-performance simulator written in **C** that detects drone collisions in a 3D space.
* **Multi-Process Architecture:** Utilizes **Shared Memory** and **Semaphores** for synchronization and communication between the multi-threaded parent process and child drone processes.
* **Environmental Factors:** Simulates wind conditions and their impact on drone trajectory.

### 📝 Language Engineering (DSL & ANTLR)
* **Custom DSL:** Implementation of a **Domain-Specific Language** to describe figures.
* **Validation:** Syntax and semantic validation of flight scripts using **ANTLR**.

### 📡 Back-Office & CRM (Java)
* **Customer & Request Management:** Handling of client data, show requests, and proposal generation.
* **Drone Inventory:** Fleet management including model specifications and maintenance history.

---

## 🏗️ Architecture

The solution follows a modular architecture integrating five curricular areas:
1.  **EAPLI:** Enterprise Architecture & Business Logic.
2.  **LPROG:** Compiler Design (DSL Parsing & Processing).
3.  **SCOMP:** Systems Programming (C Simulation & Synchronization).
4.  **RCOMP:** Communication Protocols (Client/Server).
5.  **LAPR4:** Agile/Scrum Project Management.

---

## 🚀 Getting Started

### 📋 Prerequisites
* **Java 11** (or higher)
* **Maven 3.6+**
* **GCC/Clang** (for C simulation modules)
* **Git**

### 🛠️ How to Build

The project includes automated scripts to check the environment and build all modules.

**Using Provided Scripts (Recommended):**

* **Linux/macOS:**
    ```bash
    ./build-all.sh
    ```
* **Windows:**
    ```cmd
    build-all.bat
    ```

**Manual Maven Build:**
```bash
mvn clean install --batch-mode --no-transfer-progress
 ```

### ▶️ How to Run

The system consists of multiple applications. After building the project, you can run them using the provided scripts or manually via JAR files.

**Using Scripts (Back-office / User Apps):**
* **Linux/macOS:**
    ```bash
    ./run_shodrone_appX.sh
    ```
    
* **Windows:**
  ```cmd
    run_shodrone_appX.bat
  ```
    
**Manual Execution (JAR):**
```bash
java -jar shodrone-appX/target/shodrone-appX-0.1.0.jar
 ```
---

## 🖼️ Documentation

To generate the architectural diagrams (C4 Model) using PlantUML:

```bash
./generate-plantuml-diagrams.sh
```
<div align="center"> <sub>Developed for the Degree in Informatics Engineering (LEI) @ ISEP - 2024/2025</sub> </div>
