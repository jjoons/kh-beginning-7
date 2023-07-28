package com.kh.rentcar_20230728.dto;

import com.kh.rentcar_20230728.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CarDTO {
  private int no;
  private String name;
  private int category;
  private int price;
  private int usePeople;
  private String company;
  private String img;
  private String info;

  public Car toEntity() {
    return Car
        .builder()
        .no(no)
        .name(name)
        .category(category)
        .price(price)
        .usePeople(usePeople)
        .company(company)
        .img(img)
        .info(info)
        .build();
  }
}
