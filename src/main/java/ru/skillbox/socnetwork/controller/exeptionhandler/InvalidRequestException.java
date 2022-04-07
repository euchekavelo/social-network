package ru.skillbox.socnetwork.controller.exeptionhandler;

public class InvalidRequestException extends Exception {

    public InvalidRequestException(String message) {
        super(message);
    }

}
