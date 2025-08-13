.data
printf_fmt:
    .string "%d\n"
char_fmt:
    .string "%c"
inter_fmt:
    .string "\n"

.align 8
.section   .text
.section   .text
.global	pstrlen
.global pstrijcpy
.global swapCase
.extern
  .type	 pstrlen, @function	# the label "main" representing the beginning of a function
  .type	 pstrijcpy, @function	# the label "main" representing the beginning of a function
  .type	 swapCase, @function	# the label "main" representing the beginning of a function

pstrlen:
    movq $0, %rax
    movb (%rdi), %sil
    addb $48, %sil
    movb %sil, %al
    ret

swapCase:
    # now %rdi = &pstr1
    leaq (%rdi), %rax
    jmp swaploop


swaploop:
    movq $0, %rsi
    addq $1, %r8
    movb (%r8,%rdi), %sil
    cmpb $0, %sil
    je swapend
    cmpb $90, %sil
    jle swapZ
    cmpb $97, %sil
    jge swapa
    jmp swaploop

swapZ:
    cmpb $65, %sil
    jge swapA
    jmp swaploop

swapA:
    addb $32, (%r8,%rdi)
    jmp swaploop

swapa:
    cmpb $122, %sil
    jle swapz
    jmp swaploop

swapz:
    subb $32, (%r8,%rdi)
    jmp swaploop

swapend:
    ret

pstrijcpy:
    # rdi = pstr1 | rdx = i
    # rsi = pstr2 | rcx = j
    leaq (%rdi), %rax
    jmp ijloop

ijloop:
    movq $0, %r9
    movq $0, %r8
    movb 1(%rdx, %rsi), %r9b #z
    movb 1(%rdx, %rdi), %r8b #a
    subb %r8b, %r9b
    subb %r9b, 1(%rdx, %rdi)
    cmpq %rdx, %rcx
    je ijend
    addq $1, %rdx
    jmp ijloop

ijend:
    ret


exit:
    xorl %eax, %eax
    movq %rbp, %rsp
    popq %rbp
    ret


