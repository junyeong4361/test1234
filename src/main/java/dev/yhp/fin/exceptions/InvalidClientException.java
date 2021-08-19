package dev.yhp.fin.exceptions;

public class InvalidClientException extends Exception {
    public InvalidClientException() {
    }

    public InvalidClientException(String message) {
        super(message);
    }
}