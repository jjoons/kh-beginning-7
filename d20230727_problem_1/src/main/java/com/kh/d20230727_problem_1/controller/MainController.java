package com.kh.d20230727_problem_1.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.kh.d20230727_problem_1.constant.CommonConstant;
import com.kh.d20230727_problem_1.dao.StudentDAO;
import com.kh.d20230727_problem_1.util.CommonUtil;

@Controller
public class MainController {
  @Autowired
  private StudentDAO studentDAO;

  @GetMapping(value = "/")
  public String index() {
    return "redirect:/login";
  }

  private boolean isLogin(HttpSession session) {
    return ((String) session.getAttribute(CommonConstant.SESSION_ATTR_LOGIN_ID)) != null;
  }

  @GetMapping(value = "/login")
  public String login(HttpSession session) {
    if (this.isLogin(session)) {
      return "redirect:/enroll";
    }

    return "login";
  }

  @PostMapping(value = "/login")
  public String loginPost(String hakbun, String passwd, HttpSession session) {
    var student = this.studentDAO.login(hakbun, passwd);

    if (student == null) {
      return "redirect:/login";
    }

    session.setAttribute(CommonConstant.SESSION_ATTR_LOGIN_ID, student.getHakbun());
    return "redirect:/enroll";
  }

  @GetMapping(value = "/enroll")
  public String enroll(HttpSession session, Model model) {
    if (!this.isLogin(session)) {
      return "redirect:/login";
    }

    var sessionHakbun = (String) session.getAttribute(CommonConstant.SESSION_ATTR_LOGIN_ID);
    var student = this.studentDAO.getStudentByHakbun(sessionHakbun);
    var majorName = CommonUtil.getMajor(student.getMajor());

    model.addAttribute("student", student);
    model.addAttribute("majorName", majorName);

    return "enroll";
  }
}
