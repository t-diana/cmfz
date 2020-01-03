package com.nsc.dao;

import com.nsc.dto.FrontArticleAndAlbumDto;
import com.nsc.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    int deleteByPrimaryKey(String id);


    int insertSelective(Article record);

    Article selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);
    //分页查询
    List<Article> selectByPage(@Param("start") Integer start, @Param("row") Integer row);
    //展示总条数
    Integer selectRecords();
    //删除所有
    int delBulk(String[] id);
    //展示前台的相应数据
    List<FrontArticleAndAlbumDto> showFrontArticleData();
}