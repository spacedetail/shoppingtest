package com.boot.shopping.entity;
//장바구니

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="cart")
@Getter @Setter @ToString
public class Cart {

    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //연관 관계 매핑
//    @OneToOne-회원Entity 와 1:1로 매핑
   // @JoinColumn(name="member_id") - 매핑할 외래키(이름) 지정
//즉시(EAGER) 로딩- entity를 조회할때 해당 엔티티와 매핑된 엔티티를 한번에 즉시 조회 : 즉시 로딩
   //FetchType.LAZY(지연):
 //   @OneToOne :fetch = FetchType.EAGER 가 default
    @OneToOne(fetch = FetchType.EAGER) // default
    @JoinColumn(name="member_id")
    private Member member;
}
