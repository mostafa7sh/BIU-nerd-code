#include <stdio.h>

#define CASE_CONVERSION 32

void printMenu();
void defaultMenu();
int mainSupporter();
int support(int leftSide, char operation, int not);
void DigitCounter();
int DigitCounter_Recursive(int number, int digit);
void GCDCalculator();
int GCDF(int num1, int num2);

int main() {
    char option = 0;
    char enter = 0;
    char end = 0;
    do {
        printMenu();

        if (end == '4') {
            option = '4';
        }
        if (option != '1' && end != '4') {
            scanf(" %c", &option);
        }

        switch (option) {
        case '1':
            printf("\nPlease write your logical statement: ");
            char space;
            scanf("%c", &space);

            int theOtherPharse, firstPharse;

            firstPharse = mainSupporter();
            scanf("%c", &enter);

            while (enter != '\n' && firstPharse != 10) {

                if (enter == '&') {
                    theOtherPharse = mainSupporter();
                    if (theOtherPharse == 10) {
                        firstPharse = 10;
                        break;
                    }
                    firstPharse = firstPharse & theOtherPharse;
                }

                if (enter == '|') {
                    theOtherPharse = mainSupporter();
                    if (theOtherPharse == 10 || firstPharse == 10) {
                        firstPharse = 10;
                        break;
                    }
                    firstPharse = firstPharse | theOtherPharse;
                }
                scanf("%c", &enter);

                if (enter == '4') {
                    end = enter;
                    enter = 0;
                    break;
                }
                if (enter == '1') {
                    break;
                }
            }

            if (firstPharse == 0) {
                printf("The statement is false.\n");
                option = enter;
                break;
            }
            if (firstPharse == 1) {
                printf("The statement is true.\n");
                option = enter;
                break;
            }
            if (firstPharse == 10 || theOtherPharse == 10) {
                printf("You have a syntax error in your statement.\n");
                option = enter;
                break;
            }
            break;

        case '2':
            DigitCounter();
            break;
        case '3':
            GCDCalculator();
            break;
        case '4':
            printf("So Long, and Thanks for All the Fish!\n");
            break;
        default:
            defaultMenu();
            break;
        }
    } while (option != '4');
    return 0;
}
/************************************************************************
 * The Function operation: print main menu *
 *************************************************************************/
void printMenu() {
    printf("Choose an option:\n"
           "\t1: Logical Statement Calculator\n"
           "\t2: Count digit in a number\n"
           "\t3: GCD Calculator\n"
           "\t4: Exit\n");
}
/************************************************************************
 * The Function operation: print default menu *
 *************************************************************************/
void defaultMenu() {
    printf("Fool of a Took!\n"
           "This is a serious journey, "
           "not a hobbit walking-party.\n"
           "Throw yourself in next time, "
           "and then you will be no further nuisance.\n");
}
/************************************************************************
 * The output: 10 if syntax wrong, 1 if expression is true, 0 otherwise *
 * The Function operation: validating sytanx and evaluating expression using
 * support function then returning the output *
 *************************************************************************/
int mainSupporter() {
    char input, leftOperand, checker = 0;
    int not = 0, result;
    int leftOperandNum;
    scanf("%c", &input);

    if (input != '~' && input != '(') {
        do {
            scanf("%c", &input);
        } while (input != ')' && input != ' ');
        return 10;
    }

    if (input == '~') {
        scanf("%c", &input);
        not = 1;
    }

    scanf("%c", &leftOperand);
    if (leftOperand <= '/' || (leftOperand >= ':' && leftOperand <= '@') ||
        (leftOperand >= '[' && leftOperand <= '`') || leftOperand >= '{') {
        do {
            scanf("%c", &input);
        } while (input != ')' && input != ' ');
        return 10;
    }

    if (leftOperand >= '0' && leftOperand <= '9') {
        leftOperandNum = leftOperand - 48;
        scanf("%c", &checker);
        while (checker >= '0' && checker <= '9') {
            checker -= 48;
            leftOperandNum = leftOperandNum * 10 + checker;
            scanf("%c", &checker);
        }
    }

    if ((leftOperand >= 'A' && leftOperand <= 'Z') ||
        (leftOperand >= 'a' && leftOperand <= 'z')) {
        if (leftOperand >= 'A' && leftOperand <= 'Z') {
            leftOperand += CASE_CONVERSION;
        }
        leftOperandNum = leftOperand + 0;
        scanf("%c", &checker);
    }

    if (checker != '>' && checker != '<' && checker != '=') {
        do {
            scanf("%c", &input);
        } while (input != ')' && input != ' ');
        return 10;
    }
    result = support(leftOperandNum, checker, not);
    return result;
}
/************************************************************************
 * The Input: left side of the logical expression, the operation and ~ (not) *
 * The output: 10 if syntax wrong, 1 if expression is true, 0 otherwise *
 * The Function operation: Reads the right side of a logical expression and evaluates it *
 *************************************************************************/
