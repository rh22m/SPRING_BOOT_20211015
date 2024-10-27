package com.example.demo.controller;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;
import java.util.List;
import java.util.Optional;
//import org.apache.el.stream.Optional; optinal적용 못 받아들임
//import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.model.domain.Article;


@Controller // 컨트롤러 어노테이션 명시
public class BlogController {

@GetMapping("/article_list") //게시판 링크 지정
public String article_list(Model model) {
    List<Article> list = blogService.findAll(); //게시판 리스트
    model.addAttribute("articles", list); //모델에 추가
    return "article_list"; //.html 연결
}

@GetMapping("/article_edit/{id}") // 게시판 링크 지정
public String article_edit(Model model, @PathVariable Long id) {
    Optional<Article> list = blogService.findById(id); // 선택한 게시판 글

    if (list.isPresent()) {
        model.addAttribute("article", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가
    } else {
    
    return "/error_page/article_error";
    }
    return "article_edit"; // .HTML 연결
    }

@PutMapping("/api/article_edit/{id}")
public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
    blogService.update(id, request);
    return "redirect:/article_list"; // 글 수정 이후 .html 연결
}

@DeleteMapping("/api/article_delete/{id}")
public String deleteArticle(@PathVariable Long id) {
    blogService.delete(id);
    return "redirect:/article_list";
}

@Autowired
BlogService blogService; // DemoController 클래스 아래 객체 생성

}