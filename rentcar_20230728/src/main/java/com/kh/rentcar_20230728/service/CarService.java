package com.kh.rentcar_20230728.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kh.rentcar_20230728.entity.Car;
import com.kh.rentcar_20230728.repository.CarRepository;

@Service
public class CarService {
  @Autowired
  private CarRepository carRepository;

  public List<Car> getAllCar() {
    return this.carRepository.findAll();
  }
}
