package com.kh.spring_boot.d20230803_problem_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kh.spring_boot.d20230803_problem_1.service.ArticleService;
import com.kh.spring_boot.d20230803_problem_1.service.CommentService;

@Controller
public class ArticleController {
  @Autowired
  private ArticleService articleService;
  @Autowired
  private CommentService commentService;

  @GetMapping(value = "/board")
  public String board(Model model) {
    var articleList = this.articleService.board();

    model.addAttribute("articleList", articleList);

    return "board";
  }

  @GetMapping(value = "/view/{id}")
  public String view(Model model, @PathVariable Long id) {
    var article = this.articleService.view(id);
    var commentList = this.commentService.getByArticleId(id);

    model.addAttribute("article", article);
    model.addAttribute("commentList", commentList);

    return "view";
  }
}
