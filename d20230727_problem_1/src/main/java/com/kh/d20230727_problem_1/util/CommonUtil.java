package com.kh.d20230727_problem_1.util;

public class CommonUtil {
  public static String getMajor(int majorNum) {
    String major = "";

    switch (majorNum) {
      case 1:
        major = "컴공";
        break;
      case 2:
        major = "전자공학";
        break;
      case 3:
        major = "응용";
        break;
    }

    return major;
  }
}
