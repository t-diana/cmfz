package com.nsc.dao;

import com.nsc.dto.ChapterDto;
import com.nsc.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    int deleteByPrimaryKey(String id);

    int insert(Chapter record);

    int insertSelective(Chapter record);

    Chapter selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);
    //分页查询
    List<Chapter> queryByPage(@Param("start") Integer start, @Param("albumId") String albumId, @Param("row") Integer row);
    //查询总条数
    Integer queryCount(String albumId);
    //进行批量删除
    int deleteBulk(String[] id);

    /**
     *  给前端展示相应数据
     * @param id   专辑id
     * @return
     */
    List<ChapterDto> queryFrontData(String id);
}