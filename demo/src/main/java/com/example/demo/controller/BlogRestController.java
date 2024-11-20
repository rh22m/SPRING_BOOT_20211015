package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

//import org.springframework.data.web.PageableDefault;
//import com.example.demo.model.domain.Article;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import com.example.demo.model.service.AddArticleRequest;
////import com.example.demo.model.service.BlogService;
//import com.example.demo.model.domain.Board;

@RequiredArgsConstructor
@RestController // @Controller + @ResponseBody

public class BlogRestController {
    ////private final BlogService blogService;

    //     @PostMapping("/api/articles")
    // public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
    // Article saveArticle = blogService.save(request);

// return ResponseEntity.status(HttpStatus.CREATED)
// .body(saveArticle);
// }

@GetMapping("/favicon.ico")
public void favicon() {
// 아무 작업도 하지 않음
}

}