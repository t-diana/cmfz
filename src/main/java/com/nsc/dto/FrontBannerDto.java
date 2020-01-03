package com.nsc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FrontBannerDto implements Serializable {
    private String thumbnail;//图片路径
    private String desc;//图片描述
    private String id;//图片id
}
