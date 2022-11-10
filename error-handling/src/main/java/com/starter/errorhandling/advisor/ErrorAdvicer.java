package com.starter.errorhandling.advisor;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.starter.errorhandling.exception.BaseException;

import lombok.Data;

@ControllerAdvice
public class ErrorAdvicer {

  @Value("${APP_TYPE}")
  private String serverType;

  @Value("${app.version}")
  private String appVersion;

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
    ErrorResponse response = new ErrorResponse();

    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setServerType(serverType);
    response.setMessage(e.getMessage());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @Data
  public static class ErrorResponse {

    private int status;

    private LocalDateTime timestamp = LocalDateTime.now();

    private String serverType;

    private String version;

    private String path;

    private String method;

    private String message;

  }
}
