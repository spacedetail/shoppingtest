package com.boot.shopping.entity;

import com.boot.shopping.constant.Role;
import com.boot.shopping.dto.MemberFormDto;
//import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Table(name="member")
@Entity
@Getter
@Setter
@ToString
public class Member extends  BaseEntity{

    @Id
    @Column(name="memeber_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member =new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password); //비밀번호 암호화
        //member.setRole(Role.USER);
        member.setRole(Role.ADMIN); // 관리자 계정
        return member;

    }

}
