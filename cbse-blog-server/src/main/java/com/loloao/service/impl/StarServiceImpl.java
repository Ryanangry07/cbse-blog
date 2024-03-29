package com.loloao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.common.Base;
import com.loloao.entity.Article;
import com.loloao.entity.Notification;
import com.loloao.entity.Star;
import com.loloao.entity.User;
import com.loloao.mapper.ArticleMapper;
import com.loloao.mapper.StarMapper;
import com.loloao.mapper.UserMapper;
import com.loloao.service.NotificationService;
import com.loloao.service.StarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("starService")
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements StarService {

    @Resource
    public StarMapper starMapper;

    @Resource
    public ArticleMapper articleMapper;

    @Resource
    public UserMapper userMapper;

    @Resource
    public NotificationService notificationService;


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
    @Transactional
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

            // update notification when user give star
            updateNotification(star);
        }
        //result
        result.put("starStatus", newStarStatus);
        result.put("starCounts", newCount);
        return result;
    }

    private void updateNotification(Star star){

        Article article = articleMapper.selectById(star.getArticleId());
        User fromUser = userMapper.selectById(star.getUserId());
        User author = userMapper.selectById(article.getAuthorId());

        //if author give the star to his own article, then do nothing
        if(fromUser.getId() == author.getId()){
            return;
        }

        Notification notification = new Notification();
        notification.setFromUid(fromUser.getId());
        notification.setFromUser(fromUser.getAccount());
        // get notification author
        notification.setType(Base.NOTIFICATION_AT_ME_TYPE);
        notification.setTitle("Congratulations! Someone likes your article!");
        notification.setContent("| " + fromUser.getAccount() + " | give a star to your article: <<" + article.getTitle()
                + ">>, current star counts is (" + article.getStarCounts() + ")");

        notificationService.addNotificationAndUpdateUnreadCounts(author, notification);
    }
}
