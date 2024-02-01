package com.boot.shopping.repository;
//JPQL
//(1) Query Method
//(2) @Query()
//(3)QueryDSL- JPQL을 코드로 작성할수 있도록 도와주는 빌더, 에러를 빨리 찾기 위해서
//     - 고정된 SQL문이 아닌 조건에 맞게 동적으로 Query를 생성
// =- 의존성 추가

import com.boot.shopping.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

//Test => Ctrl+Shift+T
public interface ItemRepository extends JpaRepository<Item, Long> , QuerydslPredicateExecutor {
//QuerydslPredicateExecutor 인터페이스 메소드
    //predicate: ~조건에 맞는
    //long count(predicate); 조건에 맞는 데이터 총갯수 반환
    //boolean exists(predicate): 조건에 맞는 데이터 존재여부 반환
    //Page<T> findAll(predicate, pageable) : 조건에 맞는 페이지 데이터 반환


    //1. 상품이름을 이용해서 데이터 조회
    List<Item> findByItemNm(String itemNm);

    //2.상품명과 상품상세 설명을 OR 조건을 이용해서 조회 쿼리
    List<Item>  findByItemNmOrItemDetail(String itemNum, String itemDetail);

    //3.파라미터로 넘어온 price 변수보다 값이 작은 상품 테이터 조회 쿼리 메소드 작성
    List<Item>  findByPriceLessThan(Integer price);

    //4. 상품의 가격이 높은 순으로 조회?
    List<Item>  findByPriceLessThanOrderByPriceDesc(Integer price);


    //5. @Query()
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    //6. @Query() - nativeQuery (기존 DB SQL)
    @Query(value = "select * from Item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);


}
