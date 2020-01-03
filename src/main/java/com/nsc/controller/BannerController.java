package com.nsc.controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nsc.dao.BannerDao;
import com.nsc.entity.Banner;
import com.nsc.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/Banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping("/showByPage")
    public Map<String, Object> showByPage(Integer rows, Integer page) {
        return bannerService.showByPage(rows, page);
    }

    @RequestMapping("/showActive")
    public List<Banner> showActive(String status){
        status="active";
        return bannerService.showActive(status);
    }


    @RequestMapping("/bannerEdit")
    public Map<String, String> bannerEidt(String oper, Banner banner, String[] id, HttpSession session) {
        Map<String, String> map = new HashMap<>();
        System.out.println("oper:" + oper);
        if ("add".equals(oper)) {
            String s = UUID.randomUUID().toString();
            banner.setId(s);
            System.out.println("id:" + banner.getId());
            bannerService.addBanner(banner);
            map.put("id", s);
        } else if ("del".equals(oper)) {
            bannerService.DeleteBulk(id);
        } else if ("edit".equals(oper)) {
            if (banner.getImg() == null||banner.getImg().equals("")) {
                banner.setImg(null);
            } else {
                map.put("id", banner.getId());
            }
            bannerService.updateBanner(banner);
        }
        return map;
    }

    @RequestMapping("/upload")
    public void fileUpload(MultipartFile img, String id, HttpSession session) {
      bannerService.ImgUpload(img,id,session);
    }

    @RequestMapping("/easyPoiPort")
    public void easyPoiPort(String name, HttpServletResponse response){
        bannerService.easyPoiPort(name,response);
    }
}
