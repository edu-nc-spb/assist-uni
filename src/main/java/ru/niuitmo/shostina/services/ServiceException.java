package ru.niuitmo.shostina.services;

public class ServiceException extends Exception {
    public ServiceException(Throwable throwable) {
        super(throwable);
    }
}
