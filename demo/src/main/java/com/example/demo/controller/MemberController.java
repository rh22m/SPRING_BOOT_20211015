package com.example.demo.controller;
import com.example.demo.model.service.AddMemberRequest;
import com.example.demo.model.service.MemberService;
import com.example.demo.model.domain.Member;
//import java.util.Optional;
//import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;

//import com.example.demo.model.service.AddArticleRequest;
////import com.example.demo.model.service.AddArticleRequest;
//import org.apache.el.stream.Optional; optinal적용 못 받아들임
//import org.hibernate.mapping.List;
////import org.springframework.web.bind.annotation.ModelAttribute;
////import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.data.domain.Pageable;
//import com.example.demo.model.domain.Article;
//import com.example.demo.model.domain.Board;
//import java.util.List;


@Controller // 컨트롤러 어노테이션 명시
public class MemberController {

@Autowired
MemberService memberService;

@GetMapping("/join_new") // 회원 가입 페이지 연결
public String join_new() {
    return "join_new"; // .HTML 연결
}

@PostMapping("/api/members") // 회원 가입 저장
public String addmembers(@ModelAttribute AddMemberRequest request) {
    memberService.saveMember(request);
    return "join_end"; // .HTML 연결
}

@GetMapping("/member_login") // 로그인 페이지 연결
public String member_login() {
    return "login"; // .HTML 연결
}
    
@PostMapping("/api/login_check") // 로그인(아이디, 패스워드) 체크
public String checkMembers(@ModelAttribute AddMemberRequest request, Model model) {
    try {
        Member member = memberService.loginCheck(request.getEmail(), request.getPassword()); // 패스워드 반환
        model.addAttribute("member", member); // 로그인 성공 시 회원 정보 전달
        return "redirect:/board_list"; // 로그인 성공 후 이동할 페이지
    }
    catch (IllegalArgumentException e) {
        model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
        return "login"; // 로그인 실패 시 로그인 페이지로 리다이렉트
    }
}
}

//@Autowired
//BlogService blogService; // DemoController 클래스 아래 객체 생성

// @GetMapping("/article_list") //게시판 링크 지정
// public String article_list(Model model) {
//     List<Board> list = blogService.findAll(); //게시판 리스트
//     model.addAttribute("boards", list); //모델에 추가
//     return "article_list"; //.html 연결
// }

//@GetMapping("/board_list") // 새로운 게시판 링크 지정
//public String board_list(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String keyword) {
//     PageRequest pageable = PageRequest.of(page, 3); // 한 페이지의 게시글 수
//     Page<Board> list; //int startNum = (page * 3) + 1; // Page를 반환

//     if (keyword.isEmpty()) {
//         list = blogService.findAll(pageable); // 기본 전체 출력(키워드 x)
//     } else {
//         list = blogService.searchByKeyword(keyword, pageable); // 키워드로 검색
//     }
//     model.addAttribute("boards", list); // 모델에 추가
//     model.addAttribute("totalPages", list.getTotalPages()); // 페이지 크기
//     model.addAttribute("currentPage", page); // 페이지 번호
//     model.addAttribute("keyword", keyword); // 키워드
//     return "board_list"; // .HTML 연결
// }

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

// @GetMapping("/board_view/{id}") // 게시판 링크 지정
// public String board_view(Model model, @PathVariable Long id) {
//     Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
//     if (list.isPresent()) {
//         model.addAttribute("boards", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가
//     } else {
//         // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
//         return "/error_page/article_error"; // 오류 처리 페이지로 연결
//     }
//     return "board_view"; // .HTML 연결
// }

// @GetMapping("/board_write")
// public String board_write() {
//     return "board_write";
// }

// @GetMapping("/board_edit/{id}") // 게시판 링크 지정
// public String board_edit(Model model, @PathVariable Long id) {
//     Optional<Board> list = blogService.findById(id); // 선택한 게시판 글

//     if (list.isPresent()) {
//         model.addAttribute("board", list.get()); // 존재할 경우 실제 Article 객체를 모델에 추가
//     } else {
//     return "/error_page/article_error";
//     }
//     return "board_edit"; // .HTML 연결
//     }

// //@PutMapping
// @PostMapping("/api/board_edit/{id}")
// public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
//     blogService.update(id, request);
//     return "redirect:/board_list"; // 글 수정 이후 .html 연결
// }

// @PostMapping("/api/boards") // 글쓰기 게시판 저장
// public String addboards(@ModelAttribute AddArticleRequest request) {
//     blogService.save(request);
//     return "redirect:/board_list"; // .HTML 연결
// }

// @DeleteMapping("/api/board_delete/{id}")
// public String deleteBoard(@PathVariable Long id) {
//     blogService.delete(id);
//     return "redirect:/board_list";
// }

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