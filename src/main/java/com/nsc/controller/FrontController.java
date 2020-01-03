package com.nsc.controller;

import com.nsc.dto.ChapterDto;
import com.nsc.dto.FrontArticleAndAlbumDto;
import com.nsc.service.AlbumService;
import com.nsc.service.ArticleService;
import com.nsc.service.BannerService;
import com.nsc.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/detail")
public class FrontController {

    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ChapterService chapterService;

    /**
     * 展示首页数据
     * 主要是  轮播图   专辑   文章
     *
     * @param uid      用户id
     * @param type     请求数据类型   首页:all  闻:wen 思:si
     * @param sub_type 子模块的数据类型  上师言教:ssyj  显密法要：xmfy
     * @return 一系列数据的map集合 有轮播图  专辑   文章
     */
    @RequestMapping("/first_page")
    public Map<String, Object> showIndexData(String uid, String type, String sub_type) {

        Map<String, Object> map = new HashMap<>();

        if ("all".equals(type)) {//all 存在轮播图
            //先放置head的数据
            map.put("head", bannerService.showFrontBannerData());
        }
        //处理数据
        List<FrontArticleAndAlbumDto> albumDtos = albumService.showFrontAlbumData();
        List<FrontArticleAndAlbumDto> articleDtos = articleService.showFrontArticleData();
        for (int i = 0; i < articleDtos.size(); i++) {
            FrontArticleAndAlbumDto dto = articleDtos.get(i);
            albumDtos.add(dto);
        }
        map.put("body", albumDtos);

        return map;
    }

    /**
     * 用于展示信息
     * @param id 专辑id
     * @param uid 用户id
     * @return
     */
    @RequestMapping("/wen")
    public Map<String,Object> showAlbumAndChapter(String id,String uid){
        Map<String, Object> map = new HashMap<>();
        //专辑细节
        FrontArticleAndAlbumDto dto = albumService.showFrontAlbumDetailData(id);
        map.put("introduction",dto);
        //章节细节
        List<ChapterDto> chapterDtos = chapterService.queryFrontData(id);
        map.put("list",chapterDtos);
        return map;
    }
}
