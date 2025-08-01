#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_NAME_LENGTH 11
#define MAX_PHONE_LENGTH 11
#define ALPHABET_SIZE 26
#define NEW_CONTACT_CASE 1
#define DEFAULT_CONTACT_CASE 2
#define SEARCH_CONTACT_BY_PHONE_NUMBER_CASE 3
#define SEARCH_CONTACT_BY_FULL_NAME_CASE 4
#define EDIT_CONTACT_CASE 5
#define DISPLAY_PHONEBOOK_CASE 6
#define EXIT_CASE 7

typedef struct Contact {
    char *firstName;
    char *lastName;
    char *phoneNum;
    struct Contact *next;
} Contact;

void printMenu();
void newContact(Contact *phoneBook[ALPHABET_SIZE]);
void deleteContact(Contact *phoneBook[ALPHABET_SIZE]);
void searchContactByPhoneNumber(Contact *phoneBook[ALPHABET_SIZE]);
void searchContactByFullName(Contact *phoneBook[ALPHABET_SIZE]);
void editContact(Contact *phoneBook[ALPHABET_SIZE]);
int findContactByName(Contact *phoneBook[ALPHABET_SIZE],
                      char firstName[MAX_NAME_LENGTH], char lastName[MAX_NAME_LENGTH]);
int findContactByNumber(Contact *phoneBook[ALPHABET_SIZE], char number[MAX_PHONE_LENGTH]);
void displayPhonebook(Contact *phoneBook[ALPHABET_SIZE]);
void freeContacts(Contact *phoneBook[ALPHABET_SIZE]);

int main() {
    Contact *phoneBook[ALPHABET_SIZE];

    int choice;
    int menu = 1;

    for (int i = 0; i < ALPHABET_SIZE; ++i) {
        phoneBook[i] = NULL;
    }

    while (1) {
        if (menu) {
            printMenu();
        }

        scanf("%d", &choice);
        menu = 1;
        switch (choice) {
        case NEW_CONTACT_CASE:
            newContact(phoneBook);
            break;
        case DEFAULT_CONTACT_CASE:
            deleteContact(phoneBook);
            break;
        case SEARCH_CONTACT_BY_PHONE_NUMBER_CASE:
            searchContactByPhoneNumber(phoneBook);
            break;
        case SEARCH_CONTACT_BY_FULL_NAME_CASE:
            searchContactByFullName(phoneBook);
            break;
        case EDIT_CONTACT_CASE:
            editContact(phoneBook);
            break;
        case DISPLAY_PHONEBOOK_CASE:
            displayPhonebook(phoneBook);
            break;
        case EXIT_CASE:
            printf("Bye!");
            freeContacts(phoneBook);
            return 0;
        default:
            printf("Wrong option, try again:\n");
            menu = 0;
            continue;
        }
    }

    return 0;
}

void printMenu() {
    printf("Welcome to the phone book manager! \n"
           "Choose an option:\n"
           "1. Add a new contact to the phone book.\n"
           "2. Delete a contact from the phone book.\n"
           "3. Find a contact in the phone book by phone number.\n"
           "4. Find a contact in the phone book by name.\n"
           "5. Update phone number for a contact.\n"
           "6. Print phone book.\n"
           "7. Exit.\n");
}
/************************************************************************
 * The Input: the phonebook and the first and last name of the new contact *
 * The output: returns 1 if we found a contact
 * who have same first and last name, otherwise return 0 *
 * The Function operation: takes the first and last name of the given contact
 * and search in the phonebook to find the contact, takes first and last name
 * of every contact and compare it with the given name,
 * if we found a contact with the same name in the phonebook return 1,
 * otherwise return 0 *
 *************************************************************************/
int findContactByName(Contact *phoneBook[ALPHABET_SIZE],
                      char firstName[MAX_NAME_LENGTH],
                      char lastName[MAX_NAME_LENGTH]) {
    int index = lastName[0] - 'A';
    if (index < 0 || index >= ALPHABET_SIZE) {
        return 0;
    }

    Contact *current = phoneBook[index];

    while (current != NULL) {
        if (strcmp(firstName, current->firstName) == 0 &&
            strcmp(lastName, current->lastName) == 0) {
            return 1;
        }
        current = current->next;
    }

    return 0;
}
/************************************************************************
 * The Input: the phonebook and the phone number of the new contact *
 * The output: returns 1 if we found a contact
 * who have same phone number, otherwise return 0 *
 * The Function operation: takes the phone number of the given contact
 * and search in the phonebook to find the contact, takes phone number
 * of every contact and compare it with the given phone number,
 * if we found a contact with the same phone number in the phonebook return 1,
 * otherwise return 0 *
 *************************************************************************/
