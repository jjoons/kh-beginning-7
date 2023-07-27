package com.kh.d20230727_problem_1.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kh.d20230727_problem_1.dao.StudentDAO;

@RestController
public class MainRestController {
  @Autowired
  private StudentDAO studentDAO;

  @GetMapping(value = "/logout")
  public ResponseEntity<Object> logout(HttpSession session) {
    session.invalidate();

    return ResponseEntity.ok().build();
  }
}
