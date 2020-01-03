package com.nsc.service;

import com.nsc.dto.FrontArticleAndAlbumDto;
import com.nsc.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    Map<String,Object> showByPage(Integer row, Integer page);
    //添加文章
    int addArticle(Article article);
    //批量删除
    int delBulk(String[] id);
    //修改文章
    int updateArticle(Article article);
    //展示前台首页数据
    List<FrontArticleAndAlbumDto> showFrontArticleData();
}
