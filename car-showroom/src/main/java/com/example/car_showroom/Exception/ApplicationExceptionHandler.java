package com.example.car_showroom.exception;

import com.example.car_showroom.constant.ApplicationConstants;
import com.example.car_showroom.constant.ErrorCodeConstant;
import com.example.car_showroom.constant.ErrorMessageConstant;
import com.example.car_showroom.service.MessageService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is responsible in catching Exceptions and generating a proper response
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private HttpServletRequest request;

    /**
     * This method handles when Exception occurred and returns error response
     *
     * @param exception - Exception obj
     * @return - error message
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Exception exception) {
        logger.error("Error Occurred {}", exception);
        String errorMessage = messageService.getMessage(getSelectedLanguage(), ErrorMessageConstant.ERROR_CONTACT_ADMINISTRATOR);
        return this.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }


    @ExceptionHandler({DataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(DataAccessException exception) {
        logger.error("Error Occurred {}", exception);
        String errorMessage = messageService.getMessage(getSelectedLanguage(), ErrorMessageConstant.SQL_ERROR_CONTACT_ADMINISTRATOR);
        return this.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    @ExceptionHandler({HibernateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(HibernateException exception) {
        logger.error("Error Occurred {}", exception);
        String errorMessage = messageService.getMessage(getSelectedLanguage(), ErrorMessageConstant.SQL_ERROR_CONTACT_ADMINISTRATOR);
        return this.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    /**
     * This method handles when CustomException occurred and returns error response from the properties file
     *
     * @param exception - CustomException obj
     * @return - error message
     */
    @ExceptionHandler({CustomException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(CustomException exception) {
        logger.error("Custom Error Occurred {} Language {} ", exception, exception.getLanguage());
        //get the error msg from property file
        String errorMessage = messageService.getMessage(exception.getLanguage(), exception.getMessage(), exception.getData());
        return this.buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, exception.getErrorCode());
    }


    /**
     * This method is used to handle any required/valid  argument not passed in the request.
     *
     * @param exception - method argumet not valid exception caught on request.
     * @return - Error response with proper  http status.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(MethodArgumentNotValidException exception) {
        logger.error("MethodArgumentNotValid Exception Occurred ", exception);
        List<String> errorMessages = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(error -> errorMessages.add(((DefaultMessageSourceResolvable) error.getArguments()[0]).getCode() + " " + error.getDefaultMessage()));
        String errorMessage = errorMessages.toString().replaceAll("]", "");
        errorMessage = errorMessage.replaceAll("\\[", "");
        logger.error("Errors : " + errorMessage);
        return this.buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, ErrorCodeConstant.BAD_REQUEST_S1);
    }

    /**
     * this method is used to handle incorrect username or password exception
     * @param exception
     * @return
     */
    @ExceptionHandler({BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleBadCredentialsException(BadCredentialsException exception) {
        logger.error("Incorrect credentials  Occurred {}", exception);
        //get the error msg from property file
        String errorMessage = messageService.getMessage(getSelectedLanguage(), ErrorMessageConstant.INCORRECT_CREDENTIALS);
        return this.buildErrorResponse(HttpStatus.UNAUTHORIZED, errorMessage);
    }

    /**
     * this method to handle unauthorized access exception
     * @param exception
     * @return
     */
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleAccessDeniedException(AccessDeniedException exception) {
        logger.error("UNAUTHORIZED access {}", exception);
        //get the error msg from property file
        String errorMessage = messageService.getMessage(getSelectedLanguage(), ErrorMessageConstant.NOT_AUTHORIZED);
        return this.buildErrorResponse(HttpStatus.UNAUTHORIZED, errorMessage);
    }


    /**
     * this method is to handle invalid JWT tokens
     * @param exception
     * @return
     */

    @ExceptionHandler({SignatureException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleSignatureException(SignatureException exception) {
        logger.error("Invalid JWT token Error Occurred {}", exception);
        //get the error msg from property file
        String errorMessage = messageService.getMessage(getSelectedLanguage(), ErrorMessageConstant.INVALID_JWT);
        return this.buildErrorResponse(HttpStatus.UNAUTHORIZED, errorMessage);
    }

    /**
     * this method is to handle Expired JWT tokens
     * @param exception
     * @return
     */
    @ExceptionHandler({ExpiredJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleExpiredJwtException(ExpiredJwtException exception) {
        logger.error("Expired JWT token Occurred {} Language {} ", exception);
        //get the error msg from property file
        String errorMessage = messageService.getMessage(getSelectedLanguage(), ErrorMessageConstant.EXPIRED_JWT);
        return this.buildErrorResponse(HttpStatus.UNAUTHORIZED, errorMessage);
    }

    /**
     * This method is used to create error response
     *
     * @param httpStatus   - http error code
     * @param errorMessage - error message from message properties
     * @return - error response
     */
    private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String errorMessage) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setError(httpStatus.getReasonPhrase());
        errorResponse.setMessage(errorMessage);
        return errorResponse;
    }

    private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String errorMessage, String errorCode) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        errorResponse.setStatus(httpStatus.value());
        errorResponse.setError(httpStatus.getReasonPhrase());
        errorResponse.setMessage(errorMessage);
        if (errorCode != null) {
            errorResponse.setErrorCode(errorCode);
        }
        return errorResponse;
    }

    /**
     * Method to get the language from the input request
     *
     * @return - selected language
     */
    private String getSelectedLanguage() {
        String language = ApplicationConstants.ENGLISH;
        if (request.getHeader(ApplicationConstants.HEADER_LANGUAGE) != null) {
            language = request.getHeader(ApplicationConstants.HEADER_LANGUAGE);
        } else if (request.getParameterMap().containsKey(ApplicationConstants.HEADER_LANGUAGE)) {
            language = request.getParameter(ApplicationConstants.HEADER_LANGUAGE);
        }
        return language;
    }

}
