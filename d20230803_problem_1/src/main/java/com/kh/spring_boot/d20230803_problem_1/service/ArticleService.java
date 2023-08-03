package com.kh.spring_boot.d20230803_problem_1.service;

import com.kh.spring_boot.d20230803_problem_1.entity.Article;
import com.kh.spring_boot.d20230803_problem_1.repository.ArticleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
  @Autowired
  private ArticleRepository articleRepository;

  public List<Article> board() {
    var list = this.articleRepository.findAll();

    return list;
  }

  public Article view(Long id) {
    return this.articleRepository.findById(id).orElse(null);
  }
}
