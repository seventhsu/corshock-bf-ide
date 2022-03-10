package com.cside.ui;

// Runs provided BF code when summoned by the Run Code button
// A new compiler is created every time with the current options/parameters (in constructor)
public class Compiler {
    private final int STACK_SIZE; // For reference, standard BF stack size is 30,000
    private final boolean BOUND_WRAP_ALLOWED;
    private byte[] stack;
    private int stackPointer = 0;
    private final String code;

    public Compiler(int stackLength, boolean boundWrapState,
                    String userCode) throws IllegalArgumentException {
        STACK_SIZE = stackLength;
        stack = new byte[STACK_SIZE];
        BOUND_WRAP_ALLOWED = boundWrapState;
        code = userCode;
        checkSquareBrackets(code); // Exception is thrown here, at "compile-time"
    }

    public void execute() throws ArrayIndexOutOfBoundsException {

        // Reads the code character by character and interprets it (all the interpretation done here)
        for (int charPos = 0; charPos < code.length(); charPos++) {
            char c = code.charAt(charPos); // current code character

            if (c == '<') {
                if (stackPointer > 0) // is not at first element
                    stackPointer--;
                else if (!BOUND_WRAP_ALLOWED) // already at first element and cannot iterate below 0
                    throw new ArrayIndexOutOfBoundsException(
                            "At character number " + charPos + ": you cannot iterate under 0.");
                else // already at first element and can iterate below 0
                    stackPointer += STACK_SIZE - 1; // Wrap to last element
            }
            else if (c == '>') {
                if (stackPointer < STACK_SIZE) // is not at last element
                    stackPointer++;
                else if (!BOUND_WRAP_ALLOWED) // already at last element and cannot iterate past it
                    throw new ArrayIndexOutOfBoundsException(
                            "At character number " + charPos + ": stack has overflowed.");
                else // already at last element and can iterate past it
                    stackPointer -= STACK_SIZE - 1; // Wrap to first element
            }
            else if (c == '+') {
                stack[stackPointer]++;
            }
            else if (c == '-') {
                stack[stackPointer]--;
            }
            else if (c == '.') {
                IDEFrame.appendToOutputArea((char) (stack[stackPointer]));
            }
            /*else if (c == ',') {
                // TODO two buttons (single char and autofeed)
                char inputChar;
                // TODO Indicate in window "Waiting for input..."
                if (stdin.length() == 0) inputChar = (char) 10; // Input empty (line ended), give a carriage return
                else {
                    // Pull off current first character of input string and use it
                    inputChar = stdin.charAt(0);
                    stdin = stdin.substring(1);
                }
                stack[stackPointer] = (byte) inputChar;
            }*/
            else if (c == '[') {
                if (stack[stackPointer] == 0) { // Loop has no more necessary reps:
                    // Find matching right square bracket and skip to it, jumping over the loop entirely
                    charPos = findMatchingRight(code, charPos);
                }
            }
            else if (c == ']') {
                if (stack[stackPointer] != 0) { // End of loop rep but needs another execution:
                    // Find matching left square bracket and return to it, restarting the loop
                    charPos = findMatchingLeft(code, charPos);
                }
            }
        }
    }

    // HELPER METHODS BEYOND THIS POINT
    
    public byte[] getStack() { // For debugger
        return stack;
    }
    
    public int getPointerPos() { // For debugger
        return stackPointer;
    }
    
    // Counts the square brackets and throws an exception if there is more of one than the other
    private void checkSquareBrackets(String code) throws IllegalArgumentException {
        int leftBraces = 0;
        int rightBraces = 0;
        for (int i = 0; i < code.length(); i++) {
            String currentChar = code.substring(i, i + 1);
            if (currentChar.equals("[")) leftBraces++;
            else if (currentChar.equals("]")) rightBraces++;
        }
        if (leftBraces > rightBraces)
            throw new IllegalArgumentException("Unmatched left square bracket in code.");
        else if (leftBraces < rightBraces)
            throw new IllegalArgumentException("Unmatched right square bracket in code.");
    }

    // Returns the int position of a right bracket's matching left square bracket
    private int findMatchingLeft(String code, int rightBracketPos) {
        int openRights = 0;
        for (int charPos = rightBracketPos - 1; charPos >= 0; charPos--) {
            char currentChar = code.charAt(charPos);
            if (currentChar == ']') openRights++; // Found another right bracket
            else if (currentChar == '[' && openRights > 0) openRights--; // Found a different right bracket's match
            else if (currentChar == '[' && openRights == 0) return charPos; // Found our match
        }
        return -1; // Will never get here with equal numbers of brackets
    }

    // Returns the int position of a left bracket's matching right square bracket
    private int findMatchingRight(String code, int leftBracketPos) {
        int openLefts = 0;
        for (int charPos = leftBracketPos + 1; charPos < code.length(); charPos++) {
            char currentChar = code.charAt(charPos);
            if (currentChar == '[') openLefts++; // Found another left bracket
            else if (currentChar == ']' && openLefts > 0) openLefts--; // Found a different left bracket's match
            else if (currentChar == ']' && openLefts == 0) return charPos; // Found our match
        }
        return -1; // Will never get here with equal numbers of brackets
    }
}
