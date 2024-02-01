package com.boot.shopping.service;

import com.boot.shopping.dto.MemberFormDto;
import com.boot.shopping.entity.Member;
//import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();

        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("종로");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto,passwordEncoder);
        
    };

    //1.
    @Test
    @DisplayName("회원가입")
    public void saveMemberTest(){

        Member member =createMember();
        Member savedMember = memberService.saveMember(member);
        //입력된 정보와 가져온 정보가 같은 확인
        assertEquals(member.getEmail(),savedMember.getEmail());
        assertEquals(member.getName(),savedMember.getName());
        assertEquals(member.getAddress(),savedMember.getAddress());
        assertEquals(member.getPassword(),savedMember.getPassword());
        assertEquals(member.getRole(),savedMember.getRole());

    }



    //2.
    @Test
    @DisplayName("중복 회원가입 테스트")
    public void saveDuplicateMemberTest(){

        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);//저장된 값

        Throwable e = assertThrows(IllegalStateException.class,()->{
            memberService.saveMember(member2); //저장하려는 값
        });

        //Member savedMember = memberService.saveMember(member1);
        //기존의 정보와 가져온 정보가 같은 확인
        assertEquals("이미 가입된 회원입니다.",e.getMessage());

    }

}