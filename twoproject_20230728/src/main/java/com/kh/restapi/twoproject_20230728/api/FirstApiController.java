package com.kh.restapi.twoproject_20230728.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

// @Controller // 뷰 페이지로 연결
@RestController // 실제 데이터들을 반환 (JSON) (문자를 반환할 때도 있음)
@Slf4j
public class FirstApiController {
  // URL 요청이 들어오면 매핑
  @GetMapping(value = "/api/hello")
  public String hello() {
    log.info("FirstApiController.hello()");
    return "hello world";
  }
}

/*
 * 헤더는 편지 봉투
 * 바디는 편지 봉투 안의 내용물
 */
