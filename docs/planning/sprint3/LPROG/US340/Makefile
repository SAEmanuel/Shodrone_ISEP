ANTLR_JAR=../lib/antlr-4.13.1-complete.jar
CLASSPATH=.:$(ANTLR_JAR)
GRAMMAR=../US251/Figures.g4
OUT_DIR_ANTLR=../US340/gen
OUTPUT_DIR_COMPILED_ANTLR=gen
OUT_DIR_VALIDATORS=bin
MAIN_CLASS=Main
ARGS=../US251/input.txt

DIR:
	mkdir -p $(OUT_DIR_ANTLR)
	mkdir -p $(OUT_DIR_VALIDATORS)

antlr: DIR
	cd ../US251 && java -Xmx500M -cp $(CLASSPATH) org.antlr.v4.Tool -visitor -o $(OUT_DIR_ANTLR) Figures.g4

compile: antlr
	javac -cp $(CLASSPATH) -d $(OUT_DIR_VALIDATORS) $(OUTPUT_DIR_COMPILED_ANTLR)/*.java $(MAIN_CLASS).java

run: compile
	java -cp $(CLASSPATH):$(OUT_DIR_VALIDATORS):$(OUT_DIR_ANTLR) $(MAIN_CLASS) $(ARGS)

clean:
	rm -rf $(OUT_DIR_ANTLR)/*
	rm -rf $(OUT_DIR_VALIDATORS)/*


