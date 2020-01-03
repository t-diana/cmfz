package com.nsc.controller;

import com.nsc.util.ValidateImageCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/verifyCode")
public class VerifyCodeController {

    @RequestMapping("/verify")
    public void verify(HttpServletResponse response, HttpServletRequest request) {
        //获取session作用域
        HttpSession session = request.getSession();
        //获取验证码
        String code = ValidateImageCodeUtils.getSecurityCode();
        //获取验证码图片
        BufferedImage image = ValidateImageCodeUtils.createImage(code);
        //将验证码放在session中
        session.setAttribute("code",code);
        //获取输出流
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("print image exception");
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw  new RuntimeException("outputStream close isn't true");
                }
            }
        }

    }
}
