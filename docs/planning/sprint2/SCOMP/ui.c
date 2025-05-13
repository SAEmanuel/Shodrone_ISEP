#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h> // para system()

// ANSI Colors
#define ANSI_RESET         "\033[0m"
#define ANSI_BRIGHT_BLACK  "\033[90m"
#define ANSI_BRIGHT_WHITE  "\033[97m"
#define ANSI_BRIGHT_CYAN   "\033[36m"
#define ANSI_BLUE          "\033[34m"


#define BORDER_WIDTH       60
#define COMPANY_NAME       "ShoDrone"
#define APP_NAME           "DroneTechApp"
#define VERSION            "1.0.0"
#define TYPE_PERSISTENCE   1

void print_border(const char *border) {
    printf(ANSI_BRIGHT_BLACK "%s" ANSI_RESET "\n", border);
}

void print_separator(const char *separator) {
    printf(ANSI_BRIGHT_BLACK "%s" ANSI_RESET "\n", separator);
}

void center_text(const char *text, int width) {
    int len = strlen(text);
    int padding = (width - len) / 2;
    if (padding < 0) padding = 0;
    for (int i = 0; i < padding; i++) printf(" ");
    printf("%s\n", text);
}

void print_header() {
    const char *border = "============================================================";
    const char *separator = "------------------------------------------------------------";

    // Obter a data/hora atual formatada
    time_t now = time(NULL);
    struct tm *t = localtime(&now);
    char formattedDate[64];
    strftime(formattedDate, sizeof(formattedDate), "%Y-%m-%d %H:%M:%S", t);

    print_border(border);

    printf(ANSI_BRIGHT_WHITE);
    center_text(COMPANY_NAME " - Innovating Drone Shows", BORDER_WIDTH);
    printf(ANSI_RESET);

    print_separator(separator);

    printf("Application        : " ANSI_BLUE "%s" ANSI_RESET "\n", APP_NAME);
    printf("Version            : " ANSI_BRIGHT_CYAN "%s" ANSI_RESET "\n", VERSION);
    printf("Session started on : " ANSI_BLUE "%s" ANSI_RESET "\n", formattedDate);

    print_separator(separator);

    printf(ANSI_BRIGHT_WHITE);
    center_text("Welcome to " APP_NAME ". Ready to manage your drone shows.", BORDER_WIDTH);
    printf(ANSI_RESET);

    print_border(border);
}

void print_main_menu() {
    printf(ANSI_BRIGHT_BLACK "╔════════════════════════════════════════╗\n");
    printf("║" ANSI_BRIGHT_WHITE "               MAIN MENU                " ANSI_BRIGHT_BLACK "║\n");
    printf("╠════════════════════════════════════════╣\n");

    printf("║    " ANSI_BRIGHT_BLACK "(1)" ANSI_RESET " -  Login                        " ANSI_BRIGHT_BLACK "║\n");
    printf("║    " ANSI_BRIGHT_BLACK "(2)" ANSI_RESET " -  Development Team             " ANSI_BRIGHT_BLACK "║\n");
    printf("║    " ANSI_BRIGHT_BLACK "(0)" ANSI_RESET " -  Cancel                       " ANSI_BRIGHT_BLACK "║\n");

    printf("╚════════════════════════════════════════╝\n" ANSI_RESET);
    printf("Type your option: ");
}

void show_development_team() {
    printf("\n\n══════════════════════════════════════════\n");
    printf(ANSI_BRIGHT_WHITE "            DEVELOPMENT TEAM\n\n" ANSI_RESET);
    printf("  Emanuel Almeida   - 1230839@isep.ipp.pt\n");
    printf("  Francisco Santos  - 1230564@isep.ipp.pt\n");
    printf("  Jorge Ubaldo      - 1231274@isep.ipp.pt\n");
    printf("  Paulo Mendes      - 1231498@isep.ipp.pt\n");
    printf("  Romeu Xu          - 1230444@isep.ipp.pt\n");
}

void pause_and_clear() {
    printf("\nPressione Enter para continuar...");
    getchar(); // consome newline pendente
    getchar(); // espera pelo Enter
    system("clear"); // em Windows: system("cls");
}

void handle_option(int option) {
    switch (option) {
        case 1:
            printf("\n[Login feature not yet implemented]\n");
            break;
        case 2:
            show_development_team();
            break;
        case 0:
            printf("\nExiting... See you next time!\n");
            return;
        default:
            printf("\nInvalid option. Please try again.\n");
            break;
    }
    pause_and_clear(); // esperar e limpar apenas se não for sair
}

int main() {
    int option;

    print_header();
    printf("\n\n\n");

    do {
        print_main_menu();
        scanf("%d", &option);
        handle_option(option);
    } while (option != 0);

    return TYPE_PERSISTENCE;
}
