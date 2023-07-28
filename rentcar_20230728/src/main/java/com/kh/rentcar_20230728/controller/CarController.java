package com.kh.rentcar_20230728.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.rentcar_20230728.entity.Car;
import com.kh.rentcar_20230728.service.CarService;

@RestController
public class CarController {
  @Autowired
  private CarService carService;

  @GetMapping(value = "/car")
  public ResponseEntity<List<Car>> getAll() {
    var list = this.carService.getAllCar();

    if (list == null) {
      return ResponseEntity.ok().build();
    }

    return ResponseEntity.ok(list);
  }

  @ExceptionHandler(value = {})
  public ResponseEntity<Object> exceptionHandler(Exception e) {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("error_msg", "오류 발생");
    map.put("error", e);

    return ResponseEntity.badRequest().body(map);
  }
}