int findContactByNumber(Contact *phoneBook[ALPHABET_SIZE],
                        char phoneNumber[MAX_PHONE_LENGTH]) {
    for (int i = 0; i < ALPHABET_SIZE; ++i) {
        Contact *current = phoneBook[i];
        while (current != NULL) {
            if (strcmp(phoneNumber, current->phoneNum) == 0) {
                return 1;
            }
            current = current->next;
        }
    }
    return 0;
}
/************************************************************************
 * The Input: the phonebook *
 * The output: just prints the contacts in the phonebook *
 * The Function operation: goes all over the phonebook and prints every contact*
 *************************************************************************/
void displayPhonebook(Contact *phoneBook[ALPHABET_SIZE]) {
    for (int i = 0; i < ALPHABET_SIZE; ++i) {
        Contact *current = phoneBook[i];
        while (current != NULL) {
            printf("%s %s %s\n",
                   current->firstName, current->lastName, current->phoneNum);
            current = current->next;
        }
    }
}
/************************************************************************
 * The Input: all contact details *
 * The output: just adds another node to a specified list in phonebook *
 * The Function operation: takes the given contact details,
 * check if they are already in the phonebook, if not,
 * add the contact to the phonebook, otherwise I don't add the number *
 *************************************************************************/
void newContact(Contact *phoneBook[ALPHABET_SIZE]) {
    printf("Enter a contact details "
           "(<first name> <last name> <phone number>): ");

    Contact *newContact = (Contact *)malloc(sizeof(Contact));
    if (newContact == NULL) {
        printf("The addition of the contact has failed!\n");
        return;
    }

    newContact->firstName = malloc(MAX_NAME_LENGTH);
    newContact->lastName = malloc(MAX_NAME_LENGTH);
    newContact->phoneNum = malloc(MAX_PHONE_LENGTH);

    if (!newContact->firstName || !newContact->lastName || !newContact->phoneNum) {
        printf("The addition of the contact has failed!\n");
        free(newContact->firstName);
        free(newContact->lastName);
        free(newContact->phoneNum);
        free(newContact);
        return;
    }

    if (scanf("%s %s %s",
              newContact->firstName,
              newContact->lastName,
              newContact->phoneNum) != 3) {
        printf("The addition of the contact has failed!\n");
        free(newContact->firstName);
        free(newContact->lastName);
        free(newContact->phoneNum);
        free(newContact);
        return;
    }

    newContact->next = NULL;

    if (findContactByName(phoneBook, newContact->firstName, newContact->lastName)) {
        printf("The addition of the contact has failed,"
               " since the contact %s %s already exists!\n",
               newContact->firstName, newContact->lastName);
        free(newContact->firstName);
        free(newContact->lastName);
        free(newContact->phoneNum);
        free(newContact);
        return;
    }

    if (findContactByNumber(phoneBook, newContact->phoneNum)) {
        printf("The addition of the contact has failed,"
               " since the phone number %s already exists!\n",
               newContact->phoneNum);
        free(newContact->firstName);
        free(newContact->lastName);
        free(newContact->phoneNum);
        free(newContact);
        return;
    }

    int index = newContact->lastName[0] - 'A';
    if (index < 0 || index >= ALPHABET_SIZE) {
        printf("The addition of the contact has failed!\n");
        free(newContact->firstName);
        free(newContact->lastName);
        free(newContact->phoneNum);
        free(newContact);
        return;
    }

    // insert at beginning of list
    newContact->next = phoneBook[index];
    phoneBook[index] = newContact;

    printf("The contact has been added successfully!!\n");
}
/************************************************************************
 * The Input: first and last name of a contact *
 * The output: no output, just delete a contact (if found) *
 * The Function operation: check the given name is in the phonebook,
 * if found, ask one more time if the user is sure to delete the contact
 * if he input 'y', I delete the contact from the phonebook *
 *************************************************************************/
void deleteContact(Contact *phoneBook[ALPHABET_SIZE]) {
    printf("Enter a contact name (<first name> <last name>): ");
    char firstName[MAX_NAME_LENGTH];
    char lastName[MAX_NAME_LENGTH];

    if (scanf("%s %s", firstName, lastName) != 2) {
        printf("The deletion of the contact has failed!\n");
        return;
    }

    int index = lastName[0] - 'A';
    if (index < 0 || index >= ALPHABET_SIZE) {
        printf("Invalid last name initial.\n");
        return;
    }

    Contact *current = phoneBook[index];
    Contact *prev = NULL;

    while (current != NULL) {
        if (strcmp(firstName, current->firstName) == 0 &&
            strcmp(lastName, current->lastName) == 0) {
            printf("Are you sure? (y/n) ");

            char confirm;
            scanf(" %c", &confirm);
            if (confirm != 'y' && confirm != 'Y') {
                printf("The deletion of the contact has been canceled.\n");
                return;
            }

            if (prev == NULL) {
                phoneBook[index] = current->next;
            } else {
                prev->next = current->next;
            }
            free(current->firstName);
            free(current->lastName);
            free(current->phoneNum);
            free(current);
            printf("The contact has been deleted successfully!\n");
            return;
        }

        prev = current;
        current = current->next;
    }

    printf("The deletion of the contact has failed!\n");
}
/************************************************************************
 * The Input: a given phone number *
 * The output: prints if the contact was found or not *
 * The Function operation: goes throw all the phonebook searching every contact
 * until I find the desired number, if found prints the first and last name
 * of the contact, if no one has the number, I print no such a contact with
 * the given phone number *
 *************************************************************************/
