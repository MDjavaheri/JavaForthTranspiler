# Java to Forth Transpiler
## Programming Languages, Spring 2017, Prof. Van Kelly
### Assignment
A basic transpiler that's input language ise a tiny subset of Java which includes the following:
* integer literals of type int (1245 is OK, but no 1245L for long).
* int variables
* any arithmetic or boolean expression made up of integer literals, * int variables, and all Java arithmetic or boolean operators, * including parentheses and the triple operator.
* like C, true denotes 1 and false denotes 0, but any non-zero number * is "truthy"
* variable declarations like
* int foo;
* int foo = 1 + 1;  // any numeric expression on the right-hand side
* assignment statements
* a non-Java-compliant print statement of the form
* print expression;
* a sequence of any such statements as those defined above

### Things We're Proud Of
* The test suite (see below)
* Java 8

### Running
#### From Eclipse (Preferred)
Just hit the big green button.
#### From the Command Line
```
$ [navigate to directory]
$ antlr4 JavaForth.g4
$ javac JavaForth*.java
$ java JavaForthRunner args*
```
`args` can be a path to an input file, but, if empty, the program will just use the default `input.txt`. Given only one parameter, all input will be printed to console, but one may provide an additional `true` parameter for it to be saved to `output.txt` instead and a third parameter (also optional) for an output file path, as well.
If the command line fails, Eclipse is recommended.
#### From Another Class
In addition to the above, there is another override of `main` that allows one to simply pass a string to be transpiled, but the current implementation follows the above file creation pattern and does not return it directly.  
### Testing
A JUnit test suite is provided in `AcceptanceTests.java`

### Credit
* Basic `Java.g4` grammar from `https://github.com/antlr/grammars-v4/blob/master/java/Java.g4`
* Role model for patience with students who can do it but keep falling behind from our great rodel for the past three years, Prof. Kelly.