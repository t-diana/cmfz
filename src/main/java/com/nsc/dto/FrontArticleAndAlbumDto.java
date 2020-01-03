package com.nsc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FrontArticleAndAlbumDto implements Serializable {
    private String thumbnail;//缩略图
    private String title;
    private String author;
    private Integer type;
    private Integer set_count;
    private Date create_date;
    private String brief;
    private String score;
    private String broadcast;
}
