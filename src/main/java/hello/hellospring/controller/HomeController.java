package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // welcome page(index.html)로 가지 않는 이유 -> 우선 순위
    // 컨트롤러 찾고 없으면 정적 페이지 확인
    // 컨트롤러와 매핑이 되었으므로 index.html을 무시하고 바로 home.html 확인
    @GetMapping("/") // 도메인 첫번째(시작 주소), loaclhost 접속하자마자 보는 화면
    public String home() {
        return "home";
    }
}
