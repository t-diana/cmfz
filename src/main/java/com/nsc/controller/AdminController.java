package com.nsc.controller;

import com.nsc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/poiExcel")
    public void poiExcel(String name, HttpServletResponse response){
        adminService.poiExcel(name,response);
    }
    @RequestMapping("/login")
    public Map<String,Object> adminLogin(String name, String password, HttpServletRequest request,String verifycode){
        //查看验证码
        //System.out.println(verifycode);
        //进行验证码比较
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> login = adminService.login(name, password);
        if (code.equals(verifycode))
            map =login;
        else
            map.put("msg","VerifyCode is Error");
        return map;
    }
}
