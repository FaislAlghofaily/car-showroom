package com.example.car_showroom.service;

public interface MessageService {
    String getMessage(String language, String key);

    String getMessage(String language, String messageKey, Object[] data);
}

