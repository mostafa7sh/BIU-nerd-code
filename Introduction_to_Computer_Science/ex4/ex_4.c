#include <stdio.h>

#define CUBE_SIZE 4

void gameCreate(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE]);
int tie(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE]);
char winPossibility(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE], char player);
char winPossibility1(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE], char player,
                     int playerHeight, int playerRow, int playerColumn);
void gamePrinter(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE]);
int restarter(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE], char *player);

int main() {
    int playerHeight, playerRow, playerColumn;
    char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE];
    char starter, player = 'X', winner;
    unsigned int fullCube = 0;
    int restart;
    printf("Would you like to start? (y/n)\n");
    scanf("%c", &starter);
    if (starter != 'y') {
        printf("YEET");
        return 0;
    }

    gameCreate(cube);
    printf("Please enter your game sequence.\n");
    while (1) {
        scanf("%d %d %d", &playerHeight, &playerRow, &playerColumn);

        if (playerHeight >= 4 || playerHeight < 0 || playerColumn >= 4 ||
            playerColumn < 0 || playerRow >= 4 || playerRow < 0) {
            printf("Input incorrect.\n");
            restart = restarter(cube, &player);
            if (restart) {
                continue;
            } else {
                break;
            }
        }

        if (cube[playerHeight][playerRow][playerColumn] != '*') {
            printf("Input incorrect.\n");
            restart = restarter(cube, &player);
            if (restart) {
                continue;
            } else {
                break;
            }
        }

        cube[playerHeight][playerRow][playerColumn] = player;
        fullCube++;

        winner = winPossibility1(cube, player, playerHeight, playerRow, playerColumn);
        if (winner != 'M') {
            printf("%c is the winner.\n", winner);
            gamePrinter(cube);
            restart = restarter(cube, &player);
            if (restart) {
                continue;
            } else {
                break;
            }
        }

        if (fullCube >= 64) {
            if (tie(cube)) {
                printf("Tie.\n");
                gamePrinter(cube);
                restart = restarter(cube, &player);
                if (restart) {
                    continue;
                } else {
                    break;
                }
            }
        }

        if (player == 'X') {
            player = 'O';
        } else {
            player = 'X';
        }
    }

    return 0;
}
/************************************************************************
 * The Input: tic tac toe game and the last player *
 * The output: 1 if we want to restart the game, 0 if we want to exit *
 * The Function operation: asks the players if they want to continue,
 * if yes we re-create the game, otherwise we return 0 to end the game later *
 *************************************************************************/
int restarter(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE], char *player) {
    char starter;
    printf("Would you like to continue? (y/n)\n");
    do {
        scanf(" %c", &starter);
    } while (starter != 'y' && starter != 'n');
    if (starter != 'y') {
        printf("YEET");
        return 0;
    } else {
        printf("Please enter your game sequence.\n");
        *player = 'X';
        gameCreate(cube);
    }
    return 1;
}
/************************************************************************
 * The Input: tic tac toe game *
 * The output: 1 if we get tie, 0 otherwise *
 * The Function operation: simply checks if every place is played in the game,
 * if all are taken (not *) return 1, if one place is not played return 0 *
 *************************************************************************/
int tie(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE]) {
    int height, row, column;
    for (height = 0; height < 4; height++) {
        for (row = 0; row < 4; row++) {
            for (column = 0; column < 4; column++) {
                if (cube[height][row][column] == '*') {
                    return 0;
                }
            }
        }
    }
    return 1;
}
/************************************************************************
 * The Input: tic tac toe game and the last player *
 * The output: in case of winning the game, returns the winner player *
 * The Function operation: scan every possibility of winning the game
 * and returns back the player who won the game *
 *************************************************************************/
char winPossibility(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE], char player) {
    int height, row, column, cntWinning = 0;
    for (height = 0; height < 4; height++) {

        for (row = 0; row < 4; row++) {
            for (column = 0; column < 3; column++) {
                if (cube[height][row][column] == cube[height][row][column + 1] && cube[height][row][column] != '*') {
                    cntWinning++;
                }
            }
            if (cntWinning == 3) {
                return player;
            } else {
                cntWinning = 0;
            }
        }

        for (column = 0; column < 4; column++) {
            for (row = 0; row < 3; row++) {
                if (cube[height][row][column] == cube[height][row + 1][column] && cube[height][row][column] != '*') {
                    cntWinning++;
                }
            }
            if (cntWinning == 3) {
                return player;
            } else {
                cntWinning = 0;
            }
        }

        for (row = 0, column = 0; row < 3; row++, column++) {
            if (cube[height][row][column] == cube[height][row + 1][column + 1] && cube[height][row][column] != '*') {
                cntWinning++;
            }
        }
        if (cntWinning == 3) {
            return player;
        } else {
            cntWinning = 0;
        }

        for (row = 0, column = 3; row < 3; row++, column--) {
            if (cube[height][row][column] == cube[height][row + 1][column - 1] && cube[height][row][column] != '*') {
                cntWinning++;
            }
        }
        if (cntWinning == 3) {
            return player;
        } else {
            cntWinning = 0;
        }
    }

    for (row = 0; row < 4; row++) {
        for (column = 0; column < 4; column++) {
            for (height = 0; height < 3; height++) {
                if (cube[height][row][column] == cube[height + 1][row][column] && cube[height][row][column] != '*') {
                    cntWinning++;
                }
            }
            if (cntWinning == 3) {
                return player;
            } else {
                cntWinning = 0;
            }
        }
    }

    if (cube[0][0][0] == cube[1][1][1] && cube[1][1][1] == cube[2][2][2] && cube[2][2][2] == cube[3][3][3] && cube[3][3][3] != '*') {
        return player;
    }
    if (cube[0][0][3] == cube[1][1][2] && cube[1][1][2] == cube[2][2][1] && cube[2][2][1] == cube[3][3][0] && cube[3][3][0] != '*') {
        return player;
    }
    if (cube[0][3][0] == cube[1][2][1] && cube[1][2][1] == cube[2][1][2] && cube[2][1][2] == cube[3][0][3] && cube[3][0][3] != '*') {
        return player;
    }
    if (cube[0][3][3] == cube[1][2][2] && cube[1][2][2] == cube[2][1][1] && cube[2][1][1] == cube[3][0][0] && cube[3][0][0] != '*') {
        return player;
    }
    return 'M';
}
/************************************************************************
 * The Input: tic tac toe game, player and his placement *
 * The output: the winner! *
 * The Function operation: checks win possibility of the placement instead
 * of looping over the cube each time like winPossibility function *
 *************************************************************************/
