package com.kh.spring_boot.d20230803_problem_1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kh.spring_boot.d20230803_problem_1.dto.ArticleForm;
import com.kh.spring_boot.d20230803_problem_1.dto.CommentDTO;
import com.kh.spring_boot.d20230803_problem_1.entity.Article;
import com.kh.spring_boot.d20230803_problem_1.repository.ArticleRepository;
import com.kh.spring_boot.d20230803_problem_1.service.ArticleService;
import com.kh.spring_boot.d20230803_problem_1.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ArticleController {
  @Autowired
  private ArticleService articleService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private ArticleRepository articleRepository;

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

  @GetMapping(value = "/articles/new")
  public String newArticleForm() {
    /*
     * @Slf4j
     *   log.trace(): 자세한 로그
     */

    return "articles/new";
  }

  // @PostMapping(value = "/articles/create")
  public String createArticle(ArticleForm form) {
    log.info("컨트롤러의 createArticle() 메소드 실행");
    //		System.out.println(form);
    log.info(form.toString());

    //		DTO의 데이터를 Entity로 변환한다.
    Article article = form.toEntity();
    //		System.out.println(article);
    log.info(article.toString());
    //		Repository에게 Entity를 데이터베이스에 저장하게 한다.
    //		id가 자동으로 증가된다.
    Article saved = articleRepository.save(article);
    //		System.out.println(saved);
    log.info(saved.toString());

    //		글 1건 저장이 완료되었으므로 redirect를 이용해서 목록 보기나 작성한 글 보기로 넘겨준다.
    return "redirect:/articles"; // 목록 보기
    //		return "redirect:/articles/" + saved.getId(); // 작성한 글 보기
  }

  //	브라우저에서 /articles/글번호 형태의 요청을 받아 처리한다.
  //	{}는 변경되는 /articles/1, /articles/2, ...와 같이 변화되는 데이터를 받는다는 의미이다.
  @GetMapping("/articles/{id}")
  //	{}를 통해서 받는 데이터를 저장할 변수는 @PathVariable 어노테이션을 붙여서 선언한다.
  public String show(@PathVariable Long id, Model model) {
    log.info("컨트롤러의 show() 메소드 실행");
    log.info("id = " + id);

    //		메인글을 얻어온다.
    //		articleRepository의 findById() 메소드로 인수로 지정한 id에 해당되는 메인글 1건을 테이블에서 가져온다.
    //		findById() 메소드로 얻어온 데이터가 없으면 orElse() 메소드로 null을 리턴시킨다.
    Article articleEntity = articleRepository.findById(id).orElse(null);
    //		테이블에서 가져온 메인글을 show.mustache 파일로 넘기기 위해 Model 인터페이스 객체에 넣어준다.
    model.addAttribute("article", articleEntity);

    //		댓글 목록을 얻어온다.
    //		테이블에서 가져온 댓글을 show.mustache 파일로 넘기기 위해 Model 인터페이스 객체에 넣어준다.
    List<CommentDTO> commentsDtos = commentService.comments(id);
    //		테이블에서 가져온 댓글 목록을 show.mustache 파일로 넘기기 위해 Model 인터페이스 객체에 넣어준다.
    model.addAttribute("commentsDtos", commentsDtos);
    log.info("commentsDtos = {}", commentsDtos);

    //		뷰 페이지를 설정한다.
    return "articles/show";
  }

  @GetMapping("/articles")
  public String index(Model model) {
    log.info("컨트롤러의 index() 메소드 실행");

    //		테이블에 저장된 모든 글 목록을 얻어온다.
    List<Article> articleEntityList = articleRepository.findAll();
    log.info(articleEntityList + "");
    //		가져온 글 목록을 index.mustache로 넘겨주기 위해 Model 객체에 저장한다.
    model.addAttribute("articleList", articleEntityList);

    return "articles/index";
  }
}
