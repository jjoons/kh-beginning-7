package com.kh.spring_boot.d20230803_problem_1.test02;

public class UserDAO {
  private User user;

  public UserDAO(User user) {
    this.user = user;
  }

  public void print() {
    this.user.print();
  }
}
