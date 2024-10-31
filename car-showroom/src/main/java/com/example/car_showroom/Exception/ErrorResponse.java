package com.example.car_showroom.exception;

import java.sql.Timestamp;

public class ErrorResponse {

    private Timestamp timestamp;
    private Integer status;
    private String error;
    private String message;
    private String data;
    private String uuid;
    private String errorCode;
    public ErrorResponse() {

    }

    public ErrorResponse(Timestamp timestamp, String error, Integer status, String message) {
        this.timestamp = timestamp;
        this.error = error;
        this.status = status;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
