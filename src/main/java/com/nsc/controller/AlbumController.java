package com.nsc.controller;

import com.nsc.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Album")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping("/AlbumShowByPage")
    public Map<String,Object> albumShowByPage(Integer rows,Integer page){
        return albumService.showByPage(rows,page);
    }
}
