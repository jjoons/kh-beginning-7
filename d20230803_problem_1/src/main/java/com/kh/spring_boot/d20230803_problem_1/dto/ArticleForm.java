package com.kh.spring_boot.d20230803_problem_1.dto;

import com.kh.spring_boot.d20230803_problem_1.entity.Article;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleForm {
  private Long id;
  private String title;
  private String content;

  public Article toEntity() {
    return Article.builder()
        .id(id)
        .title(title)
        .content(content)
        .build();
  }
}
