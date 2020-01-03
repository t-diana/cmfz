package com.nsc.dao;

import com.nsc.entity.Guru;

public interface GuruMapper {
    int deleteByPrimaryKey(String id);

    int insert(Guru record);

    int insertSelective(Guru record);

    Guru selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Guru record);

    int updateByPrimaryKey(Guru record);
}