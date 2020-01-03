package com.nsc.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Banner implements Serializable {
    @Excel(name = "编号")
    private String id;
    @Excel(name = "标题")
    private String title;
    @Excel(name = "图片",type = 2,height = 24.72,width = 40)
    private String img;//图片路径
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传日期",format = "yyyy-MM-dd")
    private Date create_date;
    @Excel(name = "状态")
    private String status;
    private String other;
}
