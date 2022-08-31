package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // MemberService memberService = new MemberService();
    // MemberService 클래스의 인스턴스와 다른 인스턴스
    // ctrl + b: 함수 선언으로 이동
    // MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // 프로그램 실행 전 실행되는 메서드
    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach()
    {
        memberRepository.clearStore();
    }

    // test 코드의 메서드명은 한글로 작성해도 ok
    // 빌드 시, 포함되지 않음
    @Test
    void 회원가입() {
        // given(...한 상황이 주어졌는데)
        Member member = new Member();
        member.setName("spring");

        // when(...를 실행했을 때 - 검증할 메서드)
        Long saveId = memberService.join(member);

        // then(결과가 ...로 나와야 한다)
        // 저장한 회원이 리포지토리에 있나를 확인
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        // 변수 자동 생성 - ctrl + alt + v
        // assertThrows의 두번째 인자를 실행하여 첫번째 인자인 예외 타입과 같은지 검사
        // member2 넣었을 때, 관련 에러가 터져야 함
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*
        try {
            memberService.join(member2);
            fail();
        } catch(IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
         */

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}