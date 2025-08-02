#include "BinTree.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/************************************************************************
 * The Input: a pointer to a function and the root of a binary tree *
 * The output: no specific output (regarding the pointer to the function *
 * The Function operation: calls another void function using a pointer *
 *************************************************************************/
void generic_function(BinTree *root, void (*task)(void *)) {
    task(root);
}
/************************************************************************
 * The Input: a pointer to a function and the root of a binary tree *
 * The output: returns the same output as the pointer to function *
 * The Function operation: calls another int function using a pointer,
 * and returns the value of the pointer to function *
 *************************************************************************/
int generic_int_function(BinTree *root, int (*task)(void *)) {
    return task(root);
}
/************************************************************************
 * The Input: a void pointer *
 * The output: prints the name of every kid (except Moshe) and "cry waaaa" *
 * The Function operation: simply prints the name of every kid (except Moshe)
 * and "cry waaaa" *
 *************************************************************************/
void child_task(void *val) {
    printf("%s cry waaaa\n", (char *)val);
}
/************************************************************************
 * The Input: a void pointer *
 * The output: prints "Moshe quiet" *
 * The Function operation: simply prints "Moshe quiet" *
 *************************************************************************/
void moshe_task(void *val) {
    printf("%s quiet\n", (char *)val);
}
/************************************************************************
 * The Input: name, gender and ID of every born child *
 * The output: returns a new node to the binary tree *
 * The Function operation: create new node using the proper memory allocation,
 * and gives the child his name, gender and Id *
 *************************************************************************/
BinTree *create_child(int id, char name[11], char gender) {
    BinTree *newChild;
    newChild = (BinTree *)malloc(sizeof(BinTree));
    newChild->name = (char *)malloc(sizeof(char) * 11);
    newChild->id = id;
    newChild->age = 0;
    strcpy(newChild->name, name);
    *(char *)newChild->action;
    newChild->action = newChild->name;
    newChild->gender = gender;
    /*
     * if the new child's name was "Moshe" and his gender is male
     * I give him his own task, otherwise give him child_task
     */
    if (strcmp("Moshe", newChild->name) == 0 && gender == 'M') {
        newChild->task = moshe_task;
    } else {
        newChild->task = child_task;
    }
    newChild->right = NULL;
    newChild->left = NULL;
    return newChild;
}
/************************************************************************
 * The Input: a pointer to the root and the new child node *
 * The output: no output, just adds the child to the tree *
 * The Function operation: using the new child id, I go through the binary tree,
 * look after his proper index and place him where he belongs*
 *************************************************************************/
void add_child_to_BinTree(BinTree **root, BinTree *child) {
    if ((*root) == NULL) {
        (*root) = child;
        return;
    }
    /*
     * if id was greater than the root, add the new child to the right subtree
     * otherwise add the child to the left subtree
     */
    else if (child->id < (*root)->id) {
        add_child_to_BinTree(&(*root)->left, child);
    } else {
        add_child_to_BinTree(&(*root)->right, child);
    }
}
/************************************************************************
 * The Input: a pointer of the root of the binary tree *
 * The output: no output *
 * The Function operation: takes from the user it name, gender and the id
 * of the new child, send that data to another function that create a node
 * that contains these data, after that send that node to another function that
 * adds the new child to his proper place in the binary tree*
 *************************************************************************/
void child_birth(BinTree **root) {
    char name[11];
    char gender;
    int id;
    printf("please enter child id:\n");
    scanf("%d", &id);
    printf("please insert child name:\n");
    scanf("%s", name);
    printf("please insert child gender:\n");
    scanf(" %c", &gender);

    BinTree *child = create_child(id, name, gender);
    add_child_to_BinTree(root, child);
}
/************************************************************************
 * The Input: a void pointer to pointer *
 * The output: no output *
 * The Function operation: takes the ID of a male child that his name isn't
 * "Moshe" using assistant function, after that I send the found ID to another
 * assistant function to delete him from the binary tree *
 *************************************************************************/
void remove_all_males(void **root) {
    BinTree *Root = (BinTree *)*root;
    if (root == NULL) {
        return;
    }
    // save the id of the desired person to remove him soon
    // (-1 means no one to remove yet)
    int childID = find_all_males(Root);
    if (childID == -1) {
        return;
    }
    Root = all_removing_cases(&Root, childID);
    *root = Root;
}
/************************************************************************
 * The Input: a void pointer *
 * The output: returns 1 if there is still male childes to remove from
 * the binary tree, otherwise returns 0 *
 * The Function operation: searches in the binary tree to find male child
 * that his name isn't "Moshe", after that returns 1 if I found someone
 * otherwise return 0 *
 *************************************************************************/
int check_if_male_exist(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return 0;
    } else if (Root->gender == 'M') {
        if (strcmp("Moshe", Root->name) == 0) {
            return check_if_male_exist(Root->left) +
                   check_if_male_exist(Root->right);
        }
        return check_if_male_exist(Root->left) +
               check_if_male_exist(Root->right) + 1;
    } else {
        return check_if_male_exist(Root->left) + check_if_male_exist(Root->right);
    }
}
/************************************************************************
 * The Input: binary tree pointer *
 * The output: if I found a male child that his name isn't "Moshe"
 * return his ID, otherwise returns -1 (means that didnt find any) *
 * The Function operation: search in the binary tree for male child that his
 * name isn't "Moshe", if found return his ID, otherwise return -1
 * (means that didnt find any) *
 *************************************************************************/
