package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 인터페이스는 다중 상속 가능
// Spring Data Jpa가 JpaRepository를 받는 인터페이스를 자동으로 구현하고 스프링 빈에 등록한다
// 사용자는 가져다 쓰기만 하면 된다
// JpaRepository가 기본 기능(ex:) findAll) 제공
// 기본적인 crud 기능도 제공
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // 공통 인터페이스로 제공할 수 없는 것
    // 규칙: findBy~ -> [JPQL] select m from Member m where m.~ = ?
    // 단순 80% 기능은 인터페이스만으로 구현 가능
    @Override
    Optional<Member> findByName(String name);
}
