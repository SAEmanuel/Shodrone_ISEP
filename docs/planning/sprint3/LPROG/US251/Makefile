ANTLR_JAR=../lib/antlr-4.13.1-complete.jar
JUNIT_JAR=../lib/junit-platform-console-standalone-1.9.3.jar
CLASSPATH=.:$(ANTLR_JAR):$(JUNIT_JAR):gen
MAIN_CLASS=Main
GRAMMAR=Figures.g4
START_RULE=program
INPUT=input.txt
OUT_DIR=gen

all: compile

$(OUT_DIR):
	mkdir -p $(OUT_DIR)

antlr: $(OUT_DIR)
	java -Xmx500M -cp $(CLASSPATH) org.antlr.v4.Tool -visitor -o $(OUT_DIR) $(GRAMMAR)

compile: antlr
	javac -cp $(CLASSPATH) -d $(OUT_DIR) $(OUT_DIR)/*.java tests/*.java Main.java

run: compile
	java -cp $(CLASSPATH) $(MAIN_CLASS)

gui: compile
	java -cp $(CLASSPATH) org.antlr.v4.gui.TestRig Figures $(START_RULE) -gui $(INPUT)

tests: compile
	java -jar $(JUNIT_JAR) --class-path $(OUT_DIR):$(ANTLR_JAR) --scan-class-path

clean:
	rm -rf $(OUT_DIR)
