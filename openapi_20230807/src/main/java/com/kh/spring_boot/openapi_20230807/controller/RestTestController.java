package com.kh.spring_boot.openapi_20230807.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 공공데이터를 가져올 때 데이터를 주고 받는 내용(XML, JSON)이 있기 때문에
 * 그냥 Controller
 */

@RestController
public class RestTestController {
  @Value(value = "${OPENDATA_BUSAN_API_KEY_ENCODED}")
  private String apiKey;

  /*
   * 서버를 실행하면 웹 브라우저에서 URL을 작성하고 엔터를 누르면
   * DispatcherServlet이 모든 URL을 받는다
   * 전송할 때 GET, POST 구별해서 매핑시켜준다
   * openapi 메소드가 실행된다
   * 
   * 공공 데이터를 실행하기 위해서 필요한 작업
   *   1. https://www.data.go.kr/ 접속
   *   2. 회원가입 (나만의 고유 API Key)
   *   3. 
   */
  @GetMapping(value = "/apitest")
  public String openapi() {
    /*
     * 필요 요소와 URL 저장
     * 맨 처음에는 요청 URL
     */
    try {
      String apiUrl = "http://apis.data.go.kr/6260000/FoodService/getFoodKr?" + "serviceKey="
          + this.apiKey
          + "&numOfRows=10"
          + "&pageNo=1";

      /*
       * URL 객체를 생성하는 클래스
       * 문자열이 지정하는 자원에 대한 URL 객체 생성
       */
      /* 
      * URL 객체 만들면서 프로토콜, 통신 방법, 호스트 주소, 포트 번호, 파일 이름
      * 객체 생성
      */
      URL url = new URL(apiUrl);

      /*
       * URL 내용을 읽어오거나 URL에 GET, POST 방식으로 데이터를 전달할 때 사용
       * 웹 페이지나 서블릿에 데이터를 전달할 수 있다
       */
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

    } catch (IOException e) {
      e.printStackTrace();
    }

    return "";
  }
}
