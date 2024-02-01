package com.boot.shopping.entity;
//상품 이미지 저장
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="item_img")
@Getter @Setter
public class ItemImg extends  BaseEntity {


    @Id
    @Column(name="item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private  String imgName;
    private String origImgName;
    private String imgUrl;
    private String repImgYn; //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private  Item item;

    public void updateItemImg(String imgName, String origImgName, String imgUrl) {
        this.imgName = imgName;
        this.origImgName = origImgName;
        this.imgUrl = imgUrl;
    }

}
