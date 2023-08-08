package com.kh.spring_boot.openapi_20230807.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

// @Controller
@RestController
public class Clova_Sentiment {
  @Value("${NAVER_CLOUD_PLATFORM_CLIENT_ID}")
  private String clientId;
  @Value("${NAVER_CLOUD_PLATFORM_CLIENT_SECRET}")
  private String clientSecret;

  @GetMapping(value = "/sentiment")
  public String sentiment() {
    var mapper = new ObjectMapper();
    // String clientId = SpringProperties.NAVER_CLOUD_PLATFORM_CLIENT_ID;
    // String clientSecret = SpringProperties.NAVER_CLOUD_PLATFORM_CLIENT_SECRET;

    StringBuilder response = new StringBuilder();

    try {
      var contentMap = new LinkedHashMap<String, Object>();
      contentMap.put("content", "싸늘하다. 가슴에 비수가 날아와 꽂힌다.");
      var content = mapper.writeValueAsString(contentMap);
      System.out.println(content); // {"content":"싸늘하다. 가슴에 비수가 날아와 꽂힌다."}

      String apiUrl = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";
      /*
       * 주어진 URL 주소에 대해 새 URL 객체를 생성한다
       * URL 형식이 잘못된 경우에는 오류가 발생할 수 있다
       */
      URL url = new URL(apiUrl);

      HttpURLConnection con = (HttpURLConnection) url.openConnection();

      // 전송 방식
      con.setRequestMethod("POST");
      con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
      con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
      con.setRequestProperty("Content-Type", "application/json");

      // POST Request
      String postParams = "content=" + content;

      // URLConnection이 서버에 데이터를 보내는데 사용할 수 있는지 여부를 설정한다
      con.setDoOutput(true);
      con.setDoInput(true);

      // DataOutputStream wr = new DataOutputStream(con.getOutputStream());
      // wr.writeBytes(postParams);
      OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
      wr.write(content);
      wr.flush();
      wr.close();

      int responseCode = con.getResponseCode();
      BufferedReader br;
      if (responseCode == 200) {
        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
      } else {
        br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
      }

      String line = null;
      while ((line = br.readLine()) != null) {
        response.append(line);
      }
      br.close();

      System.out.println(response.toString());

    } catch (IOException e) {
      e.printStackTrace();
    }

    return response.toString();
  }
}