int find_all_males(BinTree *root) {
    if (root == NULL) {
        return -1;
    }
    if (root->gender == 'M' && strcmp("Moshe", root->name) != 0) {
        return root->id;
    }
    if (find_all_males(root->left) != -1) {
        return find_all_males(root->left);
    }
    if (find_all_males(root->right) != -1) {
        return find_all_males(root->right);
    }
    return -1;
}
/************************************************************************
 * The Input: a void pointer to pointer *
 * The output: no output *
 * The Function operation: search in the binary tree to someone that ages more
 * than 120 years, if found I send his ID to an assistant function to remove
 * the person from the binary tree *
 *************************************************************************/
void remove_120_years_old(void **root) {
    BinTree *Root = (BinTree *)*root;
    if (root == NULL) {
        return;
    }
    // save the id of the desired person to remove him soon
    // (-1 means no one to kill yet)
    int dead_id = find_120_years_old(Root);
    if (dead_id == -1) {
        return;
    }
    Root = all_removing_cases(&Root, dead_id);
    *root = Root;
}
/************************************************************************
 * The Input: binary tree pointer *
 * The output: the ID of someone that ages 120+ years old *
 * The Function operation: search in the binary tree for someone that ages 120+
 * years old, if found I return his ID, otherwise returns -1
 * (means that didnt find any) *
 *************************************************************************/
int find_120_years_old(BinTree *root) {
    if (root == NULL) {
        return -1;
    }
    if (root->age >= 120) {
        return root->id;
    }
    if (find_120_years_old(root->left) != -1) {
        return find_120_years_old(root->left);
    }
    if (find_120_years_old(root->right) != -1) {
        return find_120_years_old(root->right);
    }
    return -1;
}
/************************************************************************
 * The Input: a pointer to the top of binary tree and ID of someone to remove *
 * The output: a new tree without the person that I sent his ID *
 * The Function operation: search through the binary tree to find the desired ID
 * after I find him, make sure to cover all case of deletion so I dont miss up
 * the order of the binary tree, after the deletion I return back the new
 * binary tree *
 *************************************************************************/
