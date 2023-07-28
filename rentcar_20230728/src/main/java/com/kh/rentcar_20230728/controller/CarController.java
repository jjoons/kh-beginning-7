package com.kh.rentcar_20230728.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {
  @GetMapping
  public ResponseEntity<Object> getAll() {

  }
}
