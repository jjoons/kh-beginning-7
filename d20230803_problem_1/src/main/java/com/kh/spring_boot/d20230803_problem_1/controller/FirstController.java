package com.kh.spring_boot.d20230803_problem_1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
  @GetMapping(value = "/")
  public String index() {
    return "redirect:/board";
  }
}
