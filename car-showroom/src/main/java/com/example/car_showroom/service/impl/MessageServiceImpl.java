package com.example.car_showroom.service.impl;


import com.example.car_showroom.constant.ApplicationConstants;
import com.example.car_showroom.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    @Autowired
    @Qualifier("customMessageSource")
    private MessageSource messageSource;

    @Override
    public String getMessage(String language, String messageKey) {
        return getMessage(language, messageKey, null);
    }

    @Override
    public String getMessage(String language, String messageKey, Object[] data) {
        language = language == null ? ApplicationConstants.ARABIC : language;
        logger.debug("Read the msg from property file for the msg key  {}", messageKey);
        LocaleContextHolder.setLocale(Locale.forLanguageTag(language));
        String message;
        try {
            message = messageSource.getMessage(messageKey, data, LocaleContextHolder.getLocale());
        } catch (Exception exception) {
            logger.debug("Unable to read the msg from property file {}", exception);
            message = messageKey;
        }
        return message;
    }
}
