package com.boot.shopping.entity;

//import jakarta.persistence.Column;
//import jakarta.persistence.EntityListeners;
//import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter @Setter
@EntityListeners(value = {AuditingEntityListener.class})  //Audit적용
@MappedSuperclass  // 공통 매핑 정보가 필요할때 상속받는 자식클래스에만 매핑정보 정보
public  abstract  class BaseTimeEntity {

    @CreatedDate  //엔티티 생성시 시간 자동 저장
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate //엔티티값을 변경시 변경시간 자동저장
    private LocalDateTime updateTime;

}
