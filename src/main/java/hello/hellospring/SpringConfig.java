package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 컴포넌트 스캔이 아닌 자바 코드로 직접 스프링 빈 등록하기
@Configuration
public class SpringConfig {

    // MemberService -> MemberRepository
    // Controller는 예외 -> 스프링이 관리하는 빈(컴포넌트 스캔)
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository(); // 인터페이스가 아니라 구현체를 넣어줄 것!!
    }
}
