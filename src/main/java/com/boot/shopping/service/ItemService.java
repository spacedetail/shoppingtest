package com.boot.shopping.service;

import com.boot.shopping.dto.ItemFormDto;
import com.boot.shopping.entity.Item;
import com.boot.shopping.entity.ItemImg;
import com.boot.shopping.repository.ItemImgRepository;
import com.boot.shopping.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private  final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException {

        //상품등록(저장)
        Item item = itemFormDto.createItem();
        itemRepository.save(item);  //상품 데이터 저장

        //이미지 등록(저장)
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if (i == 0)  //첫번째 이미지일 경우 대표 상품 이미지 여부 값
                itemImg.setRepImgYn("Y");
            else
                itemImg.setRepImgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));//상품 이미지 정보 저장
        }
        return item.getId();
    }
}
