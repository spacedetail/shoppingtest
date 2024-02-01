package com.boot.shopping.service;

import com.boot.shopping.entity.ItemImg;
import com.boot.shopping.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.awt.font.MultipleMaster;
import java.io.IOException;

//상품이미지를 업로드하고 이미지 정보 저장 하는 클래스
@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}")
    private  String itemImgLocation;

    private  final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    //
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws IOException {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName="";
        String imgUrl="";

        //파일 업로드
        if(StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation, oriImgName,itemImgFile.getBytes());
            imgUrl = "/images/item/"+imgName;
        }

        // 입력받은 상품 이미지 정보저장
        itemImg.updateItemImg(oriImgName, imgName,imgUrl);
        itemImgRepository.save(itemImg);
    }

}
