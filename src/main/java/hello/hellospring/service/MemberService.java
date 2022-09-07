package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// 이 annotation이 없으면 순수 자바 코드이기 때문에 스프링 컨테이너 내부 객체와 연결 불가능
// @Service
@Transactional // JPA 사용(데이터 저장, 변경)시 필수
public class MemberService {

    // 오직 한번만 할당할 수 있는 entity를 정의할 때 사용
    // final로 선언된 변수가 할당되면 항상 같은 값을 가진다
    private final MemberRepository memberRepository;

    // 의존성 주입(Dependency Injection)
    // 하나의 클래스에서 다른 클래스를 내부에 변수로 사용하는 것 [의존성]
    // 위(내부)에서 직접 new를 생성하지 말고 외부에서 생성하도록 설정 [주입]
    // @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     * 비즈니스 로직 - 중복 데이터(같은 이름의 회원) 금지
     */
    public Long join(Member member){

        /*
        // AOP가 필요한 사항 - 메서드 호출 시간 측정
        long start = System.currentTimeMillis();

        try{
            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();
        }
        finally{ // 예외처리 발생 여부를 떠나 무조건 실행하도록 하는 구문
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = "+timeMs+"ms");
        }
         */

        validateDuplicateMember(member);// 중복 회원 검증
        memberRepository.save(member); // 검증되면 저장
        // (optional) 해당 member의 id를 반환한다
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
    /*
    Optional<Member> result = memberRepository.findByName(member.getName());
    // optional로 리턴을 받기 때문에 null 처리 가능
    // 조회한 메서드 반환값을 optional로 감싸고 isPresent()로 반환하여 boolean 값으로 조건문을 채운다
    result.ifPresent(m -> { // if(m != null) : m이 존재하면 아래 코드 실행
        throw new IllegalStateException("이미 존재하는 회원입니다.");
    });
     */

        // 좀 더 간단하게 리팩토링
        // result 변수를 따로 선언할 필요 없이 바로 리턴된 결과에 ifPresent 실행
        memberRepository.findByName(member.getName()).ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){

        /*
        // AOP가 필요한 사항 - 메서드 호출 시간 측정
        long start = System.currentTimeMillis();
        try{
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers "+timeMs+"ms");
        }
         */

        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
