package com.kh.spring_boot.d20230803_problem_1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kh.spring_boot.d20230803_problem_1.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  // 특정 게시글의 모든 댓글을 조회하는 메소드
  @Query(value = "SELECT * FROM comment WHERE article_id = :articleId", nativeQuery = true)
  List<Comment> findByArticleId(Long articleId);

  // 특정 닉네임의 모든 댓글을 조회하는 메소드
  // ORM은 객체와 관계형 데이터베이스의 데이터를 자동으로 매핑(연결)해 주는 것을 말한다
  List<Comment> findByNickname(String nickname);
}
