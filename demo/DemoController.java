package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller // 컨트롤러 어노테이션 명시
Public class DemoController {
@GetMapping(＂/hello＂) // 전송 방식 GET
public String hello(Model model) {
model.addAttribute("data", " 방갑습니다."); // model 설정
return "hello"; // hello.html 연결
@GetMapping(＂/hello2＂) // 전송 방식 GET
public String hello2(Model model) {
model.addAttribute("data", "홍길동님.", " 방갑습니다.", "오늘.", "날씨는.", "매우 좋습니다."); // model 설정
return "index"; // hello.html 연결
}
}