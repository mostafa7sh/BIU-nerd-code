#include "copytree.h"
#include <dirent.h>
#include <errno.h>
#include <fcntl.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>

int x = 0;
int symlink_cnt = 0;
char *meow;

void copy_file(const char *src, const char *dest, int copy_symlinks, int copy_permissions) {
    // 'src' must be single file name, 'dset' must be complete file path
    char *src_name = malloc(1024);
    char *dest_name = malloc(1024);
    strcpy(src_name, src);
    strcpy(dest_name, dest);
    int source;
    if ((source = open(src, O_RDWR)) == -1) {
        perror("error in open");
        exit(1);
    }
    strcat(dest_name, "/");
    strcat(dest_name, src_name);
    int destination;
    if ((destination = open(dest_name, O_CREAT | O_RDWR, S_IRWXU)) == -1) {
        perror("error in open");
        exit(1);
    }
    int reading;
    char buf[1024];
    while ((reading = read(source, buf, 1024)) > 0) {
        if (write(destination, buf, reading) == -1) {
            perror("error in writing");
            exit(1);
        }
    }
    if (reading == -1) {
        perror("error in reading");
        exit(1);
    }
    if (close(source) == -1) {
        perror("error in closing");
        exit(1);
    }
    if (close(destination) == -1) {
        perror("error in closing");
        exit(1);
    }
    if (copy_permissions) {
        char *cwd = malloc(1024);
        if (getcwd(cwd, 1024) == NULL) {
            perror("error in getcwd");
            exit(1);
        }
        strcat(cwd, "/");
        strcat(cwd, src_name);
        struct stat permissions;
        if (stat(cwd, &permissions) == -1) {
            perror("erro in stat");
            exit(1);
        }
        if (chmod(dest_name, permissions.st_mode) == -1) {
            perror("error in chmod");
            exit(1);
        }
        free(cwd);
    }
    free(src_name);
    free(dest_name);
}

void copy_directory(const char *src, const char *dest, int copy_symlinks, int copy_permissions) {
    DIR *dip_src;
    DIR *dip_dest;
    struct dirent *dit;
    struct stat check_dest;
    int check;

    if (x == 0) {
        meow = malloc(1024);
        getcwd(meow, 1024);
        x = 1;
    }
    if ((dip_src = opendir(src)) == NULL) {
        perror("opendir src");
        exit(1);
    }
    // checking if dest direcotory already exist
    if ((check = stat(dest, &check_dest)) == -1) {
        if (errno == ENOENT) {
            mkdir(dest, S_IRWXU);
        } else {
            perror("dir error");
            exit(1);
        }
    } else if (check == 0) {
        // directory exists
    }
    if (copy_permissions) {
        struct stat permissions;
        if (stat(src, &permissions) == -1) {
            perror("error in stat");
            exit(1);
        }
        if (chmod(dest, permissions.st_mode) == -1) {
            perror("error in chmod");
            exit(1);
        }
    }
    if ((dip_dest = opendir(dest)) == NULL) {
        perror("opendir dest");
        exit(1);
    }

    while ((dit = readdir(dip_src)) != NULL) {
        if ((unsigned char)dit->d_type == 4) {
            // directory
            if (!strcmp(dit->d_name, "..") || !strcmp(dit->d_name, ".")) {
                continue;
            }
            char *cwd = malloc(1024);
            if (getcwd(cwd, 1024) == NULL) {
                perror("error in getcwd");
                exit(1);
            }
            char *dest_path = malloc(1024);
            strcpy(dest_path, dest);
            strcat(dest_path, "/");
            strcat(dest_path, dit->d_name);
            if (mkdir(dest_path, S_IRWXU) == -1) {
                perror("error in mkdirr");
                exit(1);
            }
            chdir(src);
            copy_directory(dit->d_name, dest_path, copy_symlinks, copy_permissions);
            chdir(cwd);
            free(cwd);
            free(dest_path);
        } else if ((unsigned char)dit->d_type == 10) {
            symlink_cnt++;
            continue;
        } else if ((unsigned char)dit->d_type == 8) {
            // file
            char *cwd = malloc(1024);
            if (getcwd(cwd, 1024) == NULL) {
                perror("error in getcwd");
                exit(1);
            }

            chdir(src);
            copy_file(dit->d_name, dest, copy_symlinks, copy_permissions);
            chdir(cwd);
            free(cwd);
        }
    }
    char *buf = malloc(1024);
    getcwd(buf, 1024);
    if (strncmp(meow, buf, strlen(buf)) == 0 && symlink_cnt != 0 && copy_symlinks) {
        help(src, dest, copy_symlinks, copy_permissions);
    }
    closedir(dip_src);
    closedir(dip_dest);
}

