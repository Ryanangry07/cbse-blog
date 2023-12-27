package com.loloao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.common.Base;
import com.loloao.entity.Article;
import com.loloao.entity.Star;
import com.loloao.mapper.ArticleMapper;
import com.loloao.mapper.StarMapper;
import com.loloao.service.StarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("starService")
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements StarService {

    @Resource
    public StarMapper starMapper;

    @Resource
    public ArticleMapper articleMapper;

    @Override
    public String loadStar(Star star) {
        // check user star or not before
        boolean isStar = checkStar(star);
        // return what we get from database
        return isStar ? Base.STAR_ON : Base.STAR_OFF;
    }

    private boolean checkStar(Star star){
        LambdaQueryWrapper<Star> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Star::getArticleId, star.getArticleId())
                .eq(Star::getUserId, star.getUserId());
        List<Star> list = starMapper.selectList(wrapper);
        return list.size() > 0;
    }

    @Override
    public Map<String, Object> starArticle(Star star) {
        // prepare map result
        Map<String, Object> result = new HashMap<>();
        Integer newCount = 0;
        String newStarStatus = Base.STAR_OFF;

        //check star
        boolean isStar = checkStar(star);

        if(isStar){
            // if star, return star-off
            newStarStatus = Base.STAR_OFF;
            // remove star
            Map<String, Object> columnMap = new HashMap<String, Object>();
            columnMap.put("user_id", star.getUserId());
            columnMap.put("article_id", star.getArticleId());
            starMapper.deleteByMap(columnMap);

            // article star count - 1
            Article article = articleMapper.selectById(star.getArticleId());
            newCount = article.getStarCounts() - 1;
            article.setStarCounts(newCount);
            articleMapper.updateById(article);

        }else {
            // if not, return star-on
            // star on article
            starMapper.insert(star);
            newStarStatus = Base.STAR_ON;
            // article star count + 1
            Article article = articleMapper.selectById(star.getArticleId());
            newCount = article.getStarCounts() + 1;
            article.setStarCounts(newCount);
            articleMapper.updateById(article);
        }
        //result
        result.put("starStatus", newStarStatus);
        result.put("starCounts", newCount);
        return result;
    }
}
