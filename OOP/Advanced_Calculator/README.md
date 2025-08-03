# Math Expression Evaluator in Java

## 📘 Overview

This is a Java-based expression evaluator and simplifier that supports arithmetic and trigonometric operations using an object-oriented approach. Expressions are built using classes and can be evaluated with or without variable substitution.

## 🧮 Features

- Representation of mathematical expressions:
  - Constants, variables, binary operations (add, subtract, multiply, divide, power)
  - Unary operations (negation, sine, cosine, logarithm)
- Expression evaluation with variable values
- Expression simplification and differentiation
- Clean object-oriented design using inheritance and interfaces

## 📂 Project Structure

| File                 | Purpose                                             |
|----------------------|-----------------------------------------------------|
| `Main.java`          | Example usage and entry point (optional)           |
| `ExpressionsTest.java` | Contains test cases and usage demonstrations       |
| `Expression.java`    | Interface for all expressions                      |
| `BaseExpression.java`| Common functionality for all expressions           |
| `UnaryExpression.java` | Abstract class for expressions with one operand  |
| `BinaryExpression.java`| Abstract class for expressions with two operands |
| `Num.java`           | Numeric constant                                    |
| `Var.java`           | Variable                                            |
| `Plus.java`          | Addition                                            |
| `Minus.java`         | Subtraction                                         |
| `Mult.java`          | Multiplication                                      |
| `Div.java`           | Division                                            |
| `Pow.java`           | Exponentiation                                      |
| `Neg.java`           | Negation                                            |
| `Sin.java`           | Sine function                                       |
| `Cos.java`           | Cosine function                                     |
| `Log.java`           | Logarithmic function                                |
| `Makefile`           | Optional: Compile the project using `make`         |

## 🛠️ How to Compile and 🧹 Clean Up

### Using `javac` (manually):

```bash
javac *.java
java Main
```

```bash
rm  *.class
```
### Using `Makfile` (automatically):

```bash
make run
```

```bash
make clean
```

