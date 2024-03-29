package com.loloao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.common.Base;
import com.loloao.common.Result;
import com.loloao.entity.*;
import com.loloao.enums.ResultCode;
import com.loloao.mapper.*;
import com.loloao.service.ArticleService;
import com.loloao.service.NotificationService;
import com.loloao.utils.UserUtils;
import com.loloao.vo.ArticleVo;
import com.loloao.vo.PageVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (Article)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 18:12:55
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    public ArticleMapper articleMapper;

    @Resource
    public CategoryMapper categoryMapper;

    @Resource
    public UserMapper userMapper;

    @Resource
    public ArticleBodyMapper articleBodyMapper;

    @Resource
    public TagMapper tagMapper;

    @Resource
    public ArticleTagMapper articleTagMapper;

    @Resource
    public StarMapper starMapper;

    @Resource
    public CommentMapper commentMapper;

    @Resource
    public NotificationService notificationService;

    @Override
    public List<Article> listArticles(PageVo pageVo) {
        Page<Article> page = new Page<>(pageVo.getPageNumber(), pageVo.getPageSize());
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        // query condition
        //wrapper.like(StringUtils.isNotBlank(queryName), "g.name", queryName);
        wrapper.orderBy(
                StringUtils.isNotBlank(pageVo.getName()) || StringUtils.isNotBlank(pageVo.getSort()),
                pageVo.getSort().equalsIgnoreCase("asc"),
                pageVo.getName()
        );
        IPage<Article> result = articleMapper.selectPage(page, wrapper);
        return result.getRecords();
    }
    //IPage<GoodsVo> listSmart(IPage<GoodsVo> page, Wrapper wrapper);

    /**
     * Page<GoodsVo> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
     *         QueryWrapper<Goods> wrapper = new QueryWrapper<>();
     *
     *         // query condition
     *         wrapper.like(StringUtils.isNotBlank(queryName), "g.name", queryName);
     *         // result
     *         IPage<GoodsVo> result = goodsService.listSmart(page, wrapper);
     *         //System.out.println("Total count:" + result.getTotal());
     *         return Result.success(result.getRecords(), result.getTotal());
     */

    @Override
    public List<Article> listArticles(ArticleVo article, PageVo pageVo) {
        int cur = pageVo.getPageNumber().intValue();
        int size = pageVo.getPageSize().intValue();
        //IPage<Article> page = new Page<>(pageVo.getPageNumber(), pageVo.getPageSize());
        //System.out.println("page num: " + pageVo.getPageNumber() + " page size: " + pageVo.getPageSize());

        //filter query conditions
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtils.isNotNull(article.getYear()),"DATE_FORMAT (create_date,'%Y')", article.getYear());
        wrapper.eq(ObjectUtils.isNotNull(article.getMonth()),"DATE_FORMAT (create_date,'%m')", article.getMonth());
        // my articles, query articles by authorId
        wrapper.eq(ObjectUtils.isNotNull(article.getAuthorId()),"author_id", article.getAuthorId());
        // my stars, query articles starred by uid
        if(ObjectUtils.isNotNull(article.getStarUid())){
            List<Integer> articleIdList = articleMapper.getArticleIdsByStarUid(article.getStarUid());
            if(articleIdList.size() > 0) {
                wrapper.in("id", articleIdList);
            }else{
                // no star articles fulfilled query condition, return empty list
                return new ArrayList<>();
            }
        }
        // query tag
        if(ObjectUtils.isNotNull(article.getTagId())) {
            List<Integer> articleIdList = articleMapper.getArticleIdsByTagId(article.getTagId());
            if(articleIdList.size() > 0) {
                wrapper.in("id", articleIdList);
            }else{
                // no tag fulfilled query condition, return empty list
                return new ArrayList<>();
            }
        }
        wrapper.eq(ObjectUtils.isNotNull(article.getCategoryId()), "category_id", article.getCategoryId());
        // search (summary and title)
        wrapper.like(StringUtils.isNotBlank(article.getKeyword()), "CONCAT(summary,title)", article.getKeyword());
        // manual pagination by using 'LIMIT'
        wrapper.last("ORDER BY create_date DESC LIMIT " + ((cur - 1) * size) + ", " + size);


        //package result list
        List<Article> list = fillAuthorCategoryTagsById(articleMapper.selectList(wrapper));

        return list;
    }

    // handle many to one relationship
    private List<Article> fillAuthorCategoryTagsById(List<Article> list){
        for(Article a : list){
            User author = userMapper.selectById(a.getAuthorId());
            Category category = categoryMapper.selectById(a.getCategoryId());
            List<Tag> tags = tagMapper.getTagsByArticleId(a.getId());
            a.setAuthor(author);
            a.setCategory(category);
            a.setTags(tags);
        }
        return list;
    }


    // handle many to one relationship
    private List<Article> fillAuthorCategoryBodyByIds(List<Article> list){
        for(Article article : list){
            fillAuthorCategoryBodyTagsById(article);
        }
        return list;
    }

    /**
     * because we can only get authorId,categoryId,bodyId from database
     * so this method is to fill in the object by these ids
     * @param article
     * @return
     */
    private Article fillAuthorCategoryBodyTagsById(Article article){
            article.setAuthor(userMapper.selectById(article.getAuthorId()));
            article.setCategory(categoryMapper.selectById(article.getCategoryId()));
            article.setBody(articleBodyMapper.selectById(article.getBodyId()));
            article.setTags(tagMapper.getTagsByArticleId(article.getId()));
        return article;
    }


    /*@Override
    public List<Article> listArticles(ArticleVo article, PageVo pageVo) {
        Page<Article> page = new Page<>(pageVo.getPageNumber(), pageVo.getPageSize());
        System.out.println("page num: " + pageVo.getPageNumber() + " page size: " + pageVo.getPageSize());
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        // query condition
        //wrapper.like(StringUtils.isNotBlank(queryName), "g.name", queryName);
        wrapper.eq(ObjectUtils.isNotNull(article.getYear()),"DATE_FORMAT (created_time,'%Y')", article.getYear());
        wrapper.eq(ObjectUtils.isNotNull(article.getMonth()),"DATE_FORMAT (created_time,'%m')", article.getMonth());
        wrapper.orderBy(
                StringUtils.isNotBlank(pageVo.getName()) && StringUtils.isNotBlank(pageVo.getSort()) && "a.createDate".equals(pageVo.getName()),
                pageVo.getSort().equalsIgnoreCase("asc"),
                "me_article.create_date",
                "me_article.id"
        );
        //wrapper.last("LIMIT " + pageVo.getPageNumber() + ", " + pageVo.getPageSize());
        IPage<Article> result = articleMapper.selectPage(page, wrapper);
        //List<Article> list = articleMapper.selectList(wrapper);

        // handle many to one relationship
        List<Article> list = result.getRecords();
        for(Article a : list){
            User author = userMapper.selectById(a.getAuthorId());
            Category category = categoryMapper.selectById(a.getCategoryId());
            a.setAuthor(author);
            a.setCategory(category);
        }
        return list;
    }*/

    @Override
    public List<Article> findAll() {
        return articleMapper.selectList(null);
    }

    @Override
    public Article getArticleById(Integer id) {
        Article article = articleMapper.selectById(id);
        // fill author, category, body by id
        article = fillAuthorCategoryBodyTagsById(article);
        return article;
    }

    @Override
    public Long publishArticle(Article article) {
        if(null != article.getId()){
            return this.updateArticle(article);
        }else{
            return this.saveArticle(article);
        }
    }

    @Override
    @Transactional
    public Long saveArticle(Article article) {

        // fill in properties
        User currentUser = UserUtils.getCurrentUser();
        if (null != currentUser) {
            article.setAuthor(currentUser);
            article.setAuthorId(currentUser.getId());
        }
        article.setViewCounts(0);
        article.setCommentCounts(0);
        article.setCategoryId(article.getCategory().getId());
        article.setCreateDate(new Date());
        article.setWeight(Article.Article_Common);

        // save body table
        ArticleBody body = new ArticleBody();
        body.setContent(article.getBody().getContent());
        body.setContentHtml(article.getBody().getContentHtml());
        articleBodyMapper.insert(body);
        // update body_id
        article.setBodyId(body.getId());

        // save article table
        articleMapper.insert(article);

        // save article-tag relationships table
        // create new article-tag relationships
        for(Tag tag: article.getTags()){
            articleTagMapper.insert(new ArticleTag(article.getId(), tag.getId()));
        }

        //
        //update notification
        updateNotification(article, "published");

        return article.getId();
    }

    /**
     * get 'article' from frontend
     * udpate 'oldArticle' in database
     * @param article
     * @return
     */
    @Override
    @Transactional
    public Long updateArticle(Article article) {
        Article oldArticle = articleMapper.selectById(article.getId());

        // update basic information
        oldArticle.setTitle(article.getTitle());
        oldArticle.setSummary(article.getSummary());

        // update category id
        oldArticle.setCategory(article.getCategory());
        oldArticle.setCategoryId(article.getCategory().getId());

        articleMapper.updateById(oldArticle);

        // update body table
        oldArticle.setBody(article.getBody());
        ArticleBody body = new ArticleBody(
                oldArticle.getBodyId(),
                article.getBody().getContent(),
                article.getBody().getContentHtml()
        );
        articleBodyMapper.updateById(body);


        // update article tags table
        // delete old article-tag relationships
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId, oldArticle.getId());
        articleTagMapper.delete(wrapper);
        // create new article-tag relationships
        oldArticle.setTags(article.getTags());
        for(Tag tag: oldArticle.getTags()){
            articleTagMapper.insert(new ArticleTag(oldArticle.getId(), tag.getId()));
        }

        //update notification
        updateNotification(oldArticle, "updated");

        return oldArticle.getId();
    }

    @Override
    @Transactional
    public void deleteArticleById(Long id) {
        //delete article-tag relationships
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, id));
        //delete star (article-user)
        starMapper.delete(new LambdaQueryWrapper<Star>().eq(Star::getArticleId, id));
        //delete article comments
        commentMapper.delete(new LambdaQueryWrapper<Comment>().eq(Comment::getArticleId, id));
        //delete article body
        Article delArticle = articleMapper.selectById(id);
        articleBodyMapper.delete(new LambdaQueryWrapper<ArticleBody>().eq(ArticleBody::getId, delArticle.getBodyId()));
        //delete article
        articleMapper.deleteById(id);

        //update notification
        updateNotification(delArticle, "deleted");
    }

    private void updateNotification(Article article, String operation){

        User author = userMapper.selectById(article.getAuthorId());

        Notification notification = new Notification();
        // get notification author
        notification.setType(Base.NOTIFICATION_SYSTEM_TYPE);
        String title = "You " + operation +  " an article: <<" + article.getTitle() + ">>";
        notification.setTitle("WOW! " + title);
        notification.setContent(title + " Thank you for using our LoLoBlog platform! Have a nice day!");

        // setup basic information and add notification
        notificationService.addNotificationAndUpdateUnreadCounts(author, notification);
    }

    @Override
    public List<Article> listArticlesByTag(Integer id) {
        return null;
    }

    @Override
    public List<Article> listArticlesByCategory(Integer id) {
        return null;
    }

    @Override
    public Article getArticleAndAddViews(Integer id) {
        Article article = articleMapper.selectById(id);

        // update view counts
        article.setViewCounts(article.getViewCounts() + 1);

        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getId, id);
        articleMapper.update(article, wrapper);

        // fill author, category, body by id
        article = fillAuthorCategoryBodyTagsById(article);
        return article;
    }

    @Override
    public List<Article> listHotArticles(int limit) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Article::getViewCounts)
                .last("limit " + limit);
        return articleMapper.selectList(wrapper);
    }

    @Override
    public List<Article> listNewArticles(int limit) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Article::getCreateDate)
                .last("limit " + limit);
        return articleMapper.selectList(wrapper);
    }

    @Override
    public List<ArticleVo> listArchives() {
        return articleMapper.listArchives();
    }
}
