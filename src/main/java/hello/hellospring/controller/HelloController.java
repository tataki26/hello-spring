package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("hello-mvc")
    // 외부에서 파라미터를 받을 때 사용하는 annotation
    // GET 방식: url을 통해 파라미터를 전달해줘야 한다(?변수명=값)
    public String helloMvc(@RequestParam("name") String name, Model model)
    {
        model.addAttribute("name", name);
        return "hello-template";

    }

    // 문자열 전달하기
    @GetMapping("hello-string")
    // HTTP(통신 프로토콜)의 body 부분에 리턴 값을 직접 넣어주는 역할
    // 템플릿 엔진과 다르게 VIEW를 활용하지 않는다
    // HTML 태그가 필요 없이 넘겨 받은 인자를 그대로 화면에 띄워준다
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
    }

    // 데이터 전달하기
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){ // value
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // json 포맷(key-value)의 데이터가 넘어감 (default)
    };

    // static 사용 시, class 안에서 또 다른 class 선언이 가능
    static class Hello {
        private String name; // key

        // 자바 빈 표준 - 프로퍼티 접근 방식
        // 외부에서 데이터에 바로 접근할 수 없도록 보호해주는 기능
        // getter
        public String getName(){
            return name;
        }

        // setter
        public void setName(String name){
            this.name = name;
        }

    }

}
