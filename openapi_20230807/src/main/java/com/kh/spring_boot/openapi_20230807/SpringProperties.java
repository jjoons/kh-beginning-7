package com.kh.spring_boot.openapi_20230807;

import org.springframework.beans.factory.annotation.Value;

public class SpringProperties {
  @Value("${NAVER_DEVELOPER_API_CLIENT_ID}")
  public static String NAVER_DEVELOPER_API_CLIENT_ID;
  @Value("${NAVER_DEVELOPER_API_CLIENT_SECRET}")
  public static String NAVER_DEVELOPER_API_CLIENT_SECRET;
  @Value("${NAVER_CLOUD_PLATFORM_CLIENT_ID}")
  public static String NAVER_CLOUD_PLATFORM_CLIENT_ID;
  @Value("${NAVER_CLOUD_PLATFORM_CLIENT_SECRET}")
  public static String NAVER_CLOUD_PLATFORM_CLIENT_SECRET;
}
