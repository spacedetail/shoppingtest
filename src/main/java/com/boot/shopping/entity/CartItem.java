package com.boot.shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="cart_item")
@Getter @Setter
public class CartItem {
    @Id
    @GeneratedValue
    @Column(name="cart_item_id")
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
   private Cart cart;

@ManyToOne(fetch = FetchType.LAZY)    //default-(fetch = FetchType.EAGER
@JoinColumn(name="item_id")
    private Item item;

    private  int count;

}
