package com.kh.d20230727_problem_1.dto;

import com.kh.d20230727_problem_1.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StudentDTO {
  private String hakbun;
  private String passwd;
  private String name;
  private String address;
  private String tel;
  private String email;
  private int major;
  private int grade;
  private int hakjom;

  public Student toEntity() {
    return new Student(hakbun, passwd, name, address, tel, email, major, grade, hakjom);
  }
}
