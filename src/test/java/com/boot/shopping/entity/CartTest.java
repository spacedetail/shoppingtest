package com.boot.shopping.entity;

import com.boot.shopping.constant.Role;
import com.boot.shopping.dto.MemberFormDto;
import com.boot.shopping.repository.CartRepository;
import com.boot.shopping.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public  Member createMember(){

        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.co");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울");
        memberFormDto.setPassword("1234");

        return Member.createMember(memberFormDto,passwordEncoder);

    }
    @Test
    @DisplayName("장바구니회원 엔티티 매핑 조회 테스트")
    public  void findCartAndMemberTest(){
        Member member =  createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        em.flush();//transaction끝날때 flush()호출하여 DB에 반영
        em.clear();

        Cart savedCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(savedCart.getMember().getId(), member.getId());
    }
}