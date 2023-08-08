package com.kh.spring_boot.openapi_20230807.m08d08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ShoppingInsightController {
  @Value(value = "${NAVER_DEVELOPER_API_CLIENT_ID}")
  private String naverApiId;
  @Value(value = "${NAVER_DEVELOPER_API_CLIENT_SECRET}")
  private String naverApiSecret;

  @GetMapping(value = "/shopping_insight")
  public ResponseEntity<Object> shoppingInsight() {
    var mapper = new ObjectMapper();
    var body = new StringBuilder();
    URL url = null;

    try {
      url = new URL("https://openapi.naver.com/v1/datalab/shopping/categories");
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().build();
    }

    HttpURLConnection con = null;

    try {
      con = (HttpURLConnection) url.openConnection();
      con.setDoOutput(true);
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("X-Naver-Client-Id", this.naverApiId);
      con.setRequestProperty("X-Naver-Client-Secret", this.naverApiSecret);
      con.setRequestMethod("POST");

      var writer = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
      var requestBody = new HashMap<String, Object>();
      var endDate = LocalDate.now();
      var startDate = endDate.minusYears(1L);

      requestBody.put("startDate", startDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
      requestBody.put("endDate", endDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
      requestBody.put("timeUnit", "month");
      var categories = new ArrayList<Map<String, Object>>();

      var category1 = new HashMap<String, Object>();
      category1.put("name", "패션의류");
      category1.put("param", new ArrayList<String>(List.<String>of("50000000")));

      var category2 = new HashMap<String, Object>();
      category2.put("name", "화장품/미용");
      category2.put("param", new ArrayList<String>(List.<String>of("50000002")));

      categories.add(category1);
      categories.add(category2);

      requestBody.put("category", categories);

      writer.write(mapper.writeValueAsString(requestBody));
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().build();
    }

    BufferedReader reader = null;

    try {
      int resCode = con.getResponseCode();
      if (resCode >= 200 && resCode <= 299) {
        reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
      } else {
        reader = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
      }

      String readLine = null;
      while ((readLine = reader.readLine()) != null) {
        body.append(readLine);
      }

      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().build();
    }

    var responseHeader = new HttpHeaders();
    responseHeader.add("Content-Type", "application/json; charset=UTF-8");

    return ResponseEntity.ok().headers(responseHeader).body(body);
  }
}
