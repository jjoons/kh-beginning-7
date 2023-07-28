package com.kh.restapi.twoproject_20230728.dto;

import com.kh.restapi.twoproject_20230728.entity.Article;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class ArticleForm {
  private Long id;
  private String title;
  private String content;

  public Article toEntity() {
    Article article = new Article();
    article.setId(id);
    article.setTitle(title);
    article.setContent(content);

    return article;
  }
}
