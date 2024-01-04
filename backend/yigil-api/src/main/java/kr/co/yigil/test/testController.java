package kr.co.yigil.test;

import jakarta.servlet.http.HttpSession;
import kr.co.yigil.member.domain.Member;
import kr.co.yigil.member.domain.SocialLoginType;
import kr.co.yigil.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class testController {

    private final MemberRepository repository;

    @PostMapping("/test")
    public ResponseEntity<String> loginTest(HttpSession session) {
        session.setAttribute("memberId", 1L);
        return ResponseEntity.ok("로그인 성공");
    }
}