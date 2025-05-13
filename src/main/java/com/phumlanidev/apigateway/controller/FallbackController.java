package com.phumlanidev.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class FallbackController {

  @GetMapping("/fallback/product")
  public ResponseEntity<String> fallbackProduct() {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Product Service is currently unavailable. Please try again later.");
  }
}
