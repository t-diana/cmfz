package com.nsc.service.impl;

import com.nsc.dao.AdminDao;
import com.nsc.entity.Admin;
import com.nsc.service.AdminService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void poiExcel(String downloadName, HttpServletResponse response) {
        //创建excel文件
        HSSFWorkbook sheets = new HSSFWorkbook();
        //创建工作簿
        HSSFSheet adminMessage = sheets.createSheet("管理员信息");
        //创建行
        HSSFRow row = adminMessage.createRow(0);
        //创建单元格，并且赋值
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("username");
        row.createCell(2).setCellValue("password");
        //获取数据
        List<Admin> admins = adminDao.queryAll();
        for (int i = 0; i < admins.size(); i++) {
            Admin admin = admins.get(i);
            HSSFRow row1 = adminMessage.createRow(i + 1);
            row1.createCell(0).setCellValue(admin.getId());
            row1.createCell(1).setCellValue(admin.getUsername());
            row1.createCell(2).setCellValue(admin.getPassword());
        }
        try {
            response.setHeader("content-dosiposition","attachment;filename="+downloadName);
            ServletOutputStream outputStream = response.getOutputStream();
            sheets.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                sheets.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> login(String loginName, String password) {
        Admin admin = adminDao.queryByName(loginName);
        Map<String,Object> map = new HashMap<>();
        if (admin==null)
            map.put("msg","Username is Error");
        else{
            if (password.equals(admin.getPassword()))
                map=null;
            else
                map.put("msg","Password is Error");
        }
        return map;
    }
}
