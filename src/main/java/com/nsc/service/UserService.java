package com.nsc.service;

import com.nsc.dto.MapDto;
import com.nsc.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,String> login(String name, String password);
    //实时更新
    //只要数据变动过就要刷新数据
    //注册只要更新近一天的
    //删除要看全部
    //修改无法修改时间
    //List<MapDto> querySomeDayRegister();
    /**
     * 注册用户
     */
    /**
     * 删除用户
     */
    int registerUser(User user);
    int removeUser(String id);
    //实时监控用户注册数据
    List<MapDto> updateUser();
    //实时监控每个月的用户注册量
    List<MapDto> RegisterMonth();
    //不同地方的用户
    List<MapDto> selectLocation();

}
