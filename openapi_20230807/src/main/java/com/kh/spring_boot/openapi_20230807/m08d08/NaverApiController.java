package com.kh.spring_boot.openapi_20230807.m08d08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 네이버 개발자 센터에서 검색 기능 구현
 *   1. 네이버 개발자 센터 회원가입
 *   2. 어플리케이션 등록
 */
@RestController
public class NaverApiController {
  @Value("${NAVER_DEVELOPER_API_CLIENT_ID}")
  private String naverClientId;
  @Value("${NAVER_DEVELOPER_API_CLIENT_SECRET}")
  private String naverClientSecret;

  @GetMapping(value = "/naver_api/news_search")
  public ResponseEntity<Object> testMain() {
    String clientId = this.naverClientId; //애플리케이션 클라이언트 아이디
    String clientSecret = this.naverClientSecret; //애플리케이션 클라이언트 시크릿

    String text = null;
    try {
      // 검색어를 URL 뒤에 붙여서 보낼 때 인코딩
      text = URLEncoder.encode("잼버리", "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("검색어 인코딩 실패", e);
    }

    String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text; // JSON 결과
    //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과

    // 요청하기 전에 어떤 id로 접속할 지 헤더 파일을 만들어서 저장한다
    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put("X-Naver-Client-Id", clientId);
    requestHeaders.put("X-Naver-Client-Secret", clientSecret);
    String responseBody = get(apiURL, requestHeaders);

    // 검색한 내용을 출력한다
    // System.out.println(responseBody);

    // JSON 파싱

    /*
     * JSONObject: Map 형식으로 데이터가 감싸져 있을 경우 {}
     * JSONArray: 배열 형식으로 데이터가 감싸져 있을 경우 []
     * 
     * 가장 큰 JSONObject를 가져온다
     */
    JSONObject jObject = new JSONObject(responseBody);

    // 배열을 가져온다
    JSONArray jArray = jObject.getJSONArray("items");

    /* jArray.forEach(t -> {
      JSONObject obj = (JSONObject) t;
      
      String title = obj.getString("title");
      String originallink = obj.getString("originallink");
      String link = obj.getString("title");
      String description = obj.getString("title");
    
      System.out.println("title: " + title);
      System.out.println("description: " + description);
    }); */
    // jArray.getJSONObject(0);

    // 배열의 모든 아이템을 출력한다

    return ResponseEntity.status(HttpStatus.OK)
        .header("Content-Type", "application/json")
        .body(responseBody);
  }

  private static String get(String apiUrl, Map<String, String> requestHeaders) {
    HttpURLConnection con = connect(apiUrl);
    try {
      // 전송 방식을 설정하는 메소드
      con.setRequestMethod("GET");
      for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
        con.setRequestProperty(header.getKey(), header.getValue());
      }

      /*
       * 응답
       *   실제 전송이 잘 되서 응답이 온다면 200 정상
       */
      int responseCode = con.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
        return readBody(con.getInputStream());
      } else { // 오류 발생
        return readBody(con.getErrorStream());
      }
    } catch (IOException e) {
      throw new RuntimeException("API 요청과 응답 실패", e);
    } finally {
      con.disconnect();
    }
  }

  private static HttpURLConnection connect(String apiUrl) {
    try {
      URL url = new URL(apiUrl);
      return (HttpURLConnection) url.openConnection();
    } catch (MalformedURLException e) {
      throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
    } catch (IOException e) {
      throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
    }
  }

  private static String readBody(InputStream body) {
    InputStreamReader streamReader = new InputStreamReader(body);

    try (BufferedReader lineReader = new BufferedReader(streamReader)) {
      /*
       * 변경 가능한 객체 한 번 만들어 놓으면 추가, 삭제 및 수정이 가능하다
       * 
       * 응답받은 내용을 꺼내서 저장하기 위한 변수
       */
      StringBuilder responseBody = new StringBuilder();

      String line;
      while ((line = lineReader.readLine()) != null) {
        responseBody.append(line);
      }

      return responseBody.toString();
    } catch (IOException e) {
      throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
    }
  }
}
