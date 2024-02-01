package com.boot.shopping.entity;
//양방향
// on delete cascade(단계적삭제)  - 부모 삭제시 자식 자동삭제
// 영속성 전이 - cascade(계단식), Entity상태를 변경할때 해당 Entity와
// //                     연관된 엔티티의 상태 변화를 전파 시키는 옵션
// cascade 종류:  persist- 부모 entity가 영속화 될때 자식 엔티티도 영속화(저장)
//                             merge(병합)
//                             remove(삭제)
//                             all- 부모 엔티티에 상태  변화를 자식 엔티티에 모두 전이


// 고아객체- 부모 엔티티와 연관관계가 끊어진 자식 엔티티 => 제거 대상
//orphanRemoval = true (주의-고아객체 참조하는 곳이 하나 일때만 사용)
import com.boot.shopping.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


    private LocalDateTime orderDate;//주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;// 주문상태

    //양방향-member
    @OneToMany(mappedBy = "order", cascade= CascadeType.ALL
            , orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime regTime;
    private LocalDateTime updateTime;

}
