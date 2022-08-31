package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

// 정형화된 패턴 - 스프링이 인식할 수 있는 클래스들
// @Repository
// MemberRepository의 구현체
public class MemoryMemberRepository implements MemberRepository {

    // key-value
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    // 데이터 저장하기
    @Override
    public Member save(Member member) {
        // id 셋팅 - 유저x, 시스템이 지정
        member.setId(++sequence);
        // id(key)와 이름(value)를 store map에 저장
        store.put(member.getId(), member);
        return member;
    }

    // id(key)로 사용자 정보 가져오기
    @Override
    public Optional<Member> findById(Long id) {
        // ofNullable - null도 반환 가능
        return Optional.ofNullable(store.get(id));
    }

    // name(value)로 사용자 정보 가져오기
    @Override
    public Optional<Member> findByName(String name) {
        // stream.filter() - 스트림 내 요소에 대해서 필터링하는
        // 루프를 돌면서 인자에 해당하는 값을 찾는다
        // filter는 boolean 결과를 리턴하는 람다식(변수 -> 수행문)을 필요로 한다
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // 조건에 일치하는 요소 1개 리턴
        // 목록에 없으면 null 반환
    }

    // store에 있는 모든 value 반환
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // store에 저장된 데이터 삭제
    // afterEach 메서드에 활용
    public void clearStore(){
        store.clear();
    }

}
