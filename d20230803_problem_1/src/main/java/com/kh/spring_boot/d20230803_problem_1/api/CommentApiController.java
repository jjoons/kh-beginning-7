package com.kh.spring_boot.d20230803_problem_1.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring_boot.d20230803_problem_1.dto.CommentDTO;
import com.kh.spring_boot.d20230803_problem_1.service.ArticleService;
import com.kh.spring_boot.d20230803_problem_1.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CommentApiController {
  @Autowired
  private ArticleService articleService;
  @Autowired
  private CommentService commentService;

  // 댓글을 관리하는 컨트롤러

  // 목록 조회 요청
  @GetMapping(value = "")
  public ResponseEntity<Object> showList() {
    return ResponseEntity.ok().build();
  }

  // 댓글 생성 요청
  @PostMapping(value = "/api/comments/{articleId}/create")
  public ResponseEntity<CommentDTO> create(@PathVariable Long articleId, @RequestBody CommentDTO dto) {
    CommentDTO dtos = this.commentService.create(articleId, dto);

    return ResponseEntity.status(HttpStatus.CREATED).body(dtos);
  }

  // 댓글 수정 요청

  // 댓글 삭제 요청
}