BinTree *all_removing_cases(BinTree **root, int id) {
    /*
     * base case
     */
    if ((*root) == NULL) {
        return (*root);
    }
    BinTree *tmp;
    /*
     * check if the desired person is on the left or the right or even the root
     */
    if (id > (*root)->id) {
        (*root)->right = all_removing_cases(&(*root)->right, id);
    } else if (id < (*root)->id) {
        (*root)->left = all_removing_cases(&(*root)->left, id);
    } else {
        /*
         * check if desired person has sons or not
         * now I check if he has 1 son or none
         */
        if ((*root)->right == NULL) {
            tmp = (*root)->left;
            free((*root)->name);
            free((*root));
            //(*root) = NULL;
            return tmp;
        } else if ((*root)->left == NULL) {
            tmp = (*root)->right;
            free((*root)->name);
            free((*root));
            //(*root) = NULL;
            return tmp;
        }
        /*
         * in case of finding out that the person has 2 sons (subtree)
         * I search for the smallest between the id's that are greater than
         * the desired person, after that I replace him with the removed person
         */
        BinTree *switching_tmp = lowest_id_with_one_step_right((*root)->right);
        strcpy((*root)->name, switching_tmp->name);
        (*root)->action = (*root)->name;
        (*root)->age = switching_tmp->age;
        (*root)->gender = switching_tmp->gender;
        (*root)->id = switching_tmp->id;
        (*root)->task = switching_tmp->task;

        (*root)->right = all_removing_cases(&(*root)->right,
                                            switching_tmp->id);
    }
    return (*root);
}
/************************************************************************
 * The Input: binary tree pointer *
 * The output: a node from the binary tree *
 * The Function operation: goes all the way down to the left son,
 * when I find him I return him back *
 *************************************************************************/
BinTree *lowest_id_with_one_step_right(BinTree *root) {
    BinTree *ptr = root;

    while (ptr && ptr->left != NULL) {
        ptr = ptr->left;
    }
    return ptr;
}
/************************************************************************
 * The Input: a void pointer *
 * The output: the sum of the nodes in the binary tree *
 * The Function operation: goes throw all the binary tree,
 * whenever I find a node, I return +1, otherwise add to the sum 0 *
 *************************************************************************/
int size_of_all_jews(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return 0;
    }
    return size_of_all_jews(Root->left) + size_of_all_jews(Root->right) + 1;
}
/************************************************************************
 * The Input: a void pointer *
 * The output: the sum of all the males in the binary tree *
 * The Function operation: search through the binary tree to find males,
 * if found add to the sum 1, otherwise I add 0 to the sum *
 *************************************************************************/
int size_of_male_jews(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return 0;
    }
    if (Root->gender == 'M') {
        return size_of_male_jews(Root->left) +
               size_of_male_jews(Root->right) + 1;
    } else {
        return size_of_male_jews(Root->left) + size_of_male_jews(Root->right);
    }
}
/************************************************************************
 * The Input: a void pointer *
 * The output: the sum of the females in binary tree *
 * The Function operation: search through the binary tree to find females,
 * if found add to the sum 1, otherwise I add 0 to the sum *
 *************************************************************************/
int size_of_female_jews(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return 0;
    }
    if (Root->gender == 'F') {
        return size_of_female_jews(Root->left) +
               size_of_female_jews(Root->right) + 1;
    } else {
        return size_of_female_jews(Root->left) +
               size_of_female_jews(Root->right);
    }
}
/************************************************************************
 * The Input: a void pointer *
 * The output: no output *
 * The Function operation: add 20 years to every person in the tree *
 *************************************************************************/
void getting_old_in_age(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return;
    }
    getting_old_in_age(Root->left);
    getting_old_in_age(Root->right);
    Root->age += 20;
}
/************************************************************************
 * The Input: a void pointer *
 * The output: return 1 if male "Moshe' was found, otherwise return 0 *
 * The Function operation: searches in the binary tree to find male "Moshe"
 * if found I return 1, otherwise return 0 *
 *************************************************************************/
