package org.exceptions;

public class IncorrectArgumentException extends Exception {
    public IncorrectArgumentException() {
        super("The argument entered is incorrect!");
    }
}
