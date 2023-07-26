package com.kh.first_project_20230726.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 어노테이션을 필수로
 * 붙여야하고 엔티티라 부른다
 * @Entity 어노테이션이 붙은 클래스 이름으로 Spring boot가 자동으로
 * 테이블을 만들고 클래스 내부에 선언한 필드 이름으로 테이블을 구성하는 컬럼을 만든다
 */

/*
 * ORM (Object Relation Mapping)
//  객체와 데이터베이스 매핑을 이루는 것 
//  객체가 테이블이 되도록 매핑시켜주는 프로임워크이다!
//  프로그램의 복잡도를 줄이고 자바 객체와 쿼리를 분리할 수있으며 
//  트랜잭션처리나 데이터베이스 관련 작업들을 좀 더 편리하게 처리할 수있는 방법
 */

/*
 * JPA (Java Persistence API)
//  ORM을 사용하기 위한 인터페이스를 모아 둔 것!
//  자바 어플리케이션에서 데이터베이스를 사용하는 방식을 정의한 인터페이스!
 */

/*
 * Hibernate
// JPA를 사용하기 위해서는 JPA 구현한 ORM 프레임워크 중 하나 
 */

//JPA 를 사용해서 테이블과 매핑할 클래스는 @Entity 어노테이션을 필수로 
//붙여야하고 하고 엔티티라 부른다.
//@Entity 어노테이션이 붙은 클래스 이름으로 springboot가 자동으로 
//테이블을 만들고 클래스 내부에 선언한 필드 이름으로 테이블을 구성하는 컬럼을
//만든다.

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Article {
  /*
   * 기본 키로 사용할 필드 선언
   * 필드를 기본 키로 설정한다
   */
  @Id
  // 기본키 값을 자동으로 생성한다
  @GeneratedValue
  private long id;

  // 테이블 컬럼과 매핑한다
  @Column
  private String title;
  @Column
  private String content;
}

/*
 * h2 데이터베이스
 *   웹용 콘솔 (쿼리들) 제공하여 개발용 로컬 DB
 *   인 메모리 데이터베이스는 디스크가 아니라 메인 메모리에 모든 데이터를 저장하는 데이터베이스
 * 
 *   장점
 *     - 접근하는 속도가 빠름
 *   단점
 *     - 휘발성 (서버 끄면 전부 날아간다)
 */
