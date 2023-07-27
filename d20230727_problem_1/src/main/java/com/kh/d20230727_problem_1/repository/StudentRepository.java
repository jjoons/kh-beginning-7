package com.kh.d20230727_problem_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kh.d20230727_problem_1.entity.Student;



public interface StudentRepository extends JpaRepository<Student, Integer> {
  Student findByHakbunAndPasswd(String hakbun, String passwd);

  Student findByHakbun(String hakbun);
}
