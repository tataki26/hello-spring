package hello.hellospring.repository;

import hello.hellospring.domain.Member;
// import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // AfterEach
    // 콜백 함수(함수 하나가 끝날 때 뒤이어 실행되는 함수)
    // 하나의 메서드 테스트가 끝날 때마다 store에 저장된 데이터를 삭제한다
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    // 테스트를 수행하는 메소드
    // 이 메서드 내부에서 테스트가 진행된다
    @Test
    public void save() {

        Member member = new Member();
        member.setName("spring"); // id는 save 메서드가 호출되는 시점에서 자동 생성

        repository.save(member);

        // optional 타입의 데이터는 .get()을 통해 가져올 수 있다
        Member result = repository.findById(member.getId()).get();
        // 출력으로 일일히 확인할 수 없음 - Assertions (실제값 / 기대값) 사용 - jupyter 기준
        // result와 member가 같은 값인지 알 수 있다
        // 다르면 테스트 결과 - ERROR
        // System.out.println("result = "+ (result==member));
        // Assertions.assertEquals(member, result);
        // 좀 더 편한 방법
        assertThat(member).isEqualTo(result); // assertj.core
    }

    @Test
    public void findByName() {

        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        // shift + f6 : 변수명 일괄 변경
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findAll(){

        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring1");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

    }

}
