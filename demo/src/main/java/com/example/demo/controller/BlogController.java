package com.example.demo.controller;
import com.example.demo.model.service.AddArticleRequest;
import com.example.demo.model.service.BlogService;

import jakarta.servlet.http.HttpSession;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import com.example.demo.model.domain.Board;

////import org.springframework.web.bind.annotation.ModelAttribute;
////import org.springframework.web.bind.annotation.PutMapping;
////import com.example.demo.model.service.AddArticleRequest;
//import org.springframework.data.domain.Pageable;
//import com.example.demo.model.domain.Article;
//import org.apache.el.stream.Optional; optinal적용 못 받아들임
//import org.hibernate.mapping.List;
//import java.util.List;

@Controller // 컨트롤러 어노테이션 명시
public class BlogController {

@Autowired
BlogService blogService; // DemoController 클래스 아래 객체 생성


// @GetMapping("/article_list") //게시판 링크 지정
// public String article_list(Model model) {
//     List<Board> list = blogService.findAll(); //게시판 리스트
//     model.addAttribute("boards", list); //모델에 추가
//     return "article_list"; //.html 연결
// }

@GetMapping("/board_list") // 새로운 게시판 링크 지정
public String board_list(
    Model model,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "") String keyword,
    HttpSession session) { //세션 객체 전달
        String userId = (String) session.getAttribute("userId"); //세션 아이디 존재 확인
        String email = (String) session.getAttribute("email"); // 세션에서 이메일 확인
        
        if (userId == null) {
            return "redirect:/member_login"; // 로그인 페이지로 리다이렉션
        }

        System.out.println("세션 userId: " + userId); // 서버 IDE 터미널에 세션 값 출력
    PageRequest pageable = PageRequest.of(page, 3); // 한 페이지의 게시글 수
    Page<Board> list; //int startNum = (page * 3) + 1; // Page를 반환

    if (keyword.isEmpty()) {
        list = blogService.findAll(pageable); // 기본 전체 출력(키워드 x)
    } else {
        list = blogService.searchByKeyword(keyword, pageable); // 키워드로 검색
    }

    // 현재 페이지에 대한 시작 번호 계산 (페이지 당 3개의 게시글)
    int startNum = (page * 3) + 1;
    model.addAttribute("email", email); // 로그인 사용자 이메일
    model.addAttribute("startNum", startNum); // 시작 번호를 모델에 추가    model.addAttribute("email", email); // 로그인 사용자(이메일)
    model.addAttribute("boards", list); // 모델에 추가
    model.addAttribute("totalPages", list.getTotalPages()); // 페이지 크기
    model.addAttribute("currentPage", page); // 페이지 번호
    model.addAttribute("keyword", keyword); // 키워드
    return "board_list"; // .HTML 연결
}

//@GetMapping("/article_edit/{id}") // 게시판 링크 지정
//public String article_edit(Model model, @PathVariable Long id) {
//    Optional<Article> list = blogService.findById(id); // 선택한 게시판 글
//    if (list.isPresent()) {
//        model.addAttribute("boards", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가
//    } else {
//    return "/error_page/article_error";
//    }
//    return "article_edit"; // .HTML 연결
//    }

@GetMapping("/board_view/{id}") // 게시판 링크 지정
public String board_view(Model model, @PathVariable Long id) {
    Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
    if (list.isPresent()) {
        model.addAttribute("boards", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가
    } else {
        // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
        return "/error_page/article_error"; // 오류 처리 페이지로 연결
    }
    return "board_view"; // .HTML 연결
}

@GetMapping("/board_write")
public String board_write() {
    return "board_write";
}

@GetMapping("/board_edit/{id}") // 게시판 링크 지정
public String board_edit(Model model, @PathVariable Long id) {
    Optional<Board> list = blogService.findById(id); // 선택한 게시판 글

    if (list.isPresent()) {
        model.addAttribute("board", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가
    } else {
    return "/error_page/article_error";
    }
    return "board_edit"; // .HTML 연결
    }

//@PutMapping
@PostMapping("/api/board_edit/{id}")
public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
    blogService.update(id, request);
    return "redirect:/board_list"; // 글 수정 이후 .html 연결
}

@PostMapping("/api/boards") // 글쓰기 게시판 저장
public String addboards(@ModelAttribute AddArticleRequest request) {
    blogService.save(request);
    return "redirect:/board_list"; // .HTML 연결
}

@DeleteMapping("/api/board_delete/{id}")
public String deleteBoard(@PathVariable Long id) {
    blogService.delete(id);
    return "redirect:/board_list";
}


@Value("${spring.servlet.multipart.location}") // properties 등록된 설정(경로) 주입
private String uploadFolder;

@PostMapping("/upload-email")
public String uploadEmail( // 이메일, 제목, 메시지를 전달받음
    @RequestParam("email") String email,
    @RequestParam("subject") String subject,
    @RequestParam("message") String message,
    RedirectAttributes redirectAttributes) {

        try {
            Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
            if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            
            }

            String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
            Path filePath = uploadPath.resolve(sanitizedEmail + ".txt"); // 업로드 폴더에 .txt 이름 설정
            System.out.println("File path: " + filePath); // 디버깅용 출력
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("메일 제목: " + subject); // 쓰기
                writer.newLine(); // 줄 바꿈
                writer.write("요청 메시지:");
                writer.newLine();
                writer.write(message);
            }
        
            redirectAttributes.addFlashAttribute("message", "메일 내용이 성공적으로 업로드되었습니다!");
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message", "업로드 중 오류가 발생했습니다.");
                return "/error_page/article_error"; // 오류 처리 페이지로 연결
            }
            
            return "upload_end"; // .html 파일 연동
        }

//@PutMapping("/api/article_edit/{id}")
//public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
//    blogService.update(id, request);
//    return "redirect:/article_list"; // 글 수정 이후 .html 연결
//}


//@DeleteMapping("/api/article_delete/{id}")
//public String deleteArticle(@PathVariable Long id) {
//    blogService.delete(id);
//    return "redirect:/article_list";
//}

}