package org.acme.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ServiceException extends WebApplicationException {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Response response) {
        super(response);
    }

    public ServiceException(String message, Response response) {
        super(message, response);
    }

    public ServiceException(int status) {
        super(status);
    }

    public ServiceException(String message, int status) {
        super(message, status);
    }

    public ServiceException(Response.Status status) {
        super(status);
    }

    public ServiceException(String message, Response.Status status) {
        super(message, status);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause, Response response) {
        super(cause, response);
    }

    public ServiceException(String message, Throwable cause, Response response) {
        super(message, cause, response);
    }

    public ServiceException(Throwable cause, int status) {
        super(cause, status);
    }

    public ServiceException(String message, Throwable cause, int status) {
        super(message, cause, status);
    }

    public ServiceException(Throwable cause, Response.Status status) throws IllegalArgumentException {
        super(cause, status);
    }

    public ServiceException(String message, Throwable cause, Response.Status status) throws IllegalArgumentException {
        super(message, cause, status);
    }
}
