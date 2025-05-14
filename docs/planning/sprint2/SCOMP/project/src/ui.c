#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>
#include <dirent.h>
#include <unistd.h>
#include "../include/ui.h"
#include "simulation.h"

#define BORDER_WIDTH       60
#define COMPANY_NAME       "ShoDrone"
#define APP_NAME           "DroneTechApp"
#define VERSION            "1.0.0"
#define USERFORLOGIN       "droneTech@shodrone.app"
#define USERPASSWORD       "drone123!"
#define SCRIPTS_PATH       "./scripts/"
#define MAX_SCRIPTS        100
#define MAX_NAME_LEN       256

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

    time_t now = time(NULL);
    struct tm *t = localtime(&now);
    char formattedDate[64];
    strftime(formattedDate, sizeof(formattedDate), "%Y-%m-%d %H:%M:%S", t);

    print_border(border);

    printf(ANSI_BRIGHT_WHITE);
    center_text(COMPANY_NAME " - Innovating Drone Shows", BORDER_WIDTH);
    printf(ANSI_RESET);

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

int checkUserCredentials(char * user, char * password) {
    return strcmp(user, USERFORLOGIN) == 0 && strcmp(password, USERPASSWORD) == 0;
}

int simulatorUI() {
    printf("\n\n══════════════════════════════════════════\n");
    printf(ANSI_BRIGHT_WHITE "                SIMULATOR UI\n\n" ANSI_RESET);

    char scriptNames[MAX_SCRIPTS][MAX_NAME_LEN];
    int scriptCount = 0;

    DIR *dir = opendir(SCRIPTS_PATH);
    if (!dir) {
        printf(ANSI_BRIGHT_RED "✖ Failed to open scripts directory.\n" ANSI_RESET);
        return -1;
    }

    struct dirent *entry;
    while ((entry = readdir(dir)) != NULL) {
        if (entry->d_type == DT_REG) { // Apenas ficheiros normais
            strncpy(scriptNames[scriptCount], entry->d_name, MAX_NAME_LEN);
            scriptNames[scriptCount][MAX_NAME_LEN - 1] = '\0'; // proteção extra
            scriptCount++;
            if (scriptCount >= MAX_SCRIPTS) break;
        }
    }
    closedir(dir);

    if (scriptCount == 0) {
        printf(ANSI_BRIGHT_RED "✖ No scripts found in the directory.\n" ANSI_RESET);
        return -1;
    }

    printf("Available drone simulation scripts:\n\n");
    for (int i = 0; i < scriptCount; i++) {
        printf("  [%d] %s\n", i + 1, scriptNames[i]);
    }

    int choice = 0;
    do {
        printf("\nSelect a script to run [1-%d]: ", scriptCount);
        if (scanf("%d", &choice) != 1) {
            // Entrada inválida
            printf(ANSI_BRIGHT_RED "✖ Invalid input. Please enter a number.\n" ANSI_RESET);
            while (getchar() != '\n'); // limpar buffer
            choice = 0;
            continue;
        }

        if (choice < 1 || choice > scriptCount) {
            printf(ANSI_BRIGHT_RED "✖ Invalid selection. Try again.\n" ANSI_RESET);
        }

    } while (choice < 1 || choice > scriptCount);

    // Caminho completo do script selecionado
    char selectedScriptName[MAX_NAME_LEN];
    snprintf(selectedScriptName, sizeof(selectedScriptName), "%s", scriptNames[choice - 1]);

    printf(ANSI_BRIGHT_GREEN "\n✔ You selected: %s\n\n" ANSI_RESET, selectedScriptName);

    run_simulation(selectedScriptName);
    
    printf(ANSI_BRIGHT_GREEN"\n✔ Simulation terminated!"ANSI_RESET);
    return 0;
}


void loginUI() {
    printf("\n\n══════════════════════════════════════════\n");
    printf(ANSI_BRIGHT_WHITE "                  LOGIN MENU\n\n" ANSI_RESET);
    int attempts = 3;
    char user[100], password[100];

    while (attempts > 0) {
        printf("Enter:\n");
        printf("    • UserId/Email: ");
        scanf("%99s", user);
        printf("    • Password: ");
        scanf("%99s", password);

        if (checkUserCredentials(user, password)) {
            printf(ANSI_BRIGHT_GREEN "\n✔ Login successful! Welcome back.\n" ANSI_RESET);
            simulatorUI();
            return;
        }
        attempts--;
        printf( "\n%s✖ Invalid credentials. Attempts left: [%d]%s\n",ANSI_BG_BRIGHT_RED, attempts,ANSI_RESET);
    }

    printf(ANSI_BRIGHT_RED "\n✖ Too many failed attempts. Exiting login.\n" ANSI_RESET);
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
            loginUI();
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

    return 0;
}
