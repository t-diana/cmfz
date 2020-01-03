package com.nsc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Album implements Serializable {
    private String id;

    private String title;

    private String img;

    private String score;

    private String author;

    private String broadcaster;//播音员

    private String count;

    private String brief;//简介

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private String status;

    private String other;
}