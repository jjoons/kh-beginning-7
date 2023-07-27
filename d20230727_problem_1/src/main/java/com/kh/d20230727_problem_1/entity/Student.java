package com.kh.d20230727_problem_1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
public class Student {
  @Id
  @GeneratedValue
  private String hakbun;
  @Column
  private String passwd;
  @Column
  private String name;
  @Column
  private String address;
  @Column
  private String tel;
  @Column
  private String email;
  @Column
  private int major;
  @Column
  private int grade;
  @Column
  private int hakjom;
}
