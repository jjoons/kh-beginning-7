package com.kh.spring_boot.d20230803_problem_1.test02;

public class Member {
  private int a = 0;

  public int add(int val) {
    this.a += val;
    return this.a;
  }

  public void print() {
    System.out.println(a);
  }
}
