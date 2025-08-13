#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>

int main(int argc, char *argv[]) {

    // history database
    char *history[100];
    int history_cnt = 0;
    for (int i = 0; i < 100; i++) {
        history[i] = malloc(101);
    }

    char *env_paths[101];
    int env_paths_count = 0;

    // load provided directories into env_paths
    for (int i = 1; i < argc; i++) {
        env_paths[i - 1] = argv[i];
        env_paths_count++;
    }
    env_paths[env_paths_count] = NULL;

    char command[100];

    while (1) {
        printf("$ ");
        fflush(stdout);
        if (fgets(command, sizeof(command), stdin) == NULL) {
            break;
        }

        command[strcspn(command, "\n")] = '\0';

        if (history_cnt < 100) {
            strcpy(history[history_cnt], command);
            history_cnt++;
        } else {
            for (int i = 0; i < 99; i++) {
                strcpy(history[i], history[i + 1]);
            }
            strcpy(history[99], command);
        }

        char *args[100];
        char *token = strtok(command, " ");
        int arg_cnt = 0;
        while (token != NULL) {
            args[arg_cnt] = token;
            arg_cnt++;
            token = strtok(NULL, " ");
        }
        args[arg_cnt] = NULL;

        // main functionalety of the code
        if (arg_cnt == 0) {
            continue;
        } else if (strcmp(args[0], "exit") == 0) {
            break;
        } else if (strcmp(args[0], "cd") == 0) {
            if (arg_cnt > 1) {
                if (chdir(args[1]) != 0) {
                    perror("chdir failed");
                }
            } else {
                fprintf(stderr, "cd: missing argument\n");
            }
        } else if (strcmp(args[0], "pwd") == 0) {
            char cwd[101];
            if (getcwd(cwd, sizeof(cwd)) != NULL) {
                printf("%s\n", cwd);
            } else {
                perror("getcwd failed");
            }
        } else if (strcmp(args[0], "history") == 0) {
            for (int i = 0; i < history_cnt; i++) {
                printf("%s\n", history[i]);
            }
        } else {
            pid_t pid = fork();
            if (pid < 0) {
                perror("fork failed");
                exit(1);
            } else if (pid == 0) {
                // child process
                for (int i = 0; env_paths[i] != NULL; i++) {
                    char path[101];
                    strcpy(path, env_paths[i]);
                    strcat(path, "/");
                    strcat(path, args[0]);
                    execv(path, args);
                }
                perror("execv failed");
                exit(1);
            } else {
                // parent process
                int status;
                if (waitpid(pid, &status, 0) < 0) {
                    perror("waitpid failed");
                    exit(1);
                }
            }
        }
    }

    for (int i = 0; i < 100; i++) {
        free(history[i]);
    }
    return 0;
}