int check_moshe_if_exist(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return 0;
    }
    if (Root->gender == 'M' && strcmp(Root->name, "Moshe") == 0) {
        return 1;
    }
    return check_moshe_if_exist(Root->left) +
           check_moshe_if_exist(Root->right);
}
/************************************************************************
 * The Input: a void pointer *
 * The output: prints the task of every person in the binary tree *
 * The Function operation: goes all over the binary tree and prints every
 * person's task, using pre_order search *
 *************************************************************************/
void find_moshe_pre_order(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return;
    }
    Root->task(Root->action);
    find_moshe_pre_order(Root->left);
    find_moshe_pre_order(Root->right);
}
/************************************************************************
 * The Input: a void pointer *
 * The output: prints the task of every person in the binary tree *
 * The Function operation: goes all over the binary tree and prints every
 * person's task, using in_order search *
 *************************************************************************/
void find_moshe_in_order(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return;
    }
    find_moshe_in_order(Root->left);
    Root->task(Root->action);
    find_moshe_in_order(Root->right);
}
/************************************************************************
 * The Input: a void pointer *
 * The output: prints the task of every person in the binary tree *
 * The Function operation: goes all over the binary tree and prints every
 * person's task, using post_order search *
 *************************************************************************/
void find_moshe_post_order(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return;
    }
    find_moshe_post_order(Root->left);
    find_moshe_post_order(Root->right);
    Root->task(Root->action);
}
/************************************************************************
 * The Input: a void pointer *
 * The output: prints the id, name, gender and age of every person
 * in the binary tree *
 * The Function operation: goes all over the binary tree and prints every
 * person's id, name, gender and age, using pre_order search *
 *************************************************************************/
void print_pre_order(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL)
        return;
    printf("id: %d, name: %s, gender: %c, age: %d\n",
           Root->id, Root->name, Root->gender, Root->age);
    print_pre_order(Root->left);
    print_pre_order(Root->right);
}
/************************************************************************
 * The Input: a void pointer *
 * The output: prints the id, name, gender and age of every person
 * in the binary tree *
 * The Function operation: goes all over the binary tree and prints every
 * person's id, name, gender and age, using post_order search *
 *************************************************************************/
void print_post_order(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return;
    }
    print_post_order(Root->left);
    print_post_order(Root->right);
    printf("id: %d, name: %s, gender: %c, age: %d\n",
           Root->id, Root->name, Root->gender, Root->age);
}
/************************************************************************
 * The Input: a void pointer *
 * The output: prints the id, name, gender and age of every person
 * in the binary tree *
 * The Function operation: goes all over the binary tree and prints every
 * person's id, name, gender and age, using in_order search *
 *************************************************************************/
void print_in_order(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL)
        return;
    print_in_order(Root->left);
    printf("id: %d, name: %s, gender: %c, age: %d\n",
           Root->id, Root->name, Root->gender, Root->age);
    print_in_order(Root->right);
}
/************************************************************************
 * The Input: a void pointer *
 * The output: returns 1 and prints phrase if male "Moshe" ages more than
 * 80 years, otherwise return 0 *
 * The Function operation: search in the tree to find male "Moshe"
 * if he ages more than 80 years old, I return 1, otherwise return 0 *
 *************************************************************************/
int moshe_too_old(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return 0;
    }
    if (Root->age >= 80 && Root->gender == 'M' &&
        strcmp("Moshe", Root->name) == 0) {
        printf("%s say: let my people go!\n", Root->name);
        return 1;
    }
    return moshe_too_old(Root->right) + moshe_too_old(Root->left);
}
/************************************************************************
 * The Input: a void pointer *
 * The output: no output *
 * The Function operation: free every node in the binary tree *
 *************************************************************************/
void freeIsrael(void *root) {
    BinTree *Root = (BinTree *)root;
    if (Root == NULL) {
        return;
    }
    freeIsrael(Root->left);
    freeIsrael(Root->right);
    free(Root->name);
    free(Root);
    Root = NULL;
}