package com.kh.restapi.twoproject_20230728.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kh.restapi.twoproject_20230728.dto.ArticleForm;
import com.kh.restapi.twoproject_20230728.entity.Article;
import com.kh.restapi.twoproject_20230728.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;

/*
 * 서비스
 *   데이터베이스에 접근해서 결과를 만드는 곳
 *   컨트롤러에서 데이터베이스 접근을 하면 서비스에게 넘겨주고
 *   넘겨준 데이터를 가지고 실제 데이터베이스에 접근해서 데이터를 가져온다
 */

@Service
@Slf4j
public class ArticleService {
  // ArticleRepository 인터페이스 객체의 Bean을 자동으로 주입한다
  @Autowired
  private ArticleRepository articleRepository;

  // Article 전체 목록 조회 실행
  public List<Article> index() {
    return this.articleRepository.findAll();
  }

  public Article show(Long id) {
    return this.articleRepository.findById(id).orElse(null);
  }

  public Article create(ArticleForm dto) {
    Article article = dto.toEntity();

    /*
     * id는 DB가 자동으로 생성하므로 id가 넘어오는 데이터를 저장하지 않는다
     */
    if (article.getId() != null) {
      return null;
    }

    return this.articleRepository.save(article);
  }

  // 수정
  public Article update(Long id, ArticleForm dto) {
    Article article = dto.toEntity();
    Article target = this.articleRepository.findById(id).orElse(null);

    /*
     * 수정할 대상이 없거나 id가 다른 경우
     * 잘못된 요청이다 (400)
     */
    if (target == null || target.getId() != id) {
      log.info("잘못된 요청: id: {}, article: {}", id, article);
      return null;
    }

    /*
     * 수정할 title이나 content가 입력되었는지 확인
     * 수정할 대상이 있는 필드들을 새로 저장해 주기
     * patch() 메소드가 한다
     * Article 클래스에서 저장
     */
    target.patch(article);

    return this.articleRepository.save(target);
  }

  public Article delete(Long id) {
    Article target = this.articleRepository.findById(id).orElse(null);

    if (target == null) {
      log.info("잘못된 요청: {}번글은 테이블이 존재하지 않습니다", id);
      return null;
    }

    this.articleRepository.delete(target);

    return target;
  }

  /*
   * 트랜잭션 (Transaction)
   *   - 데이터베이스의 상태를 변화시키는 하나의 논리적 기능을 수행하기 위한 작업 단위
   *   - 사용자가 시스템에 대한 서비스 요구 시 시스템이 응답하기 위한 상태 반환 과정의 작업 단위
   *   - 여러 SQL 문들을 단일 작업으로 묶어서 나눠질 수 없게 만드는 것이 트랜잭션
   *   - 만약 실행 중에 중간에 실패하면 내용을 싹 날리고 처음으로 돌아간다
   *   - 예) 계좌이체
   *   - COMMIT 또는 ROLLBACK
   */

  /*
   * @Transactional 어노테이션을 이용해서 메소드를 묶는다
   * 데이터를 추가할 때 문제가 발생하면 추가한 내용을 가지고
   * 게시판에 전체 출력할 때 문제가 발생하면 실행한 내용을 모두 취소하고
   * 기존 내용으로 돌아간다
   *
   * 정상적으로 실행되면 데이터를 영구적으로 저장까지 할 수 있도록
   * 만들어주는 것이 트랜잭션 처리 방법이다
   */
  @Transactional
  public List<Article> createArticles(List<ArticleForm> dtos) {
    /*
     * stream()
     *   Java 8 부터 추가된 자바 스트림
     *   추가된 컬렉션의 저장 요소를 참조해 람다식으로 처리할 수 있도록 해 주는 반복자
     *   
     * Lambda: 코드를 간결하게 사용
     * 
     * 스트림 특징
     *   데이터 소스를 변경하지 않음 (읽기 모드)
     *   일회성 스트림도 요소를 모두 읽고 나면 닫혀서 사용할 수 없다
     *   필요하다면 새 스트림을 생성해서 사용한다
     *   내부적으로 반복 처리
     *   
     * toList() / toMap() / toSet()
     *   Stream의 요소를 수집해 요소를 그룹화하거나
     *   결과를 담아 반환하는데 사용한다
     *   
     * stream() 작동 방식
     *   스트림 생성: 인스턴스 생성
     *   중간 연산: 필터링 및 매핑을 통해서 얻고자 하는 데이터로 가공하는 중간 작업
     *   최종 연산: 최종 결과를 만들어 내는 작업
     * 
     * Arrays.stream(dtos)
     */
    List<Article> articleList =
        dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());

    log.info("articleList: {}", articleList);

    articleList.stream().forEach(article -> this.articleRepository.save(article));

    // 강제 예외 발생
    // 데이터베이스 처리 과정에서 예외가 발생할 경우에는
    // orElseThrow() 메소드를 이용해서 예외 처리한다
    this.articleRepository
        .findById(-1L)
        .orElseThrow(() -> new IllegalArgumentException("예외 처리 메시지"));

    /*
    // 아이디를 이용해서 데이터 저장
    // 전체적인 글의 내용 저장
    
    // DTO 묶음을 Entity 묶음으로 변환하는 작업
    List<Article> articleList = new ArrayList<>();
    
    for (int i = 0; i < dtos.size(); i++) {
      Article entity = dtos.get(i).toEntity();
      articleList.add(entity);
    }
    
    // Entity 묶음을 DB로 저장한다
    for (int i = 0; i < articleList.size(); i++) {
      this.articleRepository.save(articleList.get(i));
    }
    
    return null;*/
  }
}

/*
 * Test
 *   프로그램의 품질 검증을 위한 것
 *   우리가 의도한 대로 프로그램이 동작하는지 확인하는 것
 *   
 * TDD (Test Driven Development)
 *   소프트웨어 개발 시 먼저 테스트를 작성하고 그 다음에 크드를 작성하는 것을 강조
 *   코드 작성 전에 먼저 테스트해서 코드를 작성하면 코드의 품질과 안정성을 높일 수 있다
 *   
 * JUnit
 */
