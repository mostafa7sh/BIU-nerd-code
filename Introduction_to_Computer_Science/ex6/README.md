# Binary Tree Simulation in C

## Overview

This project is a console-based simulation inspired by the biblical story of **Moshe (Moses)**. It uses a **binary tree** to represent a growing and changing population of people, tracking age, gender, and special conditions like the search for Moshe.

## Features

- Add new "child" nodes (representing people)
- Remove all male nodes (simulating Pharaoh's decree)
- Search for Moshe in the tree (preorder, inorder, or postorder)
- Print all people in different tree traversal orders
- View statistics (total, male, and female population)
- Automatically age all individuals after each action
- Automatically end simulation when Moshe becomes too old
- Proper memory cleanup on exit

## How It Works

- The tree stores people as nodes, tracking age and gender.
- Generic functions operate on the tree using function pointers.
- Each action ages the population and checks if Moshe is too old.
- Removal and search functions work recursively.
- The program ends gracefully when conditions are met or on exit.

## Usage

1. Make sure `BinTree.c` and `BinTree.h` are implemented and included.
2. Compile the program:

```bash
gcc ex_6.c BinTree.c -lm -std=c99 -DNDEBUG -o ex_6
./ex_6
```