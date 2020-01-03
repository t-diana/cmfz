package com.nsc;

import com.nsc.dao.*;
import com.nsc.dto.MapDto;
import com.nsc.entity.Album;
import com.nsc.entity.Banner;
import com.nsc.entity.Chapter;
import com.nsc.entity.User;
import com.nsc.service.AlbumService;
import com.nsc.service.UserService;
import com.nsc.service.impl.AlbumServiceImpl;
import com.nsc.service.impl.UserServiceImpl;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class CmfzApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterDao chapterDao;

    @Test
    public void contextLoads() {
        Chapter chapter = chapterDao.selectByPrimaryKey("770b45c5-9101-4508-a3cd-8a2dcc332239");
        String[] split = chapter.getSrc().split("/mp3/");
        System.out.println(split[1]);
    }

    @Test
    public void excel() throws IOException {
        //创建文件
        HSSFWorkbook sheets = new HSSFWorkbook();

        //更改时间显示样式
        HSSFDataFormat dataFormat = sheets.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd hh:mm:ss");
        //设置单元格样式
        HSSFCellStyle cellStyle = sheets.createCellStyle();
        cellStyle.setDataFormat(format);
        //创建表格
        HSSFSheet album = sheets.createSheet("专辑信息");
        album.setColumnWidth(3,20*256);
        //创建行
        HSSFRow row = album.createRow(0);//表头
        //表头数据
        String[] titles = {"id", "title", "img", "createDate", "status"};
        //创建单元格
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            row.createCell(i).setCellValue(title);
        }
        //进行赋值

        //查看数据库数据
        List<Banner> banners = bannerDao.queryByPage(0, 5);
        for (int i = 0; i < banners.size(); i++) {

            Banner banner = banners.get(i);
            //创建行
            HSSFRow row1 = album.createRow(i + 1);
            row1.createCell(0).setCellValue(banner.getId());
            row1.createCell(1).setCellValue(banner.getTitle());
            row1.createCell(2).setCellValue(banner.getImg());
            row1.createCell(3).setCellValue(banner.getCreate_date());
            row1.createCell(3).setCellStyle(cellStyle);
            row1.createCell(4).setCellValue(banner.getStatus());
        }
        //写出到指定文件夹
        sheets.write(new FileOutputStream("D:/album.xls"));
        sheets.close();
    }
    @Test
    public void test3(){
//        User user = new User(UUID.randomUUID().toString(), "dfsf", "sfdsdf", "func", null, null, null, null, null, null, new Date(), new Date(), null, null, null);
//        int i = userService.registerUser(user);
//        List<MapDto> mapDtos = userDao.querySomeMonthRegister();
//        for (MapDto mapDto : mapDtos) {
//            System.out.println(mapDto);
//        }
    }
}
