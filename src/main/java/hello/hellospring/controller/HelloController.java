package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 컨트롤러를 정의하기 위한 annotation
@Controller
public class HelloController {

    // GET 메서드 통신
    // HTTP GET 요청을 특정 핸들러 메소드에 맵핑하기 위한 annotation
    // url - /hello에 매핑 (하이퍼링크 클릭 시, 넘어가는 페이지)
    @GetMapping("hello")
    public String hello(Model model) {

        // key - value
        // hello.html 파일의 data 인자에 치환
        // key로 접근하고 value를 사용한다
        model.addAttribute("data","hello!!");
        return "hello";

    }
}
