package com.nsc.service.impl;

import com.nsc.dao.ChapterDao;
import com.nsc.dto.ChapterDto;
import com.nsc.entity.Chapter;
import com.nsc.service.ChapterService;


import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterDao chapterDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> showByPage(Integer row, Integer page, String albumId) {
        Map<String, Object> map = new HashMap<>();
        //当前页
        map.put("page", page);
        //总记录数
        Integer count = chapterDao.queryCount(albumId);
        map.put("records", count);
        //总页数
        Integer total = count % row == 0 ? count / row : count / row + 1;
        map.put("total", total);
        //数据记录
        List<Chapter> chapters = chapterDao.queryByPage(row * (page - 1), albumId, row);
        map.put("rows", chapters);
        return map;
    }

    @Override
    public int addChapter(Chapter chapter) {
        return chapterDao.insertSelective(chapter);
    }

    @Override
    public void audioUpload(MultipartFile file, String id, HttpSession session) {
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件路径并判断文件路径存不存在
        String realPath = session.getServletContext().getRealPath("/mp3/");
        File file1 = new File(realPath);
        if (!file1.exists())
            file1.mkdir();
        //一般来说文件夹存在 进行文件上传 上传的是文件名，不是文件路径
        String fileName = new Date().getTime() + "_" + originalFilename;
        //获取文件时长
        try {
            //将文件上传到指定目录
            file.transferTo(new File(realPath, fileName));
            //读取指定目录的文件
            AudioFile read = AudioFileIO.read(new File(realPath, fileName));
            AudioHeader audioHeader = read.getAudioHeader();
            //获取时长
            int trackLength = audioHeader.getTrackLength();
            String seconds = trackLength % 60 + "秒";
            String minute = trackLength / 60 + "分";
            //获取数据
            Chapter chapter = chapterDao.selectByPrimaryKey(id);
            //设置时长
            chapter.setDuration(minute + seconds);
            //设置文件大小
            String size = file.getSize() / 1024 / 1024 + "MB";//文件大小 默认为字节
            chapter.setSize(size);
            //设置文件路径 直接为文件的文件名
            chapter.setSrc(fileName);
            //将数据库的数据进行更新
            chapterDao.updateByPrimaryKeySelective(chapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void audioDownload(String src, HttpServletResponse response, HttpSession session) {
        //设置下载文件名
        String filename = src.split("_")[1];
        //判断文件夹存不存在
        String realPath = session.getServletContext().getRealPath("/mp3");
        //准备下载的文件
        File file = new File(realPath, src);
        //设置编码
        String encode = null;
        try {
            encode = URLEncoder.encode(filename, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置请求头
        response.setHeader("content-disposition", "attachment;filename=" + encode);
        //进行文件拷贝
        //获取一个文件输出流
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            long l = FileUtils.copyFile(file, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int delBulk(String[] id) {
        return chapterDao.deleteBulk(id);
    }

    @Override
    public int update(Chapter chapter) {
        return chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ChapterDto> queryFrontData(String id) {
        return chapterDao.queryFrontData(id);
    }
}
