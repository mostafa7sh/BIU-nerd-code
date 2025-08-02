#include "BinTree.h"
#include <stdio.h>

int main() {
    BinTree *root = NULL;
    int choice;

    while (1) {
        generic_function((BinTree *)&root,
                         (void (*)(void *))remove_120_years_old);

        printf("please choose action:\n"
               "(1) A child was born\n"
               "(2) Throw into the Nile\n"
               "(3) Find Moshe\n"
               "(4) Print All\n"
               "(5) Size of jews\n"
               "(6) Exit\n");
        scanf("%d", &choice);
        switch (choice) {
        case 1:
            generic_function(root, getting_old_in_age);
            if (generic_int_function(root, moshe_too_old)) {
                generic_function(root, freeIsrael);
                return 0;
            }
            child_birth(&root);
            printf("\n");
            break;
        case 2:
            while (generic_int_function(root, check_if_male_exist) != 0) {
                generic_function((BinTree *)&root,
                                 (void (*)(void *))remove_all_males);
            }

            generic_function(root, getting_old_in_age);
            if (generic_int_function(root, moshe_too_old)) {
                generic_function(root, freeIsrael);
                return 0;
            }
            printf("\n");
            break;
        case 3:
            printf("please choose order:\n"
                   "(1) preorder\n"
                   "(2) inorder\n"
                   "(3) postorder\n");
            int mosheOrder;
            scanf("%d", &mosheOrder);
            switch (mosheOrder) {
            case 1:
                if (!generic_int_function(root, check_moshe_if_exist)) {
                    generic_function(root, find_moshe_pre_order);
                    printf("Moshe Not Found!\n");
                } else {
                    generic_function(root, find_moshe_pre_order);
                    while (generic_int_function(root, check_if_male_exist) != 0) {
                        generic_function((BinTree *)&root,
                                         (void (*)(void *))remove_all_males);
                    }
                }
                break;
            case 2:
                if (!generic_int_function(root, check_moshe_if_exist)) {
                    generic_function(root, find_moshe_in_order);
                    printf("Moshe Not Found!\n");
                } else {
                    generic_function(root, find_moshe_in_order);
                    while (generic_int_function(root, check_if_male_exist) != 0) {
                        generic_function((BinTree *)&root,
                                         (void (*)(void *))remove_all_males);
                    }
                }
                break;
            case 3:
                if (!generic_int_function(root, check_moshe_if_exist)) {
                    generic_function(root, find_moshe_post_order);
                    printf("Moshe Not Found!\n");
                } else {
                    generic_function(root, find_moshe_post_order);
                    while (generic_int_function(root, check_if_male_exist) != 0) {
                        generic_function((BinTree *)&root,
                                         (void (*)(void *))remove_all_males);
                    }
                }
                break;
            default:
                printf("wrong order!\n");
            }

            generic_function(root, getting_old_in_age);
            if (generic_int_function(root, moshe_too_old)) {
                generic_function(root, freeIsrael);
                return 0;
            }
            printf("\n");
            break;
        case 4:
            printf("please choose order:\n"
                   "(1) preorder\n"
                   "(2) inorder\n"
                   "(3) postorder\n");
            int printingChoice;
            scanf("%d", &printingChoice);

            switch (printingChoice) {
            case 1:
                generic_function(root, print_pre_order);
                break;
            case 2:
                generic_function(root, print_in_order);
                break;
            case 3:
                generic_function(root, print_post_order);
                break;
            default:
                printf("wrong order!\n");
            }
            generic_function(root, getting_old_in_age);
            if (generic_int_function(root, moshe_too_old)) {
                generic_function(root, freeIsrael);
                return 0;
            }
            printf("\n");
            break;
        case 5:
            printf("please choose what you want to calculate:\n"
                   "(1) All jews\n"
                   "(2) Male\n"
                   "(3) Female\n");
            int calculatingChoice;
            scanf("%d", &calculatingChoice);

            switch (calculatingChoice) {
            case 1:
                printf("size of all jews is: %d\n",
                       generic_int_function(root, size_of_all_jews));
                break;
            case 2:
                printf("size of all male jews is: %d\n",
                       generic_int_function(root, size_of_male_jews));
                break;
            case 3:
                printf("size of all female jews is: %d\n",
                       generic_int_function(root, size_of_female_jews));
                break;
            default:
                printf("wrong order!\n");
            }
            
            generic_function(root, getting_old_in_age);
            if (generic_int_function(root, moshe_too_old)) {
                generic_function(root, freeIsrael);
                return 0;
            }

            printf("\n");
            break;
        case 6:
            generic_function(root, freeIsrael);
            return 0;
        default:
            printf("wrong choose please choose again\n");
            generic_function(root, getting_old_in_age);
            if (generic_int_function(root, moshe_too_old)) {
                generic_function(root, freeIsrael);
                return 0;
            }
            printf("\n");
        }
    }
}