int support(int leftSide, char operation, int not) {
    char rightOperand;
    int rightOperandNum;
    char checking = 0;
    char destroy;
    scanf("%c", &rightOperand);

    if (rightOperand <= '/' || (rightOperand >= ':' && rightOperand <= '@') ||
        (rightOperand >= '[' && rightOperand <= '`') || rightOperand >= '{') {
        do {
            scanf("%c", &destroy);
        } while (destroy != ')' && destroy != ' ');
        return 10;
    }

    if (rightOperand >= '0' && rightOperand <= '9') {
        rightOperandNum = rightOperand - 48;
        scanf("%c", &checking);
        while (checking >= '0' && checking <= '9') {
            checking -= 48;
            rightOperandNum = rightOperandNum * 10 + checking;
            scanf("%c", &checking);
        }
    }

    if ((rightOperand >= 'A' && rightOperand <= 'Z') ||
        (rightOperand >= 'a' && rightOperand <= 'z')) {
        if (rightOperand >= 'A' && rightOperand <= 'Z') {
            rightOperand += CASE_CONVERSION;
        }
        rightOperandNum = rightOperand + 0;
        scanf("%c", &checking);
    }

    if (checking != ')') {
        return 10;
    }

    if (not == 1) {
        if (operation == '=') {
            return leftSide != rightOperandNum;
        }
        if (operation == '>') {
            return leftSide <= rightOperandNum;
        }
        if (operation == '<') {
            return leftSide >= rightOperandNum;
        }
    }

    if (not == 0) {
        if (operation == '=') {
            return (leftSide == rightOperandNum);
        }
        if (operation == '>') {
            return (leftSide > rightOperandNum);
        }
        if (operation == '<') {
            return (leftSide < rightOperandNum);
        }
    }
}
/************************************************************************
 * The Function operation: simply swap the numbers if needed (place the smaller
 * number as first argument) the we call GCDF function *
 *************************************************************************/
void GCDCalculator() {
    int num1, num2, temp;
    printf("\nEnter two positive numbers: ");
    scanf("%d %d", &num1, &num2);

    if (num1 < 0 || num2 < 0) {
        printf("You should stay positive, "
               "and so should your input.\n");
    } else {
        if (num2 > num1) {
            temp = num1;
            num1 = num2;
            num2 = temp;
        }
        printf("GCD = %d\n", GCDF(num1, num2));
    }
}
/************************************************************************
 * The Input: two numbers *
 * The output: greatest common divisor *
 * The Function operation: calculates greatest common divisor of two numbers
 * recursively *
 *************************************************************************/
int GCDF(int num1, int num2) {
    if (num2 == 0) {
        return num1;
    } else {
        printf("%d*%d+%d = %d (num1=%d, num2=%d)\n",
               num2, num1 / num2, num1 % num2, num1, num1, num2);
        return GCDF(num2 % num1, num1 % num2);
    }
}
/************************************************************************
 * The Function operation: a helper function that gets input from user a
 * number and a digit then prints how many times did that digit appeared in
 * the number *
 *************************************************************************/
void DigitCounter() {
    printf("\nEnter a number and a digit: ");
    int number, digit;
    scanf("%d %d", &number, &digit);

    if ((number < 0 || digit < 0) || digit >= 10) {
        printf("You should stay positive"
               ", and so should your input.\n");
    } else if (number == 0 && digit == 0) {
        printf("The digit 0 appears 1 times in the number 0\n");
    } else {
        printf("The digit %d appears %d times in the number %d\n",
               digit, DigitCounter_Recursive(number, digit), number);
    }
}
/************************************************************************
 * The Input: a number and a digit *
 * The output: how many times did the digit appear in the number *
 * The Function operation: counts how many times the digit appear in the
 * number, recursively *
 *************************************************************************/
int DigitCounter_Recursive(int number, int digit) {
    if (number == 0) {
        return 0;
    }
    if (number % 10 == digit) {
        return DigitCounter_Recursive(number / 10, digit) + 1;
    } else {
        return DigitCounter_Recursive(number / 10, digit);
    }
}
