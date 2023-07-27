package com.kh.d20230727_problem_1.bean;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.hikari")
  DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }
}
