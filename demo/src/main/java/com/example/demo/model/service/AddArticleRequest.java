package com.example.demo.model.service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.demo.model.domain.Article;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter // getter, setter, toString, equals 등 자동 생성
public class AddArticleRequest {
    private String title;
    private String content;

public Article toEntity(){ // Article 객체 생성
    return Article.builder()
        .title(title)
        .content(content)
        .build();
    }
}