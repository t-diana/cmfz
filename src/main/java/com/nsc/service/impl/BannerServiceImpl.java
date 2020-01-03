package com.nsc.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.nsc.dao.BannerDao;
import com.nsc.dto.FrontBannerDto;
import com.nsc.entity.Banner;
import com.nsc.service.BannerService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> showByPage(Integer rows, Integer page) {
        Map<String, Object> map = new HashMap<>();
        //计算总记录数
        Integer records = bannerDao.queryTotal();
        map.put("records", records);
        //展示的数据
        List<Banner> row = bannerDao.queryByPage(rows * (page - 1), rows);
        map.put("rows", row);
        //计算总页数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        //给当前页
        map.put("page", page);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Banner showOne(String id) {
        return bannerDao.queryById(id);
    }

    @Override
    public Boolean addBanner(Banner banner) {
        bannerDao.insertImg(banner);
        return true;
    }

    @Override
    public Boolean updateBanner(Banner banner) {
        if (bannerDao.queryById(banner.getId()) == null)
            return false;
        else {
            bannerDao.updateImg(banner);
            return true;
        }
    }

    @Override
    public void DeleteBulk(String[] ids) {
        bannerDao.deleteBulk(ids);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> showActive(String status) {
        return bannerDao.showActive(status);
    }

    @Override
    public void ImgUpload(MultipartFile img, String id, HttpSession session) {
        //获取源文件名
        String originalFilename = img.getOriginalFilename();
        //获取文件路径
        String realPath = session.getServletContext().getRealPath("/img/");
        System.out.println(realPath);
        File file1 = new File(realPath);
        String name = new Date().getTime() + "_" + originalFilename;
        //设置轮播图
        if (!file1.exists())
            file1.mkdir();
        try {
            img.transferTo(new File(realPath,name));
            Banner banner = bannerDao.queryById(id);
            banner.setId(id);
            banner.setImg(name);
            session.setAttribute("image", banner.getImg());
            bannerDao.updateImg(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void easyPoiPort(String fileName,HttpServletResponse response) {
        //给出状态信息
        //给出所有数据
        List<Banner> banners = bannerDao.queryAll();
        for (Banner banner : banners) {
            //更改图片的路径
            banner.setImg("E:\\EndItem\\cmfz\\src\\main\\webapp\\img\\" + banner.getImg());
        }
        Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams("轮播图", "轮播图信息"), Banner.class, banners);
        try {
            response.setHeader("content-disposition","attachment;filename="+fileName);
            ServletOutputStream outputStream = response.getOutputStream();
            sheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sheets.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 进行前台轮播图展示
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<FrontBannerDto> showFrontBannerData() {
        return bannerDao.queryFrontBanner();
    }
}
