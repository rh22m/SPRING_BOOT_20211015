package com.example.demo.model.service;

import com.example.demo.model.domain.Member;
//import com.example.demo.model.domain.Article;

import jakarta.validation.constraints.*;
import lombok.*; // 어노테이션 자동 생성
@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Data // getter, setter, toString, equals 등 자동 생성
@Builder

public class AddMemberRequest {
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email
    private String email;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,30}", message = "비밀번호는 8자 이상, 영문 대소문자를 사용하세요.")
    private String password;
    @Pattern(regexp = "^[1-9]{1}[0-9]{1}$", message = "나이는 숫자로 입력, 19세 이상부터 가입 가능합니다.") //10의자리는 1~9, 1의자리는 0~9
    @Min(value = 19) //최소 19
    @Max(value = 90) //최대 90
    private String age;
    @NotEmpty
    private String mobile;
    @NotEmpty
    private String address;

    public Member toEntity(){ // Member 생성자를 통해 객체 생성
        return Member.builder()
        .name(name)
        .email(email)
        .password(password)
        .age(age)
        .mobile(mobile)
        .address(address)
        .build();
    }
}