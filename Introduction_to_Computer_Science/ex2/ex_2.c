#include <math.h>
#include <stdio.h>

#define NUMBER_ASCII 48
#define CHAR_ASCII 65
#define OCTAL_TO_HEX_CASE 1
#define BINARY_ADDITION_CASE 2
#define PRINT_HI_CASE 3
#define COUNT_BITS_CASE 4
#define DECIMAL_TO_BINARY_CASE 5
#define ZIG_ZAG_CASE 6
#define EXIT_CASE 7

void printMenu();
void octalToHex();
void binaryAddition();
void printHI();
void countBits();
void decimalToBinary();
void zigZag();

int main() {
    int option;

    do {
        printMenu();
        scanf("%d", &option);

        switch (option) {
        case OCTAL_TO_HEX_CASE:
            octalToHex();
            break;
        case BINARY_ADDITION_CASE:
            binaryAddition();
            break;
        case PRINT_HI_CASE:
            printHI();
            break;
        case COUNT_BITS_CASE:
            countBits();
            break;
        case DECIMAL_TO_BINARY_CASE:
            decimalToBinary();
            break;
        case ZIG_ZAG_CASE:
            zigZag();
            break;
        case EXIT_CASE:
            printf("Bye!");
            break;
        default:
            printf("Invalid option!\n");
            break;
        }
    } while (option != 7);
    return 0;
}

void printMenu() {
    printf("Choose an option:\n"
           "1. octal to hex\n"
           "2. binary addition\n"
           "3. print HI\n"
           "4. count bits\n"
           "5. decimal to binary\n"
           "6. Zig-Zag bits\n"
           "7. exit\n");
}

void octalToHex() {
    int octal, tempOctal, isValid = 1;
    printf("Please enter number in octal base: ");
    scanf("%d", &octal);

    // check if input is a valid octal number
    tempOctal = octal;
    while (tempOctal != 0) {
        if (tempOctal % 10 >= 8) {
            isValid = 0;
            break;
        }
        tempOctal /= 10;
    }

    if (!isValid) {
        printf("Invalid input!\n");
        return;
    }

    // reverse the octal digits
    int reversedOctal = 0, digit;
    tempOctal = octal;
    while (tempOctal > 0) {
        digit = tempOctal % 10;
        reversedOctal = reversedOctal * 10 + digit;
        tempOctal /= 10;
    }

    // convert octal to binary
    unsigned long long binary = 0;
    int bitPos = 0;
    while (reversedOctal > 0) {
        int octDigit = reversedOctal % 10;
        for (int i = 0; i < 3; ++i) {
            int bit = octDigit % 2;
            binary += bit * (unsigned long long)pow(10, bitPos++);
            octDigit /= 2;
        }
        reversedOctal /= 10;
    }

    // calculate number of binary bits
    int totalBits = bitPos;
    int hexShift = ((totalBits + 3) / 4) * 4; // round up to nearest multiple of 4

    // convert binary to hexadecimal
    while (hexShift > 0) {
        hexShift -= 4;
        unsigned long long group = (binary / (unsigned long long)pow(10, hexShift)) % 10000;

        int value = 0, power = 0;
        while (group > 0) {
            value += (group % 10) * (int)pow(2, power++);
            group /= 10;
        }

        if (value < 10)
            printf("%c", NUMBER_ASCII + value);
        else
            printf("%c", CHAR_ASCII + (value - 10));
    }
    printf("\n");
}

