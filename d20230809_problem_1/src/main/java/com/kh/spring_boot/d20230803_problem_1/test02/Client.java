package com.kh.spring_boot.d20230803_problem_1.test02;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
  private String id;
  private String pw;
  private String name;

  public void printInfo() {
    System.out.println("id(" + id + "), pw(" + pw + ")");
  }
}
