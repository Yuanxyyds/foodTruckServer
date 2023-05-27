package org.exceptions;

public class UnknownSorterException extends Exception {
    public UnknownSorterException() {
        super("This sorter doesn't exit.");
    }
}
