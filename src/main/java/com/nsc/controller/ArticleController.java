package com.nsc.controller;

import com.nsc.entity.Article;
import com.nsc.service.ArticleService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/Article")
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/showArticleAll")
    public Map<String, Object> showArticleAll(Integer rows, Integer page) {
        return articleService.showByPage(rows, page);
    }

    @RequestMapping("/addArticle")
    public void addArticle(Article article) {
        System.out.println("article" + article);
        article.setId(UUID.randomUUID().toString().replace("-", ""));
        articleService.addArticle(article);
    }

    @RequestMapping("/edit")
    public void editArticle(String[] id, String oper, Article article) {
        if ("del".equals(oper)) {
            articleService.delBulk(id);
        }
    }

    @RequestMapping("/updateArticle")
    public void updateArticle(Article article) {
        System.out.println(article);
        articleService.updateArticle(article);
    }

    @RequestMapping("/kindeditor")
    public Map<String, Object> Kindeditor(MultipartFile img, String dir, HttpSession session, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //获取文件原始名
        String originalFilename = img.getOriginalFilename();
        //改变文件名 防止重名
        String name = new Date().getTime() + "_" + originalFilename;
        //获取文件路径
        String path = session.getServletContext().getRealPath("img");
        //判断文件夹存不存在
        File file = new File(path);
        if (!file.exists())
            file.mkdir();
        //进行文件上传
        try {
            img.transferTo(new File(path, name));
            //上传成功 错误信息没有
            map.put("error", 0);
            //进行图片回显
            String scheme = request.getScheme();//获取协议头
            String contextPath = request.getContextPath();//获取应用名
            InetAddress localHost = InetAddress.getLocalHost();//获取IP地址
            System.out.println("主机名+IP地址:" + localHost);
            int serverPort = request.getServerPort();//获取服务器的端口号
            String localhost = localHost.toString().split("/")[1];
            String url = scheme + "://" + localhost + ":" + serverPort + "/" + contextPath + "/img/" + name;
            map.put("url", url);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传出错");
        }
        return map;
    }

    /*
   *
   * {
   "moveup_dir_path": "",
   "current_dir_path": "",
   "current_url": "/ke4/php/../attached/",
   "total_count": 5,
   "file_list": [
       {
           "is_dir": false,
           "has_file": false,
           "filesize": 208736,
           "dir_path": "",
           "is_photo": true,
           "filetype": "jpg",
           "filename": "1241601537255682809.jpg",
           "datetime": "2018-06-06 00:36:39"
       }
    ]
}
   * */
    @RequestMapping("getAllImages")
    public Map<String, Object> getAllImages(HttpSession session, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //获取图片文件夹路径
        String path = session.getServletContext().getRealPath("/img");
        File file = new File(path);
        //拿到所有图片
        String[] list = file.list();
        List<Object> arrayList = new ArrayList<>();
        for (String s : list) {
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("is_dir", false);
            map1.put("has_file", false);
            File file1 = new File(path, s);
            long length = file1.length();
            map1.put("filesize", length);
            map1.put("dir_path", "");
            map1.put("is_photo", true);
            //获取文件后缀
            String extension = FilenameUtils.getExtension(s);
            map1.put("filetype", extension);
            map1.put("filename", s);
            //获取时间
            String s1 = s.split("_")[0];
            Long aLong = Long.valueOf(s1);
            Date date = new Date(aLong);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(date);
            map1.put("datetime", format);
            arrayList.add(map1);
        }
        map.put("file_list", arrayList);
        map.put("total_count", list.length);
        map.put("current_dir_path", "");
        map.put("moveup_dir_path", "");
        String scheme = request.getScheme();//获取协议头
        String contextPath = request.getContextPath();//获取应用名
        InetAddress localHost = null;//获取Id地址
        try {
            localHost = InetAddress.getLocalHost();
            System.out.println("主机名+IP地址:" + localHost);
            int serverPort = request.getServerPort();//获取服务器的端口号
            String localhost = localHost.toString().split("/")[1];
            String url = scheme + "://" + localhost + ":" + serverPort + contextPath + "/img/";
            map.put("current_url", url);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return map;
    }
}
