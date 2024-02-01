package com.boot.shopping.repository;

import com.boot.shopping.constant.ItemSellStatus;
import com.boot.shopping.entity.Item;
import com.boot.shopping.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;



    @Test
    @DisplayName("상품저장 테스트")
    public void createItemTest(){
        for(int i=1; i<=10; i++){
            Item item = new Item();
            item.setItemNm("테스트 상품"+i);
            item.setPrice(10000+i);
            item.setItemDetail("테스트 상품 상세 설명"+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
            System.out.println(savedItem.toString());
        }
    }

    //1. 상품이름을 이용해서 데이터 조회- select
    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){

        this.createItemTest();
        List<Item>  itemList = itemRepository.findByItemNm("테스트 상품1");
        for(Item item : itemList)
            System.out.println(item.toString());

    }
    //2.상품명과 상품상세 설명을 OR 조건을 이용해서 조회 쿼리
    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public  void findByItemNmOrItemDetail(){
        this.createItemTest();
        List<Item>  itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for(Item item : itemList)
            System.out.println(item.toString());
    }

    //3.파라미터로 넘어온 price 변수보다 값이 작은 상품 테이터 조회 쿼리 메소드 작성
    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for(Item item : itemList)
            System.out.println(item.toString());
    }
    //4.파라미터로 넘어온 price 변수보다 값이 작은 상품 테이터 조회 쿼리 메소드 작성
    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanOrderByPriceDescTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for(Item item : itemList)
            System.out.println(item.toString());
    }

    //5. @Query()
    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for(Item item : itemList)
            System.out.println(item.toString());
    }
    //6. @Query()-nativeQuery
    @Test
    @DisplayName("nativeQuery 속성을 이용한 상품 조회 테스트")
    public void findByItemDetailByNativeTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for(Item item : itemList)
            System.out.println(item.toString());
    }


    //7.QueryDSL
    //JPAQueryFactory를 이용한 상품 조회
    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDSLTest(){
        this.createItemTest();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%"+"테스트 상품 상세 설명"+"%"))
                .orderBy(qItem.price.desc());
        //fetch()- 조회 결과 리스트 반환
        List<Item> itemList = query.fetch();
        for(Item item : itemList)
            System.out.println(item.toString());
    }

    //8. QuerydslPredicateExcutor를 이용한 상품 조회
    @Test
    @DisplayName("상품저장 테스트")
    public void createItemTest2(){
        for(int i=1; i<=5; i++){  //판매중
            Item item = new Item();
            item.setItemNm("테스트 상품"+i);
            item.setPrice(10000+i);
            item.setItemDetail("테스트 상품 상세 설명"+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);

        }
        for(int i=6; i<=10; i++){ //품절
            Item item = new Item();
            item.setItemNm("테스트 상품"+i);
            item.setPrice(10000+i);
            item.setItemDetail("테스트 상품 상세 설명"+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }
    @Test
    @DisplayName("상품 Querydsl 조회 테스트2")
    public  void queryDslTest2(){
        this.createItemTest2();
        //BooleanBuilder- query에 들어갈 조건을 만들어 주는 빌더
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String itemDetail = "테스트 상품 상세 설명";
        int price= 1003;
        String itemSellStat="SELL";

        booleanBuilder.and(item.itemDetail.like("%"+itemDetail+"%"));
        booleanBuilder.and(item.price.gt(price));
        if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)){
            booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        //데이터를 페이징해 조회해서 Pageable객체 생성
        Pageable pageable = PageRequest.of(0,5);
        //Page<T> findAll(predicate, pageable) : 조건에 맞는 페이지 데이터 반환
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements: "+ itemPagingResult.getTotalElements());

        List<Item> resultItemList =  itemPagingResult.getContent();
        for(Item resultItem : resultItemList)
            System.out.println(resultItem.toString());
    }
}