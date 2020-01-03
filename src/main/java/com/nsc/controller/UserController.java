package com.nsc.controller;

import com.nsc.dto.MapDto;
import com.nsc.entity.User;
import com.nsc.service.UserService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Map<String,String> login(String name, String password, String verifycode, HttpServletRequest request) {
        //查看验证码
        //System.out.println(verifycode);
        //进行验证码比较
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        Map<String, String> map = new HashMap<>();
        Map<String, String> login = userService.login(name, password);
        if (code.equals(verifycode))
            map =login;
        else
            map.put("msg","VerifyCode is Error");
        return map;
    }

    /**
     * 进行用户注册
     */
    @RequestMapping("/register")
    public void registerUser(User user){
        user.setId(UUID.randomUUID().toString());
        int i = userService.registerUser(user);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-b121a256bfeb4d0e874c71b5a744d092");
        goEasy.publish("180_Channel", "update data");
    }

    /**
     * 实时七天内展示用户更新数据
     * @return
     */
    @RequestMapping("/goeasy")
    public List<MapDto> showUser(){
        return  userService.updateUser();
    }

    @RequestMapping("/goeasyMonth")
    public List<MapDto> showUserMonth(){
        return userService.RegisterMonth();
    }
    //展示不同位置的用户
    @RequestMapping("/Location")
    public List<MapDto> showLocation(){
        return userService.selectLocation();
    }
}
