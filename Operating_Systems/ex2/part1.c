#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

int main(int argc, char **argv) {

    if (argc != 5) {
        fprintf(stderr, "Usage: %s <parent_message> <child1_message> <child2_message> <count>", argv[0]);
        return 1;
    }
    int cnt = atoi(argv[4]);
    // opening output.txt,
    int fd = open("output.txt", O_CREAT | O_TRUNC | O_RDWR, S_IRUSR | S_IWUSR);
    if (fd == -1) {
        perror("could not open output.txt file\n");
        return 1;
    }

    pid_t f1 = fork();

    if (f1 == -1) {
        // in case fork failed
        perror("error using fork function\n");
        return 1;
    } else if (f1 == 0) {
        // child1 process
        for (int i = 0; i < cnt; i++) {
            write(fd, argv[2], strlen(argv[2]));
        }
        close(fd);
        return 0;
    }

    // wait child1 to finish
    int status;
    if (waitpid(f1, &status, 0) == -1) {
        perror("failed to wait for child1 process\n");
    }

    pid_t f2 = fork();
    if (f2 == -1) {
        // in case fork failed
        perror("error using fork function\n");
        return 1;
    } else if (f2 == 0) {
        // child1 process
        for (int i = 0; i < cnt; i++) {
            write(fd, argv[3], strlen(argv[3]));
        }
        close(fd);
        return 0;
    }
    // wait child2 to finish
    if (waitpid(f2, &status, 0) == -1) {
        perror("failed to wait for child2 process\n");
    }

    for (int i = 0; i < cnt; i++) {
        write(fd, argv[1], strlen(argv[1]));
    }
    close(fd);
    return 0;
}
