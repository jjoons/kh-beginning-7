package com.kh.rentcar_20230728.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "RENTCAR")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Car {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private int no;
  @Column(length = 20)
  private String name;
  @Column
  private int category;
  @Column
  private int price;
  @Column(name = "use_people")
  private int usePeople;
  @Column(length = 50)
  private String company;
  @Column(length = 50)
  private String img;
  @Column(length = 500)
  private String info;
}
