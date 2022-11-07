package com.starter.errorhandling.advisor;

import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.starter.errorhandling.exception.BaseException;

import lombok.Data;

@ControllerAdvice(basePackages = "com.starter.errorhandling.controller")
public class ErrorAdvicer extends ResponseEntityExceptionHandler {

  @Value("${APP_TYPE}")
  private String serverType;

  @Value("${app.version}")
  private String appVersion;

  @ResponseBody
  @ExceptionHandler(BaseException.class)
  public ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
    HttpStatus status = getStatus(request);

    ErrorResponse response = new ErrorResponse();
    response.setStatus(status.value());
    response.setServerType(serverType);
    response.setVersion(appVersion);
    response.setPath(request.getHeader("path"));
    response.setMethod(request.getMethod());
    response.setMessage(ex.getMessage());

    return new ResponseEntity<>(response, status);
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer code = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    HttpStatus status = HttpStatus.resolve(code);
    return (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
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
