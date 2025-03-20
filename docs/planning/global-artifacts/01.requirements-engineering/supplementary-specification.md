# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:  
&nbsp; &nbsp; (i) are common across several US/UC;  
&nbsp; &nbsp; (ii) are not related to US/UC, namely: Audit, Reporting and Security._

* No info

## Usability

_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._

- The user interface shall be developed in JavaFX 11 to ensure a modern and intuitive user experience.

- Error messages shall be clear and concise, aiding users in understanding and resolving issues.

## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._

- The system shall ensure data integrity by accurately recording and updating information.
- All modules shall be thoroughly tested to minimize the occurrence of software bugs.


## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._

- The system shall handle a large number of concurrent users efficiently without significant degradation in performance.

## Supportability

_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._

- The system shall provide comprehensive documentation for users, administrators, and developers.
- Maintenance tasks, such as system updates and bug fixes, shall be easy to perform without disrupting system operation.
- The system shall support English language.

## 

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

- The system shall comply with recognized coding standards, ensuring readability and maintainability of the codebase.
- Unit tests shall be implemented using the JUnit 5 framework to validate the correctness of system functionalities.
- The system shall use object serialization for data persistence between runs.
- All images/figures produced during the development process shall be recorded in SVG format for scalability and portability.

### Implementation Constraints

_Specifies or constraints the code or construction of a system such
such as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._

(fill in here )

### Interface Constraints

_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._

(fill in here )

### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

(fill in here )