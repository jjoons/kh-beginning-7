package com.kh.d20230727_problem_1.dto;

import com.kh.d20230727_problem_1.entity.MySubjectView;
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
public class MySubjectViewDTO {
  private int subjectNum;
  private String subjectName;
  private String professorName;
  private int hakjom;
  private String major;
  private int grade;

  public MySubjectView toEntity() {
    return new MySubjectView(subjectNum, subjectName, professorName, hakjom, major, grade);
  }
}
