package com.kh.d20230727_problem_1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class MySubjectView {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int subjectNum;
  @Column
  private String subjectName;
  @Column
  private String professorName;
  @Column
  private int hakjom;
  @Column
  private String major;
  @Column
  private int grade;
}
