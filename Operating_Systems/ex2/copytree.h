// copytree.h
#ifndef COPYTREE_H
#define COPYTREE_H

#ifdef __cplusplus
extern "C" {
#endif

void copy_file(const char *src, const char *dest, int copy_symlinks, int copy_permissions);
void copy_directory(const char *src, const char *dest, int copy_symlinks, int copy_permissions);
void help(const char *src, const char *dest, int copy_symlinks, int copy_permissions);
char* unmached_offset(const char* str1, const char* str2, const char* str3);

#ifdef __cplusplus
}
#endif

#endif // COPYTREE_H