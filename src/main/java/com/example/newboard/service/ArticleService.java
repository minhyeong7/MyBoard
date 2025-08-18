package com.example.newboard.service;


import com.example.newboard.domain.Article;
import com.example.newboard.repository.ArticleRepository;
import com.example.newboard.repository.UserRepository;
import com.example.newboard.web.dto.ArticleCreateRequest;
import com.example.newboard.web.dto.ArticleUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public List<Article> findAll(){
        return articleRepository.findAll();
    }

//    @Transactional // 이 메서드 전체가 하나의 트랜잭션으로 처리됨
//    public void create(ArticleCreateRequest req , String email) {
//        // Article 엔티티를 생성하여 DB에 저장
//        articleRepository.save(
//                Article.builder()
//                        .title(req.getTitle())      // 요청 객체에서 제목 가져오기
//                        .content(req.getContent())  // 요청 객체에서 내용 가져오기
//                        .build()                    // Article 객체 생성
//        );
//    }

    @Transactional
    public Long create(ArticleCreateRequest req, String email){
        var author = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        return articleRepository.save(
                Article.builder()
                        .title(req.getTitle())
                        .content(req.getContent())
                        .author(author)
                        .build()
        ).getId();
    }

    public Article findById(Long id){
        // ArticleRepository에서 id로 게시글을 찾음
        // 만약 게시글이 없으면 예외를 발생시킴
        return articleRepository.findById(id) //Article article = articleService.findById(id) 를 짧게 쓰는 형식
                .orElseThrow(() -> new IllegalArgumentException("Article not found: "+ id));
    }

//    @Transactional
//    public void update(Long id, ArticleUpdateRequest req){
//        // id로 기존 게시글을 조회 (없으면 예외 발생 가능)
//        var article = findById(id);
//        // 조회한 id의 글을 새로운 내용으로 수정하고 업데이트
//        article.update(req.getTitle(),req.getContent());
//    }

    @Transactional
    public void update(Long id, String email, ArticleUpdateRequest req){
        var article = articleRepository.findByIdAndAuthor_Email(id, email)
                .orElseThrow(() -> new AccessDeniedException("본인 글이 아닙니다."));

        article.update(req.getTitle(), req.getContent());
    }

//    @Transactional
//    public void delete(Long id){
//        articleRepository.deleteById(id);
//    }
    @Transactional
    public void delete(Long id, String email){

        if (articleRepository.deleteByIdAndAuthor_Email(id, email) == 0)
            throw new AccessDeniedException("본인 글이 아닙니다.");
    }

}
