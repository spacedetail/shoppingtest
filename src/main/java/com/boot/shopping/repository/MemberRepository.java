package com.boot.shopping.repository;

import com.boot.shopping.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    //중복되는 이메일이 있는지 확인
    Member findByEmail(String email);
}
