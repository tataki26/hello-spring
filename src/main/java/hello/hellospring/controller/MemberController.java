package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
    // 1. 생성자 주입
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        // memberService.setMemberRepository(); // setter 주입 시, 아무 개발자나 접근할 수 있다는 단점
    }

    // 2. 필드 주입
    // 유연하지 않아서 비추천
    // @Autowired private MemberService memberService;

    /*
    // 3. setter 주입
    // 접근 권한자를 public으로 해야 하는 단점(변경 위험)
    // 의존 관계가 실행 중에 동적으로 변하는 경우(런타임 변화)는 거의 없다
    private MemberService memberService;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;

    }
     */

    // 회원 가입 하이퍼링크 클릭 -> /members/new 로 이동
    // return에 따라 members/createMemberForm으로 이동(templates의 동일 html 파일) <- viewResolver 렌더링
    // get: 조회용, url 직접 입력
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    // html의 action url로 넘어오면 이 메서드 호출
    // post: 등록용, 데이터를 form 등에 넣어서 전달하는 방식
    // create의 인자로 사용자가 입력한 name이 들어감
    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        // form에 set된 name을 가져와서 member에다 넣어줌
        member.setName(form.getName());

        memberService.join(member);

        // home 화면으로 돌리기
        return "redirect:/";
    }

    // member를 조회하는 함수를 호출하고 model에 해당 목록을 담아서 화면에 넘겨준다
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
