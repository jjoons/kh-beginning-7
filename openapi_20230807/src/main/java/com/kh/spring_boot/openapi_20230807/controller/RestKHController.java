package com.kh.spring_boot.openapi_20230807.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestKHController {
  @Value("${OPENDATA_API_KEY}")
  private String apiKey;
  @Value("${OPENDATA_API_KEY_ENCODED}")
  private String encodedApiKey;

  @GetMapping(value = "/api/flight")
  public ResponseEntity<?> flight() {
    var responseString = new StringBuilder();

    try {
      var urlBuilder = new StringBuilder(
          "http://api.odcloud.kr/api/getAPRTPsgrCongestion/v1/aprtPsgrCongestion");

      var url = new URL(urlBuilder.toString());

      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestProperty("Authorization", "Infuser " + this.apiKey);
      var is = urlConnection.getInputStream();
      var isr = new InputStreamReader(is);
      var reader = new BufferedReader(isr);

      String res = null;
      while ((res = reader.readLine()) != null) {
        responseString.append(res);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    var responseEntity = ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_XML)
        .body(responseString.toString());

    return responseEntity;
  }
}