char winPossibility1(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE], char player,
                     int playerHeight, int playerRow, int playerColumn) {
    int height, row, column;

    // normal hight or row or column win
    if ((cube[playerHeight][0][playerColumn] == player &&
         cube[playerHeight][1][playerColumn] == player &&
         cube[playerHeight][2][playerColumn] == player &&
         cube[playerHeight][3][playerColumn] == player) ||
        (cube[playerHeight][playerRow][0] == player &&
         cube[playerHeight][playerRow][1] == player &&
         cube[playerHeight][playerRow][2] == player &&
         cube[playerHeight][playerRow][3] == player) ||
        (cube[0][playerRow][playerColumn] == player &&
         cube[1][playerRow][playerColumn] == player &&
         cube[2][playerRow][playerColumn] == player &&
         cube[3][playerRow][playerColumn] == player)) {
        return player;
    }

    // diagnals of the cude while remembering where the player played
    if ((cube[0][playerRow][0] == player &&
         cube[1][playerRow][1] == player &&
         cube[2][playerRow][2] == player &&
         cube[3][playerRow][3] == player) ||
        (cube[0][playerRow][3] == player &&
         cube[1][playerRow][2] == player &&
         cube[2][playerRow][1] == player &&
         cube[3][playerRow][0] == player) ||
        (cube[0][0][playerColumn] == player &&
         cube[1][1][playerColumn] == player &&
         cube[2][2][playerColumn] == player &&
         cube[3][3][playerColumn] == player) ||
        (cube[0][3][playerColumn] == player &&
         cube[1][2][playerColumn] == player &&
         cube[2][1][playerColumn] == player &&
         cube[3][0][playerColumn] == player) ||
        (cube[playerHeight][0][0] == player &&
         cube[playerHeight][1][1] == player &&
         cube[playerHeight][2][2] == player &&
         cube[playerHeight][3][3] == player) ||
        (cube[playerHeight][3][0] == player &&
         cube[playerHeight][2][1] == player &&
         cube[playerHeight][1][2] == player &&
         cube[playerHeight][0][3] == player) ||
        (cube[playerHeight][playerRow][0] == player &&
         cube[playerHeight][playerRow][1] == player &&
         cube[playerHeight][playerRow][2] == player &&
         cube[playerHeight][playerRow][3] == player)) {
        return player;
    }

    // four cases if the hardest diagonal in the cude
    if ((cube[0][0][0] == player &&
         cube[1][1][1] == player &&
         cube[2][2][2] == player &&
         cube[3][3][3] == player) ||
        (cube[0][0][3] == player &&
         cube[1][1][2] == player &&
         cube[2][2][1] == player &&
         cube[3][3][0] == player) ||
        (cube[0][3][0] == player &&
         cube[1][2][1] == player &&
         cube[2][1][2] == player &&
         cube[3][0][3] == player) ||
        (cube[0][3][3] == player &&
         cube[1][2][2] == player &&
         cube[2][1][1] == player &&
         cube[3][0][0] == player)) {
        return player;
    }
    return 'M';
}
/************************************************************************
 * The Input: the tic tac toe game *
 * The output: prints the game after the game ends (tie or win) *
 * The Function operation: prints every place in the game and its index *
 *************************************************************************/
void gamePrinter(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE]) {
    int row, column, height;
    for (height = 0; height < 4; height++) {
        for (row = 0; row < 4; row++) {
            for (column = 0; column < 4; column++) {
                printf("(%d %d %d) ", height, row, column);
            }
            for (column = 0; column < 4; column++) {
                printf("%c ", cube[height][row][column]);
            }
            printf("\n");
        }
        printf("\n");
    }
}
/************************************************************************
 * The Input: the tic tac toe game *
 * The output: an empty tic tac toe game (filled with '*') *
 * The Function operation: place a star (*) in every place in the game *
 *************************************************************************/
void gameCreate(char cube[CUBE_SIZE][CUBE_SIZE][CUBE_SIZE]) {
    int height, column, row;
    for (height = 0; height < 4; height++) {
        for (row = 0; row < 4; row++) {
            for (column = 0; column < 4; column++) {
                cube[height][row][column] = '*';
            }
        }
    }
}
