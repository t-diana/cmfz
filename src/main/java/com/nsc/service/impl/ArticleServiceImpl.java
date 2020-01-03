package com.nsc.service.impl;

import com.nsc.dao.ArticleDao;
import com.nsc.dto.FrontArticleAndAlbumDto;
import com.nsc.entity.Article;
import com.nsc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> showByPage(Integer row, Integer page) {
        Map<String, Object> map = new HashMap<>();
        //当前页
        map.put("page", page);
        //总记录数
        Integer count = articleDao.selectRecords();
        map.put("records", count);
        //总页数
        Integer total = count % row == 0 ? count / row : count / row + 1;
        map.put("total", total);
        //数据记录
        List<Article> article = articleDao.selectByPage(row * (page - 1),row);
        map.put("rows", article);
        return map;
    }

    @Override
    public int addArticle(Article article) {
        return articleDao.insertSelective(article);
    }

    @Override
    public int delBulk(String[] id) {
        return articleDao.delBulk(id);
    }

    @Override
    public int updateArticle(Article article) {
        return articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<FrontArticleAndAlbumDto> showFrontArticleData() {
        return articleDao.showFrontArticleData();
    }
}
