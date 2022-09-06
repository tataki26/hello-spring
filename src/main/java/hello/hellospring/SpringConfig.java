package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

// 컴포넌트 스캔이 아닌 자바 코드로 직접 스프링 빈 등록하기
@Configuration
public class SpringConfig {

    /*
    // 스프링 부트가 설정 파일을 읽어 자체적으로 빈 생성
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) { // DI
        this.dataSource = dataSource;
    }
     */

    /*
    // JPA
    EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }
     */

    // spring data jpa
    // DI -> spring data jpa가 구현체 자동 생성
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // MemberService -> MemberRepository
    // Controller는 예외 -> 스프링이 관리하는 빈(컴포넌트 스캔)
    @Bean
    public MemberService memberService() {
        // return new MemberService(memberRepository());
        return new MemberService(memberRepository); // 의존 관계 setting
    }

    /*
    @Bean
    public MemberRepository memberRepository(){
        // 다형성: 어셈블리(조립하는 코드)만 수정해도 기존 코드를 전혀 손대지 않고 구현 클래스 변경 가능 -> 재사용성
        // 개방-폐쇄 원칙: 확장에는 열려 있고 수정(변경)에는 닫혀 있다
        //return new MemoryMemberRepository(); // 인터페이스가 아니라 구현체를 넣어줄 것!!
        // dataSource -> 스프링이 제공
        // return new JdbcMemberRepository(dataSource);
        // return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
     */
}
