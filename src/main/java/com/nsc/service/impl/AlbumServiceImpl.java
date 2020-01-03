package com.nsc.service.impl;

import com.nsc.dao.AlbumDao;
import com.nsc.dto.FrontArticleAndAlbumDto;
import com.nsc.entity.Album;
import com.nsc.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> showByPage(Integer row, Integer page) {
        Map<String, Object> map = new HashMap<>();
        //当前页
        map.put("page",page);
        //总记录数
        Integer records = albumDao.queryCount();
        map.put("records",records);
        //总页数
        Integer total = albumDao.queryCount()%row==0?albumDao.queryCount()/row:albumDao.queryCount()/row+1;
        map.put("total",total);
        //展示数据
        List<Album> rows = albumDao.queryByPage(row * (page - 1), row);
        map.put("rows",rows);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<FrontArticleAndAlbumDto> showFrontAlbumData() {
        return albumDao.showFrontAlbumData();
    }

    @Override
    public FrontArticleAndAlbumDto showFrontAlbumDetailData(String id) {
        return albumDao.showFrontAlbumDetailData(id);
    }

}
