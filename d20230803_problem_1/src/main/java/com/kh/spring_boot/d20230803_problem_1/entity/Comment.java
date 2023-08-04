package com.kh.spring_boot.d20230803_problem_1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kh.spring_boot.d20230803_problem_1.dto.CommentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(length = 50, nullable = false)
  private String nickname;
  @Column(length = 4096, nullable = false)
  private String body;
  // @Column(name = "article_id", nullable = false)
  // 댓글 Entity(Comment) 여러 개가 하나의 메인글(Article)에 연관된다
  @ManyToOne
  // article_id 컬럼에 article의 대표값(기본키) 저장
  @JoinColumn(name = "article_id")
  private Article article;

  // CommentDTO를 Entity로 변환하는 메소드 (댓글, 메인글)
  public static Comment createComment(CommentDTO dto, Article article) {
    /*
     * 예외 발생
     *   댓글 id는 DB가 자동으로 붙여주기 때문에 id가 넘어오는 경우 예외를 발생시킨다
     */
    if (dto.getId() != null) {
      throw new IllegalArgumentException("댓글 생성 실패! 댓글 id는 없어야 한다");
    }

    // 댓글을 생성하기 위해 요청한 id와 DB에 저장된 id가 다른 경우 예외를 발생시킨다
    if (dto.getArticleId().equals(article.getId())) {
      throw new IllegalArgumentException("댓글 생성 실패! 게시글 id가 잘못되었습니다");
    }

    return Comment.builder()
        .id(dto.getId())
        .nickname(dto.getNickname())
        .body(dto.getBody())
        .article(article)
        .build();
  }

  // 댓글 수정 메소드
  public void patch(CommentDTO dto) {
    // 댓글을 수정하기 위해 요청한 id와 DB에 저장된 id가 다를 경우 예외를 발생시킨다
    if (!this.id.equals(dto.getId())) {
      throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다");
    }

    // 댓글 작성자 이름이 넘어왔는지 확인
    if (dto.getNickname() != null) {
      this.body = dto.getNickname();
    }

    // 수정할 댓글 내용이 넘어왔는지 확인
    if (dto.getBody() != null) {
      this.body = dto.getBody();
    }
  }
}
