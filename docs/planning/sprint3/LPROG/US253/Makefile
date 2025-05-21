ANTLR_JAR=../lib/antlr-4.13.1-complete.jar
CLASSPATH=.:$(ANTLR_JAR):gen
MAIN_CLASS=Main
GRAMMAR=DroneOne.g4
START_RULE=program
INPUT=input.txt
OUT_DIR=gen

all: compile

$(OUT_DIR):
	mkdir -p $(OUT_DIR)

antlr: $(OUT_DIR)
	java -Xmx500M -cp $(CLASSPATH) org.antlr.v4.Tool -o $(OUT_DIR) $(GRAMMAR)

compile: antlr
	javac -cp $(CLASSPATH) -d $(OUT_DIR) $(OUT_DIR)/*.java Main.java

run: compile
	java -cp $(CLASSPATH) $(MAIN_CLASS)

gui: compile
	java -cp $(CLASSPATH) org.antlr.v4.gui.TestRig DroneOne $(START_RULE) -gui $(INPUT)

clean:
	rm -rf $(OUT_DIR)
