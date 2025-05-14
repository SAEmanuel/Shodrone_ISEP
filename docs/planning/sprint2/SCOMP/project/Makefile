# Compiler
CC = gcc

# Compilation flags
CFLAGS = -Wall -Wextra -Iinclude

# Directories
SRC_DIR = src
OBJ_DIR = obj
BIN = simulator

# Source and object files
SRCS = $(wildcard $(SRC_DIR)/*.c)
OBJS = $(SRCS:$(SRC_DIR)/%.c=$(OBJ_DIR)/%.o)

# Default target (does nothing)
all:
	@echo "Use 'make compile' to build the project."

# Compile target
compile: $(BIN)

# Build the binary
$(BIN): $(OBJS)
	$(CC) $(CFLAGS) -o $@ $^ -lm

# Compile each source file to object file
$(OBJ_DIR)/%.o: $(SRC_DIR)/%.c
	@mkdir -p $(OBJ_DIR)
	$(CC) $(CFLAGS) -c $< -o $@

# Clean up
clean:
	rm -rf $(OBJ_DIR) $(BIN)