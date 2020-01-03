package com.nsc.service;

import com.nsc.dto.ChapterDto;
import com.nsc.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ChapterService {
    Map<String,Object> showByPage(Integer row,Integer page,String albumId);
    //添加章节
    int addChapter(Chapter chapter);
    //文件上传
    void audioUpload(MultipartFile file, String id, HttpSession session);
    //文件下载
    void audioDownload(String src, HttpServletResponse response, HttpSession session);
    //批量删除
    int delBulk(String[] id);
    //修改
    int update(Chapter chapter);
    //展示前台章节细节数据
    List<ChapterDto> queryFrontData(String id);
}
