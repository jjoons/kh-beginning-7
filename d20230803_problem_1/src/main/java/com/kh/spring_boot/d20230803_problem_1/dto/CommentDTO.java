package com.kh.spring_boot.d20230803_problem_1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kh.spring_boot.d20230803_problem_1.entity.Comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
  private Long id;
  private String nickname;
  private String body;
  @JsonProperty(value = "article_id")
  private Long articleId;

  /*
   * 커맨드 객체를 사용해서 JSON 데이터를 받아 저장하려 할 경우
   * JSON 데이터의 key 스네이크 표기법을 사용하고
   * 커맨드 객체는 카멜 표기법을 사용하면 데이터를 받지 못 한다
   * @JsonProperty("JSON 키")
   * 어노테이션을 사용하면 JSON의 key와 커맨드 객체의 필드 이름이 다르더라도
   * 데이터를 받을 수 있다
   */

  public static CommentDTO createCommentDTO(Comment comment) {
    return CommentDTO.builder()
        .id(comment.getId())
        .nickname(comment.getNickname())
        .body(comment.getBody())
        .articleId(comment.getArticle().getId())
        .build();
  }
}
