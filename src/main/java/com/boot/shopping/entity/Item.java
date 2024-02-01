package com.boot.shopping.entity;

import com.boot.shopping.constant.ItemSellStatus;
//import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
@Entity
@Table(name="item")
public class Item extends  BaseEntity{

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemNm;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(nullable = false)
    private int stockNumber; //재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;//상품 판매 상태

    private LocalDateTime regTime;
    private LocalDateTime updateTime;


    //실무  M:N 사용 하지 않음 = >연결 table을 생성해서 1:N, M:1로 풀어서 사용
    @ManyToMany
    @JoinTable(name="member_item",
    joinColumns = @JoinColumn(name="member_id"),
    inverseJoinColumns = @JoinColumn(name="item_id"))
    private List<Member> member;

}
