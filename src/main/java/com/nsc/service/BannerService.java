package com.nsc.service;

import com.nsc.dto.FrontBannerDto;
import com.nsc.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface BannerService {
    //查看数据
    Map<String,Object> showByPage(Integer rows,Integer page);
    Banner showOne(String id);
    //添加数据
    Boolean addBanner(Banner banner);
     //修改数据
    Boolean updateBanner(Banner banner);
    //批量删除
    void DeleteBulk(String[] ids);
    //展示激活的轮播图
    List<Banner> showActive(String status);
    //进行文件上传
    void ImgUpload(MultipartFile img, String id, HttpSession session);
    //导出轮播图
    void easyPoiPort(String fileName, HttpServletResponse response);
    //展示前台相关数据
    List<FrontBannerDto> showFrontBannerData();
}
