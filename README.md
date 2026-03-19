# Overview

This project is a Java console application that tracks module progress for CSE 310. Instead of only printing a single line, the software now:

1. Tracks study sessions in structured data.
2. Prints a formatted session report table.
3. Calculates totals and averages.
4. Identifies highest and lowest effort sessions.
5. Displays submission action items.

The goal is to demonstrate Java fundamentals with meaningful logic, readable output, and modular functions.



# Development Environment

* Visual Studio Code
* OpenJDK 21
* Git / GitHub

The programming language used is **Java**. Java is a compiled, statically-typed, object-oriented language. Source files (`.java`) are compiled into bytecode (`.class`) using `javac`, and then executed on the Java Virtual Machine (JVM) using `java`.

## How to Build and Run

From the repository root (`hello-java-world`):

```bash
mkdir -p bin
javac -d bin src/com/chris/helloworld/HelloWorld.java
java -cp bin com.chris.helloworld.HelloWorld
```

Optional: pass a custom target hour goal.

```bash
java -cp bin com.chris.helloworld.HelloWorld 22
```

# Useful Websites

* [Java Full Course for Beginners](https://www.youtube.com/watch?v=eIrMbAQSU34)
* [Java in Visual Studio Code](https://code.visualstudio.com/docs/languages/java)
* [Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/)
* [OpenJDK](https://openjdk.org/)

# Future Work

* Add user input to enter custom study sessions.
* Save session data to a file for persistence.
* Add unit tests for summary calculations.