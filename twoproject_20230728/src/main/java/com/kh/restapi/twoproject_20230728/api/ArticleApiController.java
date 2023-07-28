package com.kh.restapi.twoproject_20230728.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// Rest API용 컨트롤러 데이터 반환 (JSON)
import org.springframework.web.bind.annotation.RestController;
import com.kh.restapi.twoproject_20230728.dto.ArticleForm;
import com.kh.restapi.twoproject_20230728.entity.Article;
import com.kh.restapi.twoproject_20230728.service.ArticleService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ArticleApiController {
  // DI (Dependency Injection)
  @Autowired
  private ArticleService articleService;

  // GET 글 전체 조회
  @GetMapping(value = "/api/articles")
  public List<Article> index() {
    // 서비스의 index() 메소드를 호출해서 데이터베이스 처리 결과를 받는다
    return this.articleService.index();
  }

  // 하나의 글을 조회할 때
  @GetMapping(value = "/api/articles/{id}")
  public Article show(@PathVariable Long id) {
    return this.articleService.show(id);
  }

  // POST 생성
  @PostMapping(value = "/api/articles")
  /*
   * form에서 데이터를 받아올 때는 커맨드 객체로 받으면 되지만
   * REST API에서 JSON으로 던지는 데이터를 받을 때
   * body 부분에 담겨오는 데이터를 받아야 하므로
   * 커맨드 객체에 @RequestBody 어노테이션을 붙여서 받아야 한다
   * 
   * 데이터를 테이블에 저장하고 저장한 데이터를 리턴시킨다
   * 상태 코드와 데이터 두 가지를 받아와야하기 때문에
   * 응답을 받을 때 ResponseEntity 객체를 사용해야한다
   */
  public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
    Article saved = this.articleService.create(dto);

    /*
     * 정상적으로 입력받고 저장까지 완료한다는 전제로
     * 만약 데이터 하나를 빼고 들어오거나 오류가 발생시킬 수 있는 것도
     * 처리해야한다
     * 
     * 오류가 발생해서 body부분이 비어있을 경우 build() 메소드를 이용해서
     * body 부분없이 넘긴다
     */
    if (saved == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  // PATCH

  // DELETE
}
