package com.kh.first_project_20230726.dto;

import com.kh.first_project_20230726.entity.Article;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * 롬복
 *   setter getter 자동으로 생성해 주는 어노테이션 사용
 */

@Setter
@Getter
@ToString
// 기본 생성자
//@NoArgsConstructor
// 모든 변수를 사용하는 생성자
@AllArgsConstructor
// equals() 메소드와 hashcode() 메소드
@EqualsAndHashCode
public class ArticleForm {
  private Long id;
  private String title;
  private String content;

  /*
   * DTO 클래스에 데이터를 Entity(테이블과 매핑되는 클래스)로 변환하는 메소드를 추가한다
   */
  public Article toEntity() {
    Article article = new Article();
    article.setId(id);
    article.setTitle(title);
    article.setContent(content);

    return article;
  }
}
