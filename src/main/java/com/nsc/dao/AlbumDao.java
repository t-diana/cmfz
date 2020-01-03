package com.nsc.dao;

import com.nsc.dto.FrontArticleAndAlbumDto;
import com.nsc.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    int deleteByPrimaryKey(String id);

    int insert(Album record);

    int insertSelective(Album record);

    Album selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Album record);

    int updateByPrimaryKey(Album record);
    //分页查询
    List<Album> queryByPage(@Param("start") Integer start,@Param("row") Integer row);
    //查询总条数
    Integer queryCount();

    /**
     * 展示前台数据
     * @return
     */
    List<FrontArticleAndAlbumDto> showFrontAlbumData();
    //展示专辑细节数据
    FrontArticleAndAlbumDto showFrontAlbumDetailData(String id);
}