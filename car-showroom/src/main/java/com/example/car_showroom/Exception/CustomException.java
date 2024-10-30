package com.example.car_showroom.Exception;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Set;

public class CustomException extends RuntimeException {

    private String language;
    private Set<String> errorMessages;
    private HttpStatus httpStatus;
    private Object[] data;
    private String response;
    private String errorCode;
    private Boolean showErrorCode;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String language, String message) {
        super(message);
        this.language = language;
    }

    public CustomException(String language, String message, HttpStatus status) {
        super(message);
        this.language = language;
        this.errorMessages = Collections.singleton(message);
        this.httpStatus = status;
    }

    public CustomException(String language, String message, HttpStatus status, String errorCode) {
        super(message);
        this.language = language;
        this.errorMessages = Collections.singleton(message);
        this.httpStatus = status;
        this.errorCode = errorCode;
    }

    public CustomException(String language, String message, HttpStatus status, Object[] data) {
        super(message);
        this.language = language;
        this.httpStatus = status;
        this.data = data;
    }

    public CustomException(String language, Set<String> errorMessages, HttpStatus status) {
        super(errorMessages.toString());
        this.language = language;
        this.errorMessages = errorMessages;
        this.httpStatus = status;
    }

    public CustomException(String language, Set<String> errorMessages, HttpStatus status, String errorCode) {
        super(errorMessages.toString());
        this.language = language;
        this.errorMessages = errorMessages;
        this.httpStatus = status;
        this.errorCode = errorCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Set<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Boolean getShowErrorCode() {
        return showErrorCode;
    }

    public void setShowErrorCode(Boolean showErrorCode) {
        this.showErrorCode = showErrorCode;
    }
}
