package com.nsc.dao;

import com.nsc.dto.FrontBannerDto;
import com.nsc.entity.Banner;

import java.util.List;

public interface BannerDao {
    //添加图片
    void insertImg(Banner banner);
    //分页展示数据
    List<Banner> queryByPage(Integer start,Integer row);
    //总条数
    Integer queryTotal();
    //更新数据
    void updateImg(Banner banner);
    //删除数据
    void deleteBulk(String[] ids);
    //根据id去重
    Banner queryById(String id);
    //展示激活的轮播图
    List<Banner> showActive(String status);
    //下载轮播图的excel表格
    List<Banner> queryAll();
    //展示前台图片数据
    List<FrontBannerDto> queryFrontBanner();
}
