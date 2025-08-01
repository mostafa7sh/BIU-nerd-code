# Phonebook Manager in C

## Overview

This project is a simple command-line phonebook manager implemented in C. It allows users to store, search, edit, and delete contacts efficiently using a linked list data structure organized by the first letter of the last name.

## Features

- Add new contacts (first name, last name, phone number)
- Delete contacts by full name
- Search contacts by phone number or by full name
- Edit phone number of existing contacts
- Display all contacts in the phonebook
- Input validation and duplicate checks to avoid redundant entries
- Clean memory management on exit

## How It Works

- Contacts are stored in an array of linked lists indexed by the initial letter of the last name (`A-Z`).
- Each contact contains dynamically allocated strings for first name, last name, and phone number.
- The user interacts with a text-based menu to perform operations.
- Duplicate names or phone numbers are prevented.
- The phonebook automatically frees all allocated memory on exit.

## Usage

1. Compile the program:

```bash
gcc ex_5.c -lm -std=c99 -DNDEBUG -o ex_5
./ex_5
```