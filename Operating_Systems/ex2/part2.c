#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>

void write_message(const char *message, int count) {
    for (int i = 0; i < count; i++) {
        printf("%s\n", message);
        usleep((rand() % 100) * 1000); // Random delay between 0 and 99 milliseconds
    }
}

int main(int argc, char **argv) {

    if (argc < 4) {
        fprintf(stderr, "Usage: %s <message1> <message2> ... <count>", argv[0]);
        return 1;
    }
    int cnt = atoi(argv[argc - 1]);
    for (int i = 0; i < argc - 2; i++) {
        pid_t f1 = fork();
        if (f1 == -1) {
            // in case fork failed
            perror("error using fork function");
            return 1;
        }
        if (f1 == 0) {
            // child process
            int fdLock;
            while (1) {
                fdLock = open("lockfile.lock", O_EXCL | O_CREAT, S_IRUSR | S_IWUSR);
                if (fdLock == -1) {
                    usleep((rand() % 100) * 10000);
                } else {
                    write_message(argv[i + 1], cnt);
                    close(fdLock);
                    unlink("lockfile.lock");
                    exit(0);
                }
            }
        }
    }
    // parent process
    for (int i = 0; i < argc - 2; i++) {
        if (wait(NULL) == -1) {
            perror("failed to wait for child process");
        }
    }
    return 0;
}
