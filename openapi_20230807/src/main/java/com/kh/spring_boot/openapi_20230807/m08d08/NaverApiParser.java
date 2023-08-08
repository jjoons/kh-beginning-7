package com.kh.spring_boot.openapi_20230807.m08d08;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NaverApiParser {
  private static final SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

  /*
   * API에서 가져온 문자열 데이터를 JSON 타입으로 파싱하는 메소드
   */
  public static List<Product> parseShop(String json) throws ParseException {
    List<Product> list = new ArrayList();

    // 객체 생성
    JSONParser jsonParser = new JSONParser();
    try {
      // parse(): JSON 문자열을 파싱해 JSONObject 또는 JSONArray 객체로 변환한다
      JSONObject rootObj = (JSONObject) jsonParser.parse(json);

      // 여러 가지 상품이 오는데 배열 형태로 저장해야 한다
      JSONArray array = (JSONArray) rootObj.get("items");

      for (int i = 0; i < array.size(); i++) {
        JSONObject obj = (JSONObject) array.get(i);

        String title = toString(obj, "title");
        String link = toString(obj, "link");
        String image = toString(obj, "image");
        int lprice = toInt(obj, "lprice");
        int hprice = toInt(obj, "hprice");
        String mallName = toString(obj, "mallName");
        String productId = toString(obj, "productId");
        String productType = toString(obj, "productType");
        String brand = toString(obj, "brand");
        String maker = toString(obj, "maker");
        String category1 = toString(obj, "category1");
        String category2 = toString(obj, "category2");
        String category3 = toString(obj, "category3");
        String category4 = toString(obj, "category4");

        Product product = Product.builder()
            .title(title)
            .link(link)
            .image(image)
            .lprice(lprice)
            .hprice(hprice)
            .mallName(mallName)
            .productId(productId)
            .productType(productType)
            .brand(brand)
            .maker(maker)
            .category1(category1)
            .category2(category2)
            .category3(category3)
            .category4(category4)
            .build();

      }
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return list;
  }

  private static String toString(JSONObject obj, String tag, boolean isRemoveTag) {
    /*
     * 문자에 특수 문자들(태그)이 들어있는 경우에는 true, false 값을 이용해서
     * 데이터 안에 특수 문자들을 제거하는 ㅁ함수를 따로 실행해야해서 매개 변수를 하나 더 줬다
     */
    String text = toString(obj, tag);

    if (isRemoveTag && text != null && !text.isEmpty()) {
      return removeTag(text);
    }

    return text;
  }

  private static String removeTag(String text) {
    if (text == null || text.isEmpty()) {
      return text;
    }

    text = text.replaceAll("<br>", "\n");
    text = text.replaceAll("&gt;", ">");
    text = text.replaceAll("&lt;", "<");
    text = text.replaceAll("&quot;", "");
    text = text.replaceAll("&nbsp;", " ");
    text = text.replaceAll("&apos;", "\n");

    return text.replaceAll("<(/)?([a-zA-Z])(\\s[a-zA-Z]=[^>])?(\\s)(/)?>", "");
  }

  private static String toString(JSONObject obj, String tag) {
    return (String) obj.get(tag);
  }

  private static int toInt(JSONObject obj, String tag) {
    try {
      return Integer.parseInt(toString(obj, tag));
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  public static Date toDate(JSONObject obj, String tag) {
    try {
      return sdf.parse(toString(obj, tag));
    } catch (java.text.ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
}
