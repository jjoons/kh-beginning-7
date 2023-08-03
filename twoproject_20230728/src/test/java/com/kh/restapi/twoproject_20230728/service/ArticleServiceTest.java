package com.kh.restapi.twoproject_20230728.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.kh.restapi.twoproject_20230728.dto.ArticleForm;
import com.kh.restapi.twoproject_20230728.entity.Article;

/*
 * IntelliJ에서 진행하는 경우
 *   1. 테스트하고 싶은 메소드 우클릭
 *   2. Generate
 *   3. Test
 */

/*
 * JUnit
 *   자바를 위한 단위 테스트 라이브러리
 *   assert() 테스트 케이스의 수행 결과를 판별해 알려준다
 *   assertEquals(a, b): a와 b를 비교해서 같은 값을 가지는지 확인
 *   assertArrayEquals(a, b): 배열 a와 b가 같은지 확인한다
 *   assertTrue(a): a가 참인지 확인한다
 *   assertNull(a): a가 null인지 확인한다
 *   assertNotNull(a): a가 null이 아닌지 확인한다
 */

/*
 * @Test
 *   테스트 메소드를 작성한다
 * @Test(timeout = 밀리초)
 *   테스트 메소드 수행 시간을 제한할 수 있다
 * @Test(expected = 예외)
 *   해당 테스트 메소드 예외 발생 여부에 따라 성공 / 실패를 판별할 수 있다
 *   expected에 지정된 예외가 발생해야 테스트가 성공한 것으로 판별한다
 */

// 어노테이션을 붙여 스프링 부트와 연동한 통합 테스트를 수행한다
@SpringBootTest
class ArticleServiceTest {
  // ArticleService DI
  @Autowired
  ArticleService articleService;

  @Test
  void testIndex() {
    /*
     * 예상
     *   모든 게시물을 불러오면 data.sql에 저장했던 데이터들이 불러와질 것이다라는 예상
     */
    //    Article a = new Article(1L, "가가가가", "ㄱㄱㄱㄱㄱㄱㄱ");
    //    Article b = new Article(2L, "한꼬미", "천재");
    //    Article c = new Article(3L, "두꼬미", "처언재");

    Article article1 = new Article(1L, "홍길동", "천재");
    Article article2 = new Article(2L, "임꺽정", "처언재");
    Article article3 = new Article(3L, "장길산", "처어언재");
    Article article4 = new Article(4L, "일지매", "처어어언재");

    // List 만들기
    //    List<Article> expected = new ArrayList<>(Arrays.asList(a, b, c));
    List<Article> expected =
        new ArrayList<Article>(Arrays.asList(article1, article2, article3, article4));

    /*
     * 실제
     *   데이터베이스에서 데이터를 가져오는 서비스를 이용해서 데이터를 저장하고
     *   실제 내용과 위에 들어가는 내용을 비교하는 메소드로 assertEquals()
     */
    List<Article> articles = this.articleService.index();

    // 비교
    assertEquals(expected.toString(), articles.toString());
  }

  @Test
  void testShowSuccessExistId() {
    // 예상
    Long id = 1L;
    Article expected = new Article(id, "홍길동", "천재");

    // 실제
    Article article = this.articleService.show(id);

    // 비교
    assertEquals(expected.toString(), article.toString());
  }

  @Test
  void testShowFailNotExistId() {
    // 예상
    Long id = 1L;
    Article expected = null;

    // 실제
    Article article = this.articleService.show(id);

    // 비교
    assertEquals(expected, article);
  }

  /*
   * 테이블이 변경되는 테스트들을 실행하는 경우
   * 이전 데이터의 영향을 받아서 하나씩 테스트할 때는 정상적으로 실행된다.
   * 테스트가 오류가 발생할 수 있기 때문에 테스트 결과가  테이블을 변경시키는
   * 테스트는 트랜잭션 어노테이션을 이용해서 테스트가 끝나면 롤백할 수 있도록 해야 한다
   */

  @Test
  @Transactional
  void testCreateSuccessTitleAndContent() {
    String title = "홍길동";
    String content = "수리수리마수리";

    ArticleForm dto = new ArticleForm(null, title, content);
    Article excepted = new Article(5L, title, content);
    Article article = this.articleService.create(dto);

    assertEquals(excepted.toString(), article.toString());
  }


  @Test
  void testCreate() {}

  @Test
  void testUpdate() {}

  @Test
  void testDelete() {}
}
