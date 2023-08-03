package com.kh.spring_boot.d20230803_problem_1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring_boot.d20230803_problem_1.entity.Comment;
import com.kh.spring_boot.d20230803_problem_1.repository.CommentRepository;

@Service
public class CommentService {
  @Autowired
  private CommentRepository commentRepository;

  public List<Comment> getByArticleId(Long id) {
    return this.commentRepository.findByArticleId(id);
  }
}
