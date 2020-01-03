package com.nsc.service.impl;

import com.nsc.dao.UserDao;
import com.nsc.dto.MapDto;
import com.nsc.entity.User;
import com.nsc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,String> login(String name, String password) {
        User user = userDao.queryByName(name);
        Map<String, String> map = new HashMap<>();
        if (user==null)
            map.put("msg","Username is Error");
        else{
            if (password.equals(user.getPassword()))
                map=null;
            else
                map.put("msg","Password is Error");
        }
            return map;
    }

    @Override
    public int registerUser(User user) {
        int insert = userDao.insertSelective(user);
        return insert;
    }

    @Override
    public int removeUser(String id) {
        return 0;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MapDto> updateUser(){
        List<MapDto> dayUser = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            dayUser.add(new MapDto(String.valueOf(i),userDao.querySomeDayRegister(i)));
        }
        return dayUser;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MapDto> RegisterMonth() {
        //用新集合 存放 月数所对应的人数
        ArrayList<MapDto> mapDtos1 = new ArrayList<>();
        //创建新数组
        int[] a = new int[12];
        List<MapDto> mapDtos = userDao.querySomeMonthRegister();
        for (MapDto mapDto : mapDtos) {
            String name = mapDto.getName();
            int month = Integer.valueOf(name);
            Integer value = mapDto.getValue();
            a[month-1] = value;
        }

        for (int i = 0; i < a.length; i++) {
            int i1 = a[i];
            mapDtos1.add(new MapDto(String.valueOf(i+1),a[i]));
        }
        return mapDtos1;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MapDto> selectLocation() {
        return userDao.selectLocation();
    }
}
