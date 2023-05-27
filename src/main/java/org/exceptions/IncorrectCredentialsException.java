package org.exceptions;

public class IncorrectCredentialsException extends Exception {
    public IncorrectCredentialsException() {
        super("Incorrect user credentials entered!");
    }
}
