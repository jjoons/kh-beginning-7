package com.kh.restapi.twoproject_20230728.service;

import java.util.List;
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
}