void searchContactByPhoneNumber(Contact *phoneBook[ALPHABET_SIZE]) {
    printf("Enter a phone number: ");
    char phoneNumber[MAX_PHONE_LENGTH];
    scanf("%s", phoneNumber);

    for (int i = 0; i < ALPHABET_SIZE; ++i) {
        Contact *current = phoneBook[i];
        while (current != NULL) {
            if (strcmp(phoneNumber, current->phoneNum) == 0) {
                printf("The following contact was found: %s %s %s\n",
                       current->firstName, current->lastName, current->phoneNum);
                return;
            }
            current = current->next;
        }
    }

    printf("No contact with a phone number %s was found in the phone book\n",
           phoneNumber);
}
/************************************************************************
 * The Input: first and last name of a contact *
 * The output: just prints the desired contact detail (if found) *
 * The Function operation: goes throw the phonebook and search ofr the contact
 * who has a name as same as the given name, prints the contact details
 * if found, otherwise print no such contact with that name *
 *************************************************************************/
void searchContactByFullName(Contact *phoneBook[ALPHABET_SIZE]) {
    printf("Enter a contact name (<first name> <last name>): ");
    char firstName[MAX_NAME_LENGTH];
    char lastName[MAX_NAME_LENGTH];
    if (scanf("%s %s", firstName, lastName) != 2) {
        printf("Invalid input format.\n");
        return;
    }

    int index = lastName[0] - 'A';
    if (index < 0 || index >= ALPHABET_SIZE) {
        printf("No contact with a name %s %s was found in the phone book\n", firstName, lastName);
        return;
    }

    Contact *current = phoneBook[index];
    while (current != NULL) {
        if (strcmp(firstName, current->firstName) == 0 &&
            strcmp(lastName, current->lastName) == 0) {
            printf("The following contact was found: %s %s %s\n",
                   current->firstName, current->lastName, current->phoneNum);
            return;
        }
        current = current->next;
    }
    printf("No contact with a name %s %s was found in the phone book\n",
           firstName, lastName);
}
/************************************************************************
 * The Input: first and last name *
 * The output: just changes the desired contact phone number *
 * The Function operation: first of all, check if the given name is in
 * the phonebook, if found I scan the desired phone number then check if
 * someone has that number before, if everything is ok
 * (the name of the contact in the phonebook and the given phone number
 * isn't taken) then I change the old number with a new one*
 *************************************************************************/
void editContact(Contact *phoneBook[ALPHABET_SIZE]) {
    printf("Enter a contact name (<first name> <last name>): ");
    char firstName[MAX_NAME_LENGTH];
    char lastName[MAX_NAME_LENGTH];

    if (scanf("%s %s", firstName, lastName) != 2) {
        printf("Invalid input format.\n");
        return;
    }

    int index = lastName[0] - 'A';
    if (index < 0 || index >= ALPHABET_SIZE) {
        printf("Contact not found.\n");
        return;
    }

    if (!findContactByName(phoneBook, firstName, lastName)) {
        printf("No contact with a name %s %s was found in the phone book\n",
               firstName, lastName);
        return;
    }

    Contact *current = phoneBook[index];
    while (current != NULL) {
        if (strcmp(firstName, current->firstName) == 0 &&
            strcmp(lastName, current->lastName) == 0) {
            printf("The following contact was found: %s %s %s\n",
                   current->firstName, current->lastName, current->phoneNum);

            printf("Enter the new phone number: ");
            char newPhone[MAX_PHONE_LENGTH];
            scanf("%s", newPhone);

            if (findContactByNumber(phoneBook, newPhone)) {
                printf("The update of the contact has failed, "
                       "since the phone number %s already exists!\n",
                       newPhone);
                return;
            }

            strncpy(current->phoneNum, newPhone, MAX_PHONE_LENGTH - 1);
            current->phoneNum[MAX_PHONE_LENGTH - 1] = '\0';
            printf("The contact has been updated successfully!\n");
            return;
        }
        current = current->next;
    }
}
/************************************************************************
 * The Input: the phonebook *
 * The output: no output, just free the memory *
 * The Function operation: goes all over the phonebook and free the memory
 * of every contact *
 *************************************************************************/
void freeContacts(Contact *phoneBook[ALPHABET_SIZE]) {
    for (int i = 0; i < ALPHABET_SIZE; ++i) {
        Contact *current = phoneBook[i];
        while (current != NULL) {
            Contact *toFree = current;
            current = current->next;

            free(toFree->firstName);
            free(toFree->lastName);
            free(toFree->phoneNum);
            free(toFree);
        }
        phoneBook[i] = NULL;
    }
}
