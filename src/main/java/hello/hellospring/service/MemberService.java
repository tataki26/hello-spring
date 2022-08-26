package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 회원 가입
     * 비즈니스 로직 - 중복 데이터(같은 이름의 회원) 금지
     */
    public Long join(Member member){

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
}
