.extern printf
.extern scanf
.extern srand
.extern rand

.data
enter_string:
    .string "Enter configuration seed: "
bad_input:
    .string "Incorrect.\n"
scanf_fmt:
    .string "%d"
win_fmt:
    .string "Congratz! You won!\n"
guess_fmt:
    .string "What is your guess? "
inter_fmt:
    .string "\n"
lose_fmt:
    .string "Game over, you lost :(. The correct answer was %d\n"

cnt:
    .long 5
.bss

guess:
    .long 0
random:
    .long 0
seed:
    .long 0

.section .text
.globl main
.type main, @function
main:
    # Enter
    pushq %rbp
    movq %rsp, %rbp

    # Print the prompt
    movl $enter_string, %edi
    xorl %eax, %eax
    call printf

    # scnaf the seed
    leal seed, %esi
    movl $scanf_fmt, %edi
    xorl %eax, %eax
    call scanf

    # call the srand
    movl seed, %edi
    xorq %rax, %rax
    call srand
    call rand
    movl %eax, random

    # I want the modulo 10 of this random numnber
    xorq %rax, %rax
    xorq %rdx, %rdx
    movl random, %eax
    movl $10, %ebx
    divl %ebx
    imull $10, %eax
    subl %eax, random

    jmp while

while:
    # ask the user to guess a number after that I scan it
    movl $guess_fmt, %edi
    xorl %eax, %eax
    call printf
    movl $scanf_fmt, %edi
    leal guess, %esi
    xorl %eax, %eax
    call scanf
    movl guess, %edi
    movl random, %esi
    cmpl %edi, %esi
    je win

    subl $1, cnt
    cmpl $0, cnt
    je lost

    movl $bad_input, %edi
    xorl %eax, %eax
    call printf


    jmp while

lost:
    movl $bad_input, %edi
    xorl %eax, %eax
    call printf
    movl $lose_fmt, %edi
    movl random, %esi
    xorl %eax, %eax
    call printf
    jmp exit

win:
    movl $win_fmt, %edi
    xorl %eax, %eax
    call printf
    jmp exit

exit:
    xorl %eax, %eax
    movq %rbp, %rsp
    popq %rbp
    ret


