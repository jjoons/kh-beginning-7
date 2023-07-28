package com.kh.rentcar_20230728.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kh.rentcar_20230728.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

}
