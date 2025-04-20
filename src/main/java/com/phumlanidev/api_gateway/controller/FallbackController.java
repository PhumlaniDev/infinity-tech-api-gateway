package com.phumlanidev.api_gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class FallbackController {

  @GetMapping("/fallback/product")
  public ResponseEntity<String> fallbackProduct() {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body("Product Service is currently unavailable. Please try again later.");
  }
}
