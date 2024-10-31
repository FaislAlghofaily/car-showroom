package com.example.car_showroom.exception;

public class CustomException extends RuntimeException {
    private String language;
    private Object[] data;
    private String response;
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String language, String message, Object[] data) {
        super(message);
        this.language = language;
        this.data = data;
    }

    public CustomException(String language, String message) {
        super(message);
        this.language = language;
    }

    public CustomException(String language, String message, String errorCode) {
        super(message);
        this.language = language;
        this.errorCode = errorCode;
    }

    public CustomException(String language, String message, String response, String errorCode) {
        super(message);
        this.language = language;
        this.response = response;
        this.errorCode = errorCode;
    }

    public CustomException(String language, String message, Object[] data, String errorCode) {
        super(message);
        this.language = language;
        this.data = data;
        this.errorCode = errorCode;
    }

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getErrorMessage() {
        return null != response ? response : getMessage();
    }
}
