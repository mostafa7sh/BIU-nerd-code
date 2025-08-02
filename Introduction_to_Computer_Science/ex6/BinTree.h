#ifndef BINTREE_H
#define BINTREE_H

typedef struct BinTree {
    int id;
    char *name;
    int age;
    char gender;
    void (*task)(void *val);
    void *action;
    struct BinTree *left;
    struct BinTree *right;
} BinTree;

void generic_function(BinTree *root, void (*task)(void *));
int generic_int_function(BinTree *root, int (*task)(void *));
void child_task(void *val);
void moshe_task(void *val);

// add child to tree functions
void child_birth(BinTree **root);
BinTree *create_child(int id, char name[11], char gender);
void add_child_to_BinTree(BinTree **root, BinTree *child);

// check if male exist
void remove_all_males(void **root);
int check_if_male_exist(void *root);
int find_all_males(BinTree *root);

// remove 120+ years old people from binary tree
void remove_120_years_old(void **root);
int find_120_years_old(BinTree *root);
BinTree *all_removing_cases(BinTree **root, int id);
BinTree *lowest_id_with_one_step_right(BinTree *root);

// add 20 to all ages
void getting_old_in_age(void *root);

// finding moshe case, tasks order
int check_moshe_if_exist(void *root);
void find_moshe_pre_order(void *root);
void find_moshe_in_order(void *root);
void find_moshe_post_order(void *root);

// calculating jews
int size_of_all_jews(void *root);
int size_of_male_jews(void *root);
int size_of_female_jews(void *root);

// print functions
void print_pre_order(void *root);
void print_post_order(void *root);
void print_in_order(void *root);

// clear all tree (cases)
int moshe_too_old(void *root);
void freeIsrael(void *root);
#endif