void help(const char *src, const char *dest, int copy_symlinks, int copy_permissions) {
    DIR *dip_src;
    DIR *dip_dest;
    struct dirent *dit;
    struct stat check_dest;

    if ((dip_src = opendir(src)) == NULL) {
        perror("opendir src");
        exit(1);
    }
    // checking if dest direcotory already exist

    if (copy_permissions) {
        struct stat permissions;
        if (stat(src, &permissions) == -1) {
            perror("error in stat");
            exit(1);
        }
        if (chmod(dest, permissions.st_mode) == -1) {
            perror("error in chmod");
            exit(1);
        }
    }
    if ((dip_dest = opendir(dest)) == NULL) {
        perror("opendir dest");
        exit(1);
    }

    while ((dit = readdir(dip_src)) != NULL) {
        if ((unsigned char)dit->d_type == 4) {
            // directory
            if (!strcmp(dit->d_name, "..") || !strcmp(dit->d_name, ".")) {
                continue;
            }
            char *cwd = malloc(1024);
            if (getcwd(cwd, 1024) == NULL) {
                perror("error in getcwd");
                exit(1);
            }
            char *dest_path = malloc(1024);
            strcpy(dest_path, dest);
            strcat(dest_path, "/");
            strcat(dest_path, dit->d_name);
            chdir(src);
            help(dit->d_name, dest_path, copy_symlinks, copy_permissions);
            chdir(cwd);
            free(cwd);
            free(dest_path);
        } else if ((unsigned char)dit->d_type == 10) {
            // symlink file
            int len = 0;
            char *cwd = malloc(1024);
            char *orgin_path = malloc(1024);
            char *full_symlink_path = malloc(1024);
            if (getcwd(cwd, 1024) == NULL) {
                perror("error in getcwd");
                exit(1);
            }
            chdir(src);
            if (getcwd(full_symlink_path, 1024) == NULL) {
                perror("error in getcwd");
                exit(1);
            }
            strcat(full_symlink_path, "/");
            strcat(full_symlink_path, dit->d_name);
            if ((len = readlink(full_symlink_path, orgin_path, 1024)) == -1) {
                perror("error in readlink");
                exit(1);
            }
            orgin_path[len] = '\0';
            chdir(cwd);
            // now I need to do symlink, after I collected the original path and I have the
            char *dest_path = malloc(1024);
            strcpy(dest_path, dest);
            strcat(dest_path, "/");
            strcat(dest_path, dit->d_name);
            char *link = malloc(1024);
            strcpy(link, unmached_offset(full_symlink_path, orgin_path, dest_path));
            if (symlink(link, dest_path) == -1) {
                perror("error in symlink");
                exit(1);
            }
            // printf("\norgin_path = %s\nfull_symlink_path = %s\nlink = %s\ndest_path = %s\n", orgin_path, full_symlink_path, link, dest_path);
            if (copy_permissions) {
                struct stat permissions;
                if (stat(full_symlink_path, &permissions) == -1) {
                    perror("error in stat");
                    exit(1);
                }
                if (chmod(dest_path, permissions.st_mode) == -1) {
                    perror("error in chmod");
                    exit(1);
                }
            }
            if ((dip_dest = opendir(dest)) == NULL) {
                perror("opendir dest");
                exit(1);
            }

            free(cwd);
            free(orgin_path);
            free(full_symlink_path);
            free(dest_path);
            free(link);
        } else if ((unsigned char)dit->d_type == 8) {
            // file
            continue;
        }
    }

    closedir(dip_src);
    closedir(dip_dest);
}

char *unmached_offset(const char *str1, const char *str2, const char *str3) {
    int offset = 0;
    while (str1[offset] != '\0' && str2[offset] != '\0') {
        if (str1[offset] != str2[offset]) {
            break;
        }
        offset++;
    }

    char *src_firts_half = malloc(1024); // source directory path
    char *src_second_half = malloc(1024);
    char *dest_second_half = malloc(1024);
    char *dest_first_half = malloc(1024);
    char *cooked = malloc(1024);
    strncpy(src_firts_half, str1, offset);
    strcpy(src_second_half, str1 + offset);
    strcpy(dest_second_half, str2 + offset);
    strncpy(dest_first_half, str3, strlen(str3) - strlen(src_second_half));
    strcpy(cooked, dest_first_half);
    strcat(cooked, dest_second_half);

    free(src_firts_half);
    free(src_second_half);
    free(dest_second_half);
    free(dest_first_half);
    return cooked;
}