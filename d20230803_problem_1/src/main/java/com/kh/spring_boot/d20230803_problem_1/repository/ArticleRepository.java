package com.kh.spring_boot.d20230803_problem_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kh.spring_boot.d20230803_problem_1.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
