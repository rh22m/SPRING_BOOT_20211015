package com.example.demo.controller;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;
import java.util.List;

import org.apache.el.stream.Optional;
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

//@GetMapping("/hello") // 전송 방식 GET
//public String hello(Model model) {
//model.addAttribute("data", " 방갑습니다."); // model 설정
//return "hello"; // hello.html 연결
//}

//@GetMapping("/about_detailed")
//public String about() {
//return "about_detailed";
//}

//@GetMapping("/test1")
//public String thymeleaf_test1(Model model) {
//model.addAttribute("data1", "<h2> 방갑습니다 </h2>");
//model.addAttribute("data2", "태그의 속성 값");
//model.addAttribute("link", 01);
//model.addAttribute("name", "홍길동");
//model.addAttribute("para1", "001");
//model.addAttribute("para2", 002);
//return "thymeleaf_test1";
//}

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
        model.addAttribute("article", list.get()); // 존재하면 Article 객체를 모델에 추가
    } else {
        // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
        return "error"; // 오류 처리 페이지로 연결
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

if (list.isPresent()) {
    model.addAttribute("article", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가
} else {
    // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
    return "/error_page/article_error"; // 오류 처리 페이지로 연결(이름 수정됨)
}
return "article_edit"; // .HTML 연결

@Autowired
BlogService blogService; // DemoController 클래스 아래 객체 생성

}