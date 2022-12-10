Corshock, the Java-based BF Compiler
----------------------------
This Java program compiles and runs programs written in the minimalistic programming language BF, or Brainfuck. It will also attempt to catch and highlight any program errors, just like an IDE.

You only need to paste your code in the "Code" box in the GUI. Any characters that are not "<>+-.,[]" will be ignored, so you can add comments or newlines without worrying about special demarcation characters.

After you have added your code, hit the "Run Code" button. This will run the code, and display any compile-time or runtime errors.

If there is a "," somewhere in your code, the program will pause and ask for a single ASCII character of input in the "Input" box. Enter your desired input and hit the "Enter" key.

Here's a quick error translation guide:
----------------------------

**"There was an error finding or opening the file."**
This happens if the file path is incorrect, and the file could not be found, or any of the programs have been tampered with.

**"The code could not be successfully processed."**
The user code length limit of 2,147,483,647 characters has been crossed, or (more likely) the program has experienced an unknown error.

**"You cannot iterate under 0."**
The user program contains a "<" character that attempts to iterate left while the data pointer is at cell 0. If this was intentional, make sure to check the "Bound Wrapping" option in the File menu.

**"Array has overflowed."**
The user program contains a ">" character that attempts to iterate past the last element in the byte array (the standard size is 30,000, according to the official BF implementation guide). If this was intentional, increase the "Stack Length" value or check the "Bound Wrapping" option in the File menu.

**"Unmatched left/right square bracket in code."**
There is a square bracket in the code that does not have a match. The error message will tell you whether it is a left or right bracket.