void binaryAddition() {
    unsigned long long binary1, binary2;
    double binaryResult = 0;
    int A, B, Cin = 0, Cout = 0, S;
    int cnt11 = 0, cnt22 = 0;
    int counterChecker1 = 1, counterChecker2 = 1;
    int limit;

    printf("Please enter two binary numbers: ");
    scanf("%llu", &binary1);
    scanf("%llu", &binary2);

    unsigned long long checkBinary1 = binary1;
    unsigned long long checkBinary2 = binary2;
    unsigned long long binary11 = binary1;
    unsigned long long binary22 = binary2;

    // validate binary1 and count digits
    while (checkBinary1 != 0) {
        int bit = checkBinary1 % 10;
        if (bit != 0 && bit != 1) {
            cnt11++;
        }
        counterChecker1++;
        checkBinary1 /= 10;
    }

    // validate binary2 and count digits
    while (checkBinary2 != 0) {
        int bit = checkBinary2 % 10;
        if (bit != 0 && bit != 1) {
            cnt22++;
        }
        counterChecker2++;
        checkBinary2 /= 10;
    }

    // determine the limit for addition loop
    limit = (counterChecker1 >= counterChecker2) ? counterChecker1 : counterChecker2;

    // check validity
    if (cnt11 == 0 && cnt22 == 0) {
        while (limit != 0) {
            A = (int)(binary1 % 10);
            B = (int)(binary2 % 10);
            Cin = Cout;
            S = (A ^ B) ^ Cin;
            Cout = ((A ^ B) & Cin) | (A & B);
            binaryResult = binaryResult + (S * pow(10, cnt11));
            cnt11++;
            binary1 = binary1 / 10;
            binary2 = binary2 / 10;
            limit--;
        }

        // print zeros padding for shorter number AFTER the sum, like original code
        if (counterChecker1 > counterChecker2) {
            while (counterChecker1 > counterChecker2) {
                printf("0");
                counterChecker1--;
            }
            printf("%llu + %llu = %.0f\n", binary22, binary11, binaryResult);
        } else if (counterChecker2 > counterChecker1) {
            while (counterChecker2 > counterChecker1) {
                printf("0");
                counterChecker2--;
            }
            printf("%llu + %llu = %.0f\n", binary11, binary22, binaryResult);
        } else {
            printf("%llu + %llu = %.0f\n", binary11, binary22, binaryResult);
        }
    } else {
        printf("Invalid input!\n");
    }

    // reset variables for cleanliness
    cnt11 = 0;
    cnt22 = 0;
    counterChecker1 = 1;
    counterChecker2 = 1;
    Cout = 0;
    binaryResult = 0;
}

void printHI() {
    int size, loopout, loopin;
    printf("Please enter size: ");
    scanf("%d", &size);

    // the head
    if (size < 0) {
        printf("Invalid input!\n");
    } else {
        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("#");
        }
        printf("#");

        for (loopout = 0; loopout < size + 1; loopout++) {
            printf(" ");
        }

        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("#");
        }
        printf("#  ");

        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("#");
        }
        printf("#\n");

        // the upper middle of the "HI"
        for (loopin = 0; loopin < size; loopin++) {
            for (loopout = 0, printf("#"); loopout < size; loopout++) {
                printf("*");
            }
            printf("#");

            for (loopout = 0; loopout < size + 1; loopout++) {
                printf(" ");
            }

            for (loopout = 0, printf("#"); loopout < size; loopout++) {
                printf("*");
            }
            printf("#  ");

            for (loopout = 0, printf("#"); loopout < size; loopout++) {
                printf("*");
            }
            printf("#\n");
        }

        // upper center of "HI"
        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("*");
        }
        printf("#");

        for (loopout = 0; loopout < size + 1; loopout++) {
            printf("#");
        }

        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("*");
        }
        printf("#  ");

        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("*");
        }
        printf("#\n");

        // the middle middle of the "HI"
        for (loopin = 0; loopin < size; loopin++) {
            for (loopout = 0, printf("#"); loopout < size; loopout++) {
                printf("*");
            }
            printf("*");

            for (loopout = 0; loopout < size + 1; loopout++) {
                printf("*");
            }

            for (loopout = 0, printf("*"); loopout < size; loopout++) {
                printf("*");
            }
            printf("#  ");

            for (loopout = 0, printf("#"); loopout < size; loopout++) {
                printf("*");
            }
            printf("#\n");
        }

        // lower center of "HI"
        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("*");
        }
        printf("#");

        for (loopout = 0; loopout < size + 1; loopout++) {
            printf("#");
        }

        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("*");
        }
        printf("#  ");

        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("*");
        }
        printf("#\n");

        // lower middle of "HI"
        for (loopin = 0; loopin < size; loopin++) {
            for (loopout = 0, printf("#"); loopout < size; loopout++) {
                printf("*");
            }
            printf("#");

            for (loopout = 0; loopout < size + 1; loopout++) {
                printf(" ");
            }

            for (loopout = 0, printf("#"); loopout < size; loopout++) {
                printf("*");
            }
            printf("#  ");

            for (loopout = 0, printf("#"); loopout < size; loopout++) {
                printf("*");
            }
            printf("#\n");
        }

        // the bottom of "HI"
        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("#");
        }
        printf("#");

        for (loopout = 0; loopout < size + 1; loopout++) {
            printf(" ");
        }

        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("#");
        }
        printf("#  ");

        for (loopout = 0, printf("#"); loopout < size; loopout++) {
            printf("#");
        }
        printf("#\n");
    }
}

