# US345 - Drone Language Plugin Deployment and Configuration

## 1. Requirements Engineering

### 1.1. User Story Description

As a Drone Tech,  
I want to deploy and configure plugins that analyze and validate drone programs written in various drone languages,  
So that the system can support multiple drone languages with language-specific validation logic.

### 1.2. Customer Specifications and Clarifications

- Each drone language must have its own dedicated plugin responsible for parsing and validating drone programs in that language.
- Plugins rely on ANTLR-generated lexer, parser, and visitor Java classes to process the language syntax and semantics.
- The system uses Java classes compiled from user story US253 for visitor pattern implementations to traverse parse trees.
- Plugins must integrate seamlessly with the host system, supporting input processing, validation, and error reporting.
- Validation diagnostics must provide detailed feedback, including syntax and semantic error messages with line and column numbers.
- Drone program inputs are plain text files containing code written in the respective drone language.
- The build process, including grammar generation, Java compilation of lexer/parser/visitor, and testing, is automated via a Makefile.
- Plugins should be modular, allowing independent deployment, configuration, and updates without impacting other language plugins.

**Clarifications:**

- **Q: How is the ANTLR grammar linked to Java visitor classes?**  
  A: The ANTLR grammar files generate lexer, parser, and visitor Java classes, which are compiled and used by the plugin to perform validation as per US253.

- **Q: How are plugins built and integrated?**  
  A: Using a Makefile that automates ANTLR code generation, Java compilation, and packaging for deployment.

- **Q: How are errors surfaced to users?**  
  A: Through detailed diagnostic messages produced by `DiagnosticErrorListener` and custom visitor validations, including precise line and column locations.

### 1.3. Acceptance Criteria

- **AC1**: Each drone language plugin includes an ANTLR grammar file formally defining its syntax.
- **AC2**: Lexer, parser, and visitor Java classes are automatically generated and compiled using the Makefile.
- **AC3**: The plugin successfully parses valid drone program files without errors.
- **AC4**: Syntax and semantic errors are detected and reported with detailed diagnostics including line/column info.
- **AC5**: Multiple plugins can be deployed, configured, and run concurrently without interference.
- **AC6**: Validation results consist of success confirmations or detailed lists of errors.
- **AC7**: Plugin lifecycle management (initialization, execution, shutdown) works reliably and cleanly.
- **AC8**: The Makefile correctly automates ANTLR generation, Java compilation, testing, and cleaning steps.

### 1.4. Dependencies

- **ANTLR toolchain**: For generating lexer, parser, and visitor Java classes from drone language grammar files.
- **Java compiler**: To compile ANTLR-generated classes and custom visitor implementations from US253.
- **Makefile**: Automates the entire build pipeline including grammar generation, compilation, and test execution.
- **Plugin framework**: The host system’s infrastructure that supports plugin loading, configuration, and execution.
- **Input files**: Plain text drone program files written in the respective language syntax.

### 1.5. Input and Output Data

**Input Data:**

- Drone program source files (`*.txt` or language-specific extensions), containing code in the target drone language.

**Output Data:**

- Validation results indicating success or failure.
- Diagnostic messages detailing syntax and semantic errors, with exact line and column locations.
- Abstract Syntax Tree (AST) or parse tree for use by further analysis or execution modules.
- Execution logs or status messages related to plugin operation.

### 1.6. System Sequence Diagram (SSD)

Below is a PlantUML snippet illustrating the sequence of interactions for drone program validation using the plugin framework:

![System Sequence Diagram](./svg/us345-sequence-diagram.svg)

---

### 1.7 Other Relevant Remarks

