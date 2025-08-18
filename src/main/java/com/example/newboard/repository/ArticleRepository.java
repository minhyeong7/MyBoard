package com.example.newboard.repository;

import com.example.newboard.domain.Article;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @EntityGraph(attributePaths = "author")
    Optional<Article> findById(Long id);

    @EntityGraph(attributePaths = "author")
    Optional<Article> findByIdAndAuthor_Email(Long id, String email);

    long deleteByIdAndAuthor_Email(Long id, String email);
}

//별도의 구현 없이도, Spring Data JPA가 필요한 구현을 자동으로 제공해 줌.
    /*
     * [제공되는 기본 메서드들]
     *
     * - save(Article article)
     *   게시글 저장 또는 수정 (ID가 있으면 수정, 없으면 새로 저장)
     *
     * - findById(Long id)
     *   ID로 특정 게시글을 조회 (Optional로 반환)
     *
     * - findAll()
     *   전체 게시글 리스트를 조회
     *
     * - deleteById(Long id)
     *   ID로 게시글 삭제
     *
     * - existsById(Long id)
     *   해당 ID의 게시글이 존재하는지 확인
     *
     * - count()
     *   전체 게시글 수 조회
     */

    /*
     * [추가 기능 예시 - 필요 시 직접 선언하여 사용 가능]
     *
     * List<Article> findByTitleContaining(String keyword);
     *   → 제목에 특정 키워드가 포함된 게시글 검색
     *
     * List<Article> findByAuthor(String author);
     *   → 작성자 이름으로 게시글 검색
     *
     * List<Article> findAllByOrderByCreatedAtDesc();
     *   → 작성일 기준 내림차순 정렬

     */

