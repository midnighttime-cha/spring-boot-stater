package com.starter.errorhandling.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.starter.errorhandling.exception.BaseException;

@RestController
@RequestMapping("/")
public class TestController {

  @GetMapping("/test")
  public ResponseEntity<String> test(@RequestParam("id") String request) throws BaseException {
    String response = "Test";
    return ResponseEntity.ok(response);
  }

}
