package com.nsc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDto implements Serializable {
    private String title;
    private String download_url;
    private String size;
    private String duration;
}
