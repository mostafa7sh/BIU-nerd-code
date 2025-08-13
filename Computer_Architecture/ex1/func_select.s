.data
pstrlen1_fmt:
    .string "first pstring length: %d"
pstrlen2_fmt:
    .string ", second pstring length: %d\n"
case_fmt:
    .string "length: %d, string: %s\n"
invalid_fmt:
    .string "invalid option!\n"
printf_fmt:
    .string "%d\n"
scanfij_fmt:
    .string "%d"
input_fmt:
    .string "invalid input!\n"
.bss
leng1:
    .byte 0
leng2:
    .byte 0

.text
.globl run_func
.type run_func, @function

run_func:
    # %rdi = choice
    # %rsi = pstr1
    # %rdx = pstr2
    pushq %rbp
    movq %rsp, %rbp
    movq %rdi, %r10
    leaq (%rsi), %r11
    leaq (%rdx), %r12

    # calling pstrlen (*pstr1)
    movq $0, %rax
    movq %r10, %rcx # now %rcx = choice
    leaq (%r11), %rdi # now %rdi = &pstr1
    cmpq $31, %rcx
    je pstrlen_help

    movq %r10, %rcx # now %rcx = choice
    leaq (%r11), %rdi # now %rdi = &pstr1
    cmpq $33, %rcx
    je swapcase_help

    leaq (%r11), %r8 # now %r8 = &pstr1
    leaq (%r12), %r9 # now %r9 = &pstr2
    movq %r10, %rcx
    cmpq $34, %rcx
    je pstrij_help

    jmp invalid_option

    jmp exit

pstrlen_help:
    movq $0, %rax
    call pstrlen
    subb $48, %al
    movq $0, %rsi
    movb %al, %sil
    movq $pstrlen1_fmt, %rdi
    call printf

    leaq (%r12), %rdi # now %rdi = &pstr2
    movq $0, %rax
    call pstrlen
    subb $48, %al
    movq $0, %rsi
    movb %al, %sil
    movq $pstrlen2_fmt, %rdi
    call printf

    jmp exit

swapcase_help:
    movq $0, %r8
    movq $0, %rax
    call swapCase
    # %rax = &newpstr1
    movq $0, %rsi
    movb (%rax), %sil
    movq $case_fmt, %rdi
    leaq 1(%rax), %rdx
    call printf

    movq $0, %r8
    movq $0, %rax
    leaq (%r12), %rdi
    call swapCase
    movq $0, %rsi
    movb (%rax), %sil
    movq $case_fmt, %rdi
    leaq 1(%rax), %rdx
    call printf

    jmp exit

pstrij_help:
    leaq (%r11), %r13
    leaq (%r11), %rdi
    movq $0, %rax
    call pstrlen
    subb $48, %al
    movb %al, leng1

    leaq (%r12), %rdi
    movq $0, %rax
    call pstrlen
    subb $48, %al
    movb %al, leng2
    subq $80, %rsp

    leaq 5(%rsp), %rsi
    movq $scanfij_fmt, %rdi
    xorq %rax, %rax
    call scanf

    leaq 1(%rsp), %rsi
    movq $scanfij_fmt, %rdi
    xorq %rax, %rax
    call scanf

    movq 5(%rsp), %r14 # i
    movq 1(%rsp), %r15 # j  | %r15b = j

    # bad inputs of i and j
    cmpb leng1, %r15b
    jge invalidij
    cmpb leng2, %r15b
    jge invalidij
    cmpb %r15b, %r14b
    jge invalidij


    jmp exit

invalidij:
    movq $input_fmt, %rdi
    xorq %rax, %rax
    call printf

    movq $0, %rsi
    movb (%r13), %sil
    movq $case_fmt, %rdi
    leaq 1(%r13), %rdx
    call printf

    movq $0, %rsi
    movb (%r12), %sil
    movq $case_fmt, %rdi
    leaq 1(%r12), %rdx
    call printf
    jmp exit

invalid_option:
    movq $invalid_fmt, %rdi
    call printf
    jmp exit
exit:
    xorl %eax, %eax
    movq %rbp, %rsp
    popq %rbp
    ret


