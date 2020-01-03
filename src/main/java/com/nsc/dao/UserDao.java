package com.nsc.dao;

import com.nsc.dto.MapDto;
import com.nsc.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserDao {
    User queryByName(String name);

    /**
     * 注册
     * @param record 用户
     * @return
     */
    int insert(User record);

    /***
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);

    /**
     * 含有空字段的注册
     * @param record
     * @return
     */
    int insertSelective(User record);

    //统计七天用户注册数量
    /*
    @Param
            靠近的时间天数 day
     */
    Integer querySomeDayRegister(Integer day);
    //统计12个月每个月的注册人数
    List<MapDto> querySomeMonthRegister();
    //展示不同地域的用户
    List<MapDto> selectLocation();
}
