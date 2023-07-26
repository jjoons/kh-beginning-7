package com.kh.first_project_20230726.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * view Templates
 *   화면을 담당하는 기술
 *   웹 페이지를 하나의 틀로 만들고 여기에 변수를 삽입하게 한다
 *   틀이 되는 페이지가 변수의 값에 따라서 수 많은 페이지로 바뀔 수 있다
 *   그 도구 중 하나가 Mustache
 */


@Controller
public class FirstController {
  /*
   * GET 요청으로 /hi 라는 요청이 들어오면 메소드 실행
   */
  @GetMapping("/hi")
  // viewpage로 데이터를 넘길 때 사용할 model 인터페이스 객체를 인수로 넣어준다
  public String niceToMeetYou(Model model) {
    model.addAttribute("username", "홍길동");

    // viewpage 이름
    return "greetings";
  }

  @GetMapping("bye")
  public String seeYouNext(Model model) {
    model.addAttribute("username", "ㅁㄴㅇ");

    return "goodbye";
  }
}
