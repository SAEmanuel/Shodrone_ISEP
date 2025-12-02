#ifndef UI_H
#define UI_H

// Reset
#define ANSI_RESET           "\033[0m"

// Estilos
#define ANSI_BOLD            "\033[1m"
#define ANSI_UNDERLINE       "\033[4m"
#define ANSI_REVERSED        "\033[7m"

// Cores padrão (texto)
#define ANSI_BLACK           "\033[30m"
#define ANSI_RED             "\033[31m"
#define ANSI_GREEN           "\033[32m"
#define ANSI_YELLOW          "\033[33m"
#define ANSI_BLUE            "\033[34m"
#define ANSI_MAGENTA         "\033[35m"
#define ANSI_CYAN            "\033[36m"
#define ANSI_WHITE           "\033[37m"

// Cores brilhantes (texto)
#define ANSI_BRIGHT_BLACK    "\033[90m"
#define ANSI_BRIGHT_RED      "\033[91m"
#define ANSI_BRIGHT_GREEN    "\033[92m"
#define ANSI_BRIGHT_YELLOW   "\033[93m"
#define ANSI_BRIGHT_BLUE     "\033[94m"
#define ANSI_BRIGHT_MAGENTA  "\033[95m"
#define ANSI_BRIGHT_CYAN     "\033[96m"
#define ANSI_BRIGHT_WHITE    "\033[97m"

// Fundo padrão
#define ANSI_BG_BLACK        "\033[40m"
#define ANSI_BG_RED          "\033[41m"
#define ANSI_BG_GREEN        "\033[42m"
#define ANSI_BG_YELLOW       "\033[43m"
#define ANSI_BG_BLUE         "\033[44m"
#define ANSI_BG_MAGENTA      "\033[45m"
#define ANSI_BG_CYAN         "\033[46m"
#define ANSI_BG_WHITE        "\033[47m"

// Fundo brilhante
#define ANSI_BG_BRIGHT_BLACK   "\033[100m"
#define ANSI_BG_BRIGHT_RED     "\033[101m"
#define ANSI_BG_BRIGHT_GREEN   "\033[102m"
#define ANSI_BG_BRIGHT_YELLOW  "\033[103m"
#define ANSI_BG_BRIGHT_BLUE    "\033[104m"
#define ANSI_BG_BRIGHT_MAGENTA "\033[105m"
#define ANSI_BG_BRIGHT_CYAN    "\033[106m"
#define ANSI_BG_BRIGHT_WHITE   "\033[107m"

int runUI();

#endif