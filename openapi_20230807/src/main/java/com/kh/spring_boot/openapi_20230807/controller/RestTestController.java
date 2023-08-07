package com.kh.spring_boot.openapi_20230807.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 공공데이터를 가져올 때 데이터를 주고 받는 내용(XML, JSON)이 있기 때문에
 * 그냥 Controller
 */

@RestController
public class RestTestController {
  @Value(value = "${OPENDATA_API_KEY_ENCODED: ''}")
  private String encodedApiKey;

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
  public ResponseEntity openapi() {
    StringBuffer result = new StringBuffer();
    int pageNo = 1;

    /*
     * 필요 요소와 URL 저장
     * 맨 처음에는 요청 URL
     */
    try {
      String apiUrl = "http://apis.data.go.kr/6260000/FoodService/getFoodKr?"
          + "serviceKey=" + this.encodedApiKey
          + "&numOfRows=10"
          + "&pageNo=" + pageNo
          + "&resultType=xml";

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
       * 
       * 프로토콜이 "http://" 인 경우 반환된 객체를 HttpURLConnection 객체로 캐스팅할 수 있다
       */
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

      // 실제 데이터 전송할 때 전송 방식
      urlConnection.setRequestMethod("GET");

      // 실제 연결
      // urlConnection.connect();

      /*
       * 응답 (데이터를 받아온다)
       * Input, OutputStream
       * Stream
       *   - 데이터가 연속적으로 존재한다는 것을 표현한 객체
       *   - 바이트로 데이터를 전달하기 때문에 스트림도 byte로 연속된 집합이라고 볼 수 있다
       *   - 사용자의 키보드 입력, 파일 데이터, HTTP 송수신 데이터 등이 스트림
       * 
       * InputStream
       *   자바 프로그램 안으로 데이터를 가지고 온다
       *   1 byte의 int 형 ASCII 코드로 저장
       * 
       * InputStreamReader
       *   byte 대신 char 형태로 읽을 수 있게 ASCII 코드가 아닌 문자열로 출력할 수 있다
       *   String 객체로 변환할 수 있다
       *   InputStream 인자로 받아와서 만들어진다
       */
      BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());

      // 실제 응답받은 데이터 읽기
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

      String returnLine;
      /*
       * <xmp></xmp>
       *   문자, 숫자, 태그 소스든 그대로 출력할 수 있게 도와주는 태그
       */
      result.append("<xmp>");
      /*
       * 데이터를 응답받아서 안에 있는 내용들을 꺼내야 한다
       * 꺼낼 때 데이터가 없으면 null을 반환한다
       * BufferedReader.readLine()
       */

      while ((returnLine = bufferedReader.readLine()) != null) {
        result.append(returnLine + "\n");
      }

      // URL 연결 끊기 (닫기)
      urlConnection.disconnect();
      result.append("");

    } catch (IOException e) {
      e.printStackTrace();
    }

    return ResponseEntity.status(HttpStatus.OK).body(result + "</xmp>");
  }

  /*
   * JSON key와 value를 뽑아오기 위해 필요한 라이브러리가 있다
   */
  @GetMapping(value = "/jsonapi")
  public String openapiJson() {
    StringBuffer result = new StringBuffer();
    int pageNo = 1;
    String jsonPrintString = null;

    try {
      String apiUrl = "http://apis.data.go.kr/6260000/FoodService/getFoodKr?"
          + "serviceKey=" + this.encodedApiKey
          + "&numOfRows=10"
          + "&pageNo=" + pageNo;

      URL url = new URL(apiUrl);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.connect();

      /*
       * 중간에 데이터를 임시 저장 공간인 버퍼에 저장한다
       * 저장한 내용을 한꺼번에 가지고 들어온다
       * 1 byte 씩 가져오면 속도가 느리고 데이터의 용량이 크면 시간이 꽤 오래 걸린다
       */
      BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));

      String returnLine;
      while ((returnLine = bufferedReader.readLine()) != null) {
        result.append(returnLine + "\n");
      }

      /*
       * JSON 파일을 스프링 부트에서 사용할 수 있도록 특정값을 가지고 오는 내용 작성
       * 
       * JsonParser 객체의 도움을 받는다
       *   1. JsonParser 객체 생성
       *   2. reader를 이용해서 JSON 파일을 읽어온다
       *   3. JSON 코드가 [] (Array)로 감싸고 있을 경우 List 형식으로 index 값으로 불러온다
       *   4. JSON 코드가 {} (Object)로 감싸고 있을 경우
       *     Map 형식(Key: Value)으로 저장되어 있는 값을 불러온다
       */
      JSONObject jsonObject = XML.toJSONObject(result.toString());
      jsonPrintString = jsonObject.toString();

      urlConnection.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return jsonPrintString;
  }
}
