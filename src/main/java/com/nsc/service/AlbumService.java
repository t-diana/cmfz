package com.nsc.service;

import com.nsc.dto.FrontArticleAndAlbumDto;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    Map<String,Object> showByPage(Integer row,Integer page);
    //展示前台数据
    List<FrontArticleAndAlbumDto> showFrontAlbumData();
    //展示前台专辑细节
    FrontArticleAndAlbumDto showFrontAlbumDetailData(String id);
}
