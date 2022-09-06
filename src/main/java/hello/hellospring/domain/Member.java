package hello.hellospring.domain;

import javax.persistence.*;

// JPA가 관리하는 Entity
@Entity
public class Member {

    // 요구사항
    // 회원: id, name
    // pk mapping
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // db가 id 자동 생성
    private Long id;
    // @Column (name = "username") // 만약 컬럼명이 username이라면, db의 컬럼명과 매핑
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
