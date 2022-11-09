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
    response.setMessage(e.getMessage());
    response.setStatus(HttpStatus.EXPECTATION_FAILED.value());

    return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
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
