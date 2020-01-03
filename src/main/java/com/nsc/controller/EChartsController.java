package com.nsc.controller;

import com.nsc.dto.MapDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequestMapping("/ECharts")
@RestController
public class EChartsController {

    @RequestMapping("/showData")
    public List<MapDto> showData(){
        List<MapDto> mapDtos = new ArrayList<>();
        String[] provinces={"北京","上海","深圳","重庆","天津","黑龙江","辽宁","吉林","河北","山东","江苏","浙江",
                "江西","福建","广东","台湾",};
        for (int i = 0; i < provinces.length; i++) {
            String province = provinces[i];
            mapDtos.add(new MapDto(province,new Random().nextInt(1000)));
        }
        return mapDtos;
    }
}
