package com.kh.first_project_20230726.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.kh.first_project_20230726.dto.ArticleForm;
import com.kh.first_project_20230726.entity.Article;
import com.kh.first_project_20230726.repository.ArticleRepository;

@Controller
public class ArticleController {
  /*
   * JPA Repository 인터페이스 객체를 선언하고 @Autowired로 초기화한다
   * 스프링 부트가 ...
   */
  @Autowired
  private ArticleRepository articleRepository;

  // 뷰 페이지 만들기
  @GetMapping("/Articles/new")
  public String newArticleForm() {
    return "Articles/new";
  }

  /*
   * form에서 넘어오는 데이터는 @PostMapping()
   */
  @PostMapping(value = "/Articles/create")
  public String createArticle(ArticleForm form) {
    System.out.println(form);

    // DTO의 데이터를 Entity로 변환한다
    Article article = form.toEntity();
    System.out.println(article);

    /*
     * Repository에게 Entity를 데이터베이스에 저장하게 한다
     * id가 자동으로 증가된다
     */
    try {
      Article saved = this.articleRepository.save(article);
      System.out.println(saved);
      System.out.println(saved.equals(article));

      return "redirect:/Articles/new";
    } catch (Exception e) {
      System.err.println(e);
    }

    return "Articles/create";
  }
}
