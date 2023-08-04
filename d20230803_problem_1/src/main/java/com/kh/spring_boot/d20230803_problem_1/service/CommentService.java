package com.kh.spring_boot.d20230803_problem_1.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring_boot.d20230803_problem_1.dto.CommentDTO;
import com.kh.spring_boot.d20230803_problem_1.entity.Article;
import com.kh.spring_boot.d20230803_problem_1.entity.Comment;
import com.kh.spring_boot.d20230803_problem_1.repository.ArticleRepository;
import com.kh.spring_boot.d20230803_problem_1.repository.CommentRepository;

@Service
public class CommentService {
  @Autowired
  private ArticleRepository articleRepository;
  @Autowired
  private CommentRepository commentRepository;

  public List<Comment> getByArticleId(Long id) {
    return this.commentRepository.findByArticleId(id);
  }

  // 댓글 목록 조회
  // 어떤 게시글의 댓글인지 번호를 넘겨 받는다
  public List<CommentDTO> comments(Long id) {
    // this.commentRepository.id 기준으로 찾는다
    // List<Comment> comments = this.commentRepository.findByArticleId(id);

    // // Entity를 DTO로 변환
    // List<CommentDTO> dtos = new ArrayList<>();
    // comments.stream().forEach(t -> {
    //   CommentDTO.createCommentDTO(t);
    //   comments.add(t);
    // });

    // DTO로 변환된 객체를 반환한다
    // return dtos;

    /*
     * Stream
     * 데이터베이스에서 찾은 내용을 스트림 컬렉션으로 변환해서
     * 데이터를 받아서 반복하면서 자동으로 리스트로 반환한다
     */
    return this.commentRepository.findByArticleId(id)
        .stream()
        .map(comment -> CommentDTO.createCommentDTO(comment))
        .collect(Collectors.toList());
  }

  /*
   * 댓글 생성
   *   만약 댓글 생성에 실패하면 실행 전 상태로 되돌려야 하므로 트랙잭션 처리를 해야 한다
   */
  @Transactional
  public CommentDTO create(Long articleId, CommentDTO dto) {
    // 게시글 조회 및 예외 발생
    // 댓글을 저장하려는 메인글이 있으면 얻어오고 없으면 예외를 발생시킨다
    Article article = this.articleRepository.findById(articleId)
        .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! articleId에 해당되는 메인글이 없습니다"));

    // 댓글 엔티티 생성
    // DTO를 엔티티로 변환하는 메소드를 호출한다
    Comment comment = Comment.createComment(dto, article);

    // 댓글 엔티티를 DB에 저장한다
    Comment created = this.commentRepository.save(comment);

    // DTO로 변환해서 리턴한다
    return CommentDTO.createCommentDTO(created);
  }

  // 댓글 수정
  public CommentDTO update(Long id, CommentDTO dto) {
    // 게시글 조회 및 예외 발생
    // 수정할 댓글이 있으면 얻어오고 없으면 예외를 발생시킨다
    // "댓글 수정 실패. id에 해당되는 댓글이 없습니다"
    Comment comment = this.commentRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패. id에 해당되는 댓글이 없습니다"));

    // 댓글 수정하는 메소드 호출한다
    // DTO
    comment.patch(dto);

    // 수정된 댓글을 DB에 반영한다
    Comment updated = this.commentRepository.save(comment);

    // 수정한 댓글 Entity를 DTO로 변환 및 반환
    return CommentDTO.createCommentDTO(updated);
  }

  /* 댓글 삭제
   *   메소드명 delete(아이디)
   *   예외 처리
   *   댓글 삭제
   *   delete()
   *   return
   */
  public CommentDTO delete(Long id) {
    Comment comment = this.commentRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패. id에 해당되는 댓글이 없습니다"));

    // 댓글 삭제
    this.commentRepository.delete(comment);

    // 삭제한 댓글 Entity를 DTO로 반환
    return CommentDTO.createCommentDTO(comment);
  }
}