void countBits() {
    int binaryInput, temp, onesCount = 0;
    int isValid = 1;

    printf("Please enter a binary number: ");
    scanf("%d", &binaryInput);

    temp = binaryInput;

    // validate input: check if it only contains 0s and 1s
    while (temp != 0) {
        int digit = temp % 10;
        if (digit != 0 && digit != 1) {
            printf("Invalid input!\n");
            isValid = 0;
            break;
        }
        temp /= 10;
    }

    // count the number of 1's if valid
    if (isValid) {
        while (binaryInput != 0) {
            if (binaryInput % 10 == 1) {
                onesCount++;
            }
            binaryInput /= 10;
        }
        printf("%d\n", onesCount);
    }
}

void decimalToBinary() {
    int decimalInput, binary = 0, bitCount = 0;

    printf("Enter a non-negative decimal number: ");
    scanf("%d", &decimalInput);

    int originalDecimal = decimalInput;

    // check if input is valid
    if (decimalInput < 0) {
        printf("Invalid input!\n");
        return;
    }

    // convert decimal to binary
    while (decimalInput != 0) {
        int bit = decimalInput % 2;
        binary += bit * (int)pow(10, bitCount++);
        decimalInput /= 2;
    }

    printf("%d => %d\n", originalDecimal, binary);
}

void zigZag() {
    int decimal, tempDecimal, binaryReversed = 0, bitCount = 0;
    int countPow = 0;
    int isValid = 1;

    printf("Enter a non-negative decimal number: ");
    scanf("%d", &decimal);

    if (decimal < 0) {
        printf("Invalid input!\n");
        return;
    }

    tempDecimal = decimal;

    // count how many times divisible by 2 when last digit is even
    if (decimal != 0) {
        while ((tempDecimal % 10) % 2 == 0) {
            countPow++;
            tempDecimal /= 2;
        }
    }

    // convert decimal to binary reversed (digits reversed)
    tempDecimal = decimal;
    while (tempDecimal != 0) {
        int bit = tempDecimal % 2;
        binaryReversed = binaryReversed * 10 + bit;
        tempDecimal /= 2;
    }

    // reverse the binary number to get correct order
    int binary = 0;
    while (binaryReversed != 0) {
        int digit = binaryReversed % 10;
        binary = binary * 10 + digit;
        binaryReversed /= 10;
    }

    // multiply by 10^countPow
    binary *= (int)pow(10, countPow);

    // if binary/10 == 0, print true
    if (binary / 10 == 0) {
        printf("true\n");
        return;
    }

    // check if any two adjacent binary digits are the same
    int checkAdjacentSame = 0;
    int tempBinary = binary;
    while (tempBinary / 10 != 0) {
        int lastTwoDigits = tempBinary % 100;
        int rightDigit = lastTwoDigits % 10;
        int leftDigit = lastTwoDigits / 10;
        if (rightDigit == leftDigit) {
            checkAdjacentSame++;
        }
        tempBinary /= 10;
    }

    if (checkAdjacentSame > 0) {
        printf("false\n");
    } else {
        printf("true\n");
    }
}
