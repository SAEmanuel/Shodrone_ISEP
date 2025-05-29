# Paths to dependencies
ANTLR_JAR := ../lib/antlr-4.13.1-complete.jar
JUNIT_JAR := ../lib/junit-platform-console-standalone-1.9.3.jar
MOCKITO_JAR := ../lib/mockito-core-5.10.0.jar
BYTEBUDDY_JAR := ../lib/byte-buddy-1.14.5.jar
BYTEBUDDY_AGENT_JAR := ../lib/byte-buddy-agent-1.14.5.jar

# Project structure
GRAMMAR_FILE := ../US253/DroneOne.g4
GEN_DIR := ../US253/US253
BIN_DIR := bin
INPUT_FILE := ../US253/input.txt
TEST_DIR := tests
MAIN_CLASS := Main
START_RULE := program

# Source files
SRC_FILES := DroneOnePlugin.java \
             DroneOneValidator.java \
             ValidationResult.java \
             Main.java

TEST_SRC := $(TEST_DIR)/DroneOneValidatorTest.java \
            $(TEST_DIR)/TestDronePlugin.java \
            $(TEST_DIR)/ValidationResultTest.java

# Classpath for compiling and running (includes mockito and bytebuddy)
CLASSPATH := .:$(ANTLR_JAR):$(JUNIT_JAR):$(MOCKITO_JAR):$(BYTEBUDDY_JAR):$(BYTEBUDDY_AGENT_JAR):$(BIN_DIR)

# Default target
all: compile

# Generate ANTLR parser and visitor
generate_antlr:
	mkdir -p $(GEN_DIR)
	java -Xmx500M -cp $(ANTLR_JAR) org.antlr.v4.Tool -visitor -o $(GEN_DIR) $(GRAMMAR_FILE)

# Compile sources and tests
compile: generate_antlr
	mkdir -p $(BIN_DIR)
	javac -cp "$(CLASSPATH)" -d $(BIN_DIR) $(GEN_DIR)/*.java $(SRC_FILES) $(TEST_SRC)

# Run main program
run: compile
	java -cp "$(CLASSPATH)" $(MAIN_CLASS) $(INPUT_FILE)

# Run tests using JUnit Console (with full classpath including byte-buddy-agent)
tests: compile
	java -jar $(JUNIT_JAR) --class-path $(BIN_DIR):$(ANTLR_JAR):$(MOCKITO_JAR):$(BYTEBUDDY_JAR):$(BYTEBUDDY_AGENT_JAR) --scan-class-path

# Optional: ANTLR GUI visualization of input file
gui: compile
	java -cp "$(CLASSPATH)" org.antlr.v4.gui.TestRig DroneOne $(START_RULE) -gui $(INPUT_FILE)

# Clean generated files
clean:
	rm -rf $(BIN_DIR)
	rm -rf $(GEN_DIR)

.PHONY: all generate_antlr compile run tests gui clean
