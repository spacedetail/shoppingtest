package com.boot.shopping.entity;
//양방향 매핑은 '연관관계 주인'을 설정해야 함
//엔티티를 양방향 연관관계로 설정하면 객체의 참조는 둘인데 외래키는 하나이므로
// 둘중 누가 외래키를 관리할지  결정
//1. 연관관계의 주인은 외래키가 있는 곳으로 설정
//2. 연관관계의 주인이 외래키를 관리(등록,수정,삭제)
//3.주인이 아닌쪽은 연관 관계 매핑시 mappedBy 속성의 값으로 연관관계 주인을 설정
//4.주인이 아닌쪽은 읽기가 가능
import com.boot.shopping.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItem extends  BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;

    private  int count;//주문수량

    private LocalDateTime orderDate;//주문일

    //Auditing으로 인해서 삭제
//    private LocalDateTime regTime;
//    private LocalDateTime updateTime;
}
