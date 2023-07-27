package com.kh.first_project_20230726.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
  @GetMapping("/articles/new")
  public String newArticleForm() {
    return "articles/new";
  }

  /*
   * form에서 넘어오는 데이터는 @PostMapping()
   */
  @PostMapping(value = "/articles/create")
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

      // 저장이 완료되었으면 목록 보기나 작성한 글 보기로 넘겨준다
      // return "redirect:/articles/";

      // 작성한 글 보기
      return "redirect:/articles/" + saved.getId();
    } catch (Exception e) {
      System.err.println(e);
    }

    return "articles/create";
  }

  /*
   * 브라우저에서 /articles/글번호 형태의 요청을 받아서 처리한다
   * {} /articles/1, /articles/2
   * {변수명}을 통해서 받는 데이터를 저장할 변수를 만들고
   * URL의 들어오는 값을 변수명으로 매칭시켜서 사용할 수 있도록 어노테이션을 사용한다
   */
  @GetMapping(value = "/articles/{id}")
  public String show(@PathVariable long id, Model model) {
    System.out.println("컨트롤러의 show() 메소드 실행");
    System.out.println("id: " + id);

    /*
     * id 한 건마다 해당되는 데이터를 테이블에서 가지고 온다
     * findById(): id 값을 넣으면 테이블에서 찾으서 결과를 Article 타입으로 반환한다
     * 만약 데이터가 없다면 orElse(null) 메소드를 넣으면 null을 리턴시킨다
     */
    Article articleEntity = this.articleRepository.findById(id).orElse(null);

    /*
     * 테이블에서 데이터를 가지고 와서 show.mustache 파일로 넘기기 위해서
     * model 인터페이스 객체에 넣어준다
     */
    model.addAttribute("article", articleEntity);

    return "articles/show";
  }

  /*
   * 전체 글 보기
   */
  @GetMapping(value = "/articles")
  public String index(Model model) {
    /*
     * 테이블에 저장된 모든 글 목록을 얻어온다
     * JDK 8 버전이면 Iterator로 반환될 수도 있다
     */
    List<Article> articleEntityList = this.articleRepository.findAll();
    System.out.println(articleEntityList);

    model.addAttribute("articleList", articleEntityList);

    return "articles/index";
  }

  // 글 번호를 가지고 수정하는 메소드
  @GetMapping(value = "/articles/{id}/edit")
  public String edit(@PathVariable long id, Model model) {
    // 수정할 데이터를 얻어온다
    Article articleEntity = this.articleRepository.findById(id).orElse(null);
    model.addAttribute("article", articleEntity);

    return "articles/edit";
  }

  // form에서 넘어오는 데이터 전체를 받기 위해서 커맨드 객체를 사용한다
  @PostMapping("/articles/update")
  //  @RequestMapping(value = "/articles/update", method = RequestMethod.POST)
  public String update(ArticleForm form) {
    /*
     * DTO를 Entity로 변환한다
     */
    Article article = form.toEntity();

    // 데이터베이스에 저장된 수정할 데이터를 얻어와서 Entity로 수정한 후 DB에 저장한다
    Article target = this.articleRepository.findById(article.getId()).orElse(null);

    if (target != null) {
      articleRepository.save(article);
    }

    // 수정한 글 한건만 보여주고 싶을 때는
    return "redirect:/articles/" + article.getId();
  }

  @GetMapping(value = "/articles/{id}/delete")
  public String delete(@PathVariable long id, RedirectAttributes rttr) {
    // 삭제할 데이터를 가져온다
    Article target = this.articleRepository.findById(id).orElse(null);

    // 데이터 삭제
    if (target != null) {
      this.articleRepository.delete(target);

      // 메소드를 이용해서 1회성으로 1번만 사용할 메시지를 뷰 페이지로 전달한다
      rttr.addFlashAttribute("msg", id + "번 글 삭제 완료");
    }

    return "redirect:/articles";
  }
}
