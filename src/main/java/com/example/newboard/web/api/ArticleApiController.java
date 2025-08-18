package com.example.newboard.web.api;

import com.example.newboard.domain.Article;
import com.example.newboard.service.ArticleService;
import com.example.newboard.web.dto.ArticleCreateRequest;
import com.example.newboard.web.dto.ArticleUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleApiController {

    /*
     * 현재 이 컨트롤러에는 GET 메서드가 없음
     * -> 즉, API 경로 (/api/articles 등)로 GET 요청을 보내면 처리하는 메서드가 없으므로 404 발생함
     *
     * 게시글 목록 조회나 상세 조회 기능(GET 요청)은 별도의 컨트롤러인 ArticleViewController에서 처리 중임
     * 즉, 화면(UI) 관련 요청은 ArticleViewController에서 처리하기 때문에
     * 화면을 통해 게시글 조회가 정상 동작하는 것
     *
     * 만약 API에서도 GET 요청 지원을 원한다면, 이 컨트롤러에 GET 핸들러를 추가해야 함
     */

    private final ArticleService articleService;

//    // HTTP POST 요청(서버에 데이터를 보내 리소스 생성 또는 요청 처리) 을 처리하는 핸들러 메서드
//    // 클라이언트로부터 전달받은 게시글 생성 요청을 처리한다.
//    @PostMapping
//    public ResponseEntity<Void> create(@Valid @RequestBody ArticleCreateRequest req) {
//        // 서비스 계층에 게시글 생성 로직을 위임
//        articleService.create(req);
//        // 게시글이 성공적으로 생성되었음을 나타내는 201 Created 응답 반환
//        // 본문은 없이 상태 코드만 응답
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//
//    // HTTP PUT 요청을 처리하는 핸들러 메서드
//    // 클라이언트가 보낸 수정 요청을 받아 해당 ID의 게시글을 수정한다.
//    @PutMapping("/{id}")
//    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody ArticleUpdateRequest req) {
//        // 서비스 계층에 수정 로직 위임
//        articleService.update(id, req);
//        // 수정이 성공적으로 완료되었음을 클라이언트에게 알림 (200 OK 응답)
//        return ResponseEntity.ok().build();
//    }
//
//    // HTTP DELETE 요청을 처리하는 핸들러 메서드
//    // 클라이언트로부터 전달받은 게시글 ID를 기준으로 해당 게시글을 삭제한다.
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        // 서비스 계층에 게시글 삭제 로직을 위임
//        articleService.delete(id);
//        // 게시글이 성공적으로 삭제되었음을 나타내는 204 No Content 응답 반환
//        // 본문 없이 상태 코드만 전달됨
//        return ResponseEntity.noContent().build();
//    }

    @PostMapping
    public ResponseEntity<Article> create(@Valid @RequestBody ArticleCreateRequest req, Authentication auth) {
        Long id = articleService.create(req, auth.getName());
        return ResponseEntity.created(URI.create("/articles/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @Valid @RequestBody ArticleUpdateRequest req,
                                       Authentication auth) {
        articleService.update(id, auth.getName(), req);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        articleService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

}