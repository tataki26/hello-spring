package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // JPA는 EntityManager로 동작함
    // JPA 라이브러리 추가 시, 스프링 부트가 자동으로 EntityManger 생성
    // 사용자는 생성된 Entity를 Injection 받기만 하면 된다
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        // 이렇게만 해도 JPA가 insert call을 만들어 자동으로 데이터를 db에 저장한다
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // find(조회할 타입, pk)
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // JPQL
        // 테이블이 아닌 객체(entity)를 대상으로 쿼리를 날림 -> SQL로 번역됨
        // entity 자체를 select
        return em.createQuery("select m from Member as m", Member.class).getResultList();
    }
}
