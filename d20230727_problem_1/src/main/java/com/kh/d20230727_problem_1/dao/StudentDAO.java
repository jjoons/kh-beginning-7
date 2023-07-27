package com.kh.d20230727_problem_1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.kh.d20230727_problem_1.entity.Student;
import com.kh.d20230727_problem_1.repository.StudentRepository;

@Repository
public class StudentDAO {
  @Autowired
  private StudentRepository studentRepository;

  public Student getStudentById(int id) {
    return this.studentRepository.findById(id).orElse(null);
  }

  public Student login(String hakbun, String passwd) {
    return this.studentRepository.findByHakbunAndPasswd(hakbun, passwd);
  }

  public Student getStudentByHakbun(String hakbun) {
    return this.studentRepository.findByHakbun(hakbun);
  }
}
