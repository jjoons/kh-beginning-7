package com.kh.spring_boot.openapi_20230807.m08d08;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

import com.kh.spring_boot.openapi_20230807.SpringProperties;

public class ApiExamSearchNaverAPIController {
  private String clientId = SpringProperties.NAVER_DEVELOPER_API_CLIENT_ID;
  private String clientSecret = SpringProperties.NAVER_DEVELOPER_API_CLIENT_SECRET;

  public void a() {
    String apiUrl = "https://openapi.naver.com/v1/search/shop.json";
    // 검색어
    String query = "캠핑의자";
    String display = "12";
    String start = "1";
    String text = null;

    try {
      text = URLEncoder.encode(query, "UTF-8");
    } catch (Exception e) {
      System.err.println(e);
      throw new RuntimeException("", e);
    }

    StringBuilder url = new StringBuilder();
    url.append(apiUrl)
        .append("?query=" + query)
        .append("&display=" + display)
        .append("&start=" + start);

    Map<String, String> requestHeaders = new HashMap<>();
    requestHeaders.put("X-Naver-Client-Id", clientId);
    requestHeaders.put("X-Naver-Client-Secret", clientSecret);
    String responseBody = ApiExamSearchNaverAPI.get(url.toString(), requestHeaders);

    /*
     * 실제 파싱하는 클래스를 이용해서 데이터 가져오고 리스트에 저장하기
     */
    try {
      List<Product> list = NaverApiParser.parseShop(responseBody);

      for (Product item : list) {
        System.out.println(item);
      }
    } catch (ParseException e) {
    }
  }
}
