package com.kh.restapi.twoproject_20230728.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kh.restapi.twoproject_20230728.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
