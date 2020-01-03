package com.nsc.controller;

import com.nsc.entity.Chapter;
import com.nsc.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/Chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/showChapterAll")
    public Map<String,Object> showChapterAll(Integer rows,Integer page,String albumId){
        return chapterService.showByPage(rows,page,albumId);
    }
    @RequestMapping("/chapterEdit")
    public Map<String,String> chapterEdit(Chapter chapter, String oper, HttpSession session,String[] id){
        Map<String, String> map = new HashMap<>();
        System.out.println("chapter"+chapter);
        if("add".equals(oper)){
            chapter.setId(UUID.randomUUID().toString());
            chapterService.addChapter(chapter);
            map.put("chapterId",chapter.getId());
        }else if ("del".equals(oper)){
            chapterService.delBulk(id);
        }else if ("edit".equals(oper)){
            if ("".equals(chapter.getSrc())) {
                map.put("chapterId", null);
                chapter.setSrc(null);
            }
            else
                map.put("chapterId",chapter.getId());
            chapterService.update(chapter);
        }
        return map;
    }

    @RequestMapping("/chapterUpload")
    public void chapterUpload(MultipartFile src,String id,HttpSession session){
        chapterService.audioUpload(src,id,session);
    }
    @RequestMapping("/download")
    public void chapterDownload(String src,HttpServletResponse response,HttpSession session){
        chapterService.audioDownload(src, response, session);
    }
}
