package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

// 클래스 내부가 비어 있어도 스프링 컨테이너 안에 이 클래스의 객체가 생성됨
// 스프링 관리 하에 있는 메서드 = 스프링 빈 관리
// 스프링 컨테이너: Controller -> Service -> Repository (Component 스캔)
@Controller
public class MemberController {

    // Singleton Pattern
    // 인스턴스 필요 시, 같은 인스턴스를 여러 개 만들지 않고 기존 인스턴스를 활용하는 것
    // 메모리 낭비 방지
    // 여러 번 생성할 필요 없이 스프링 컨테이너에 등록하고 가져다 쓰면 된다
    private final MemberService memberService;
    // = new MemberService();

    // 의존 관계 주입
    // 스프링 컨테이너에서 생성된 객체와 연결
    // @Service 클래스와 연결
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
