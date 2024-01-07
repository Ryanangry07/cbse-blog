package com.loloao.controller;



import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.loloao.common.Base;
import com.loloao.common.Result;
import com.loloao.entity.Article;
import com.loloao.entity.ArticleBody;
import com.loloao.entity.Tag;
import com.loloao.entity.User;
import com.loloao.enums.ResultCode;
import com.loloao.service.ArticleService;
import com.loloao.service.TagService;
import com.loloao.utils.UserUtils;
import com.loloao.vo.ArticleVo;
import com.loloao.vo.PageVo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Article)表控制层
 *
 * @author makejava
 * @since 2023-12-09 18:12:50
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {
    /**
     * 服务对象
     */
    @Resource
    private ArticleService articleService;

    @GetMapping
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Article.class, props = {"body", "category", "comments"}),
                    @FastJsonFilter(clazz = Tag.class, props = {"id", "avatar"})},
            include = {@FastJsonFilter(clazz = User.class, props = {"nickname"})})
    public Result listArticles(ArticleVo article, PageVo page) {
        System.out.println(article);
        System.out.println(page);
        List<Article> articles = articleService.listArticles(article, page);
        return Result.success(articles);
    }

    /**
     * get hot top ranking articles
     * @return
     */
    @GetMapping("/hot")
    @FastJsonView(include = {@FastJsonFilter(clazz = Article.class, props = {"id", "title"})})
    public Result listHotArticles() {
        int limit = 6;
        List<Article> articles = articleService.listHotArticles(limit);

        return Result.success(articles);
    }

    /**
     * get new articles
     * @return
     */
    @GetMapping("/new")
    @FastJsonView(include = {@FastJsonFilter(clazz = Article.class, props = {"id", "title"})})
    public Result listNewArticles() {
        int limit = 6;
        List<Article> articles = articleService.listNewArticles(limit);

        return Result.success(articles);
    }


    @GetMapping("/{id}")
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Article.class, props = {"comments"}),
                    @FastJsonFilter(clazz = ArticleBody.class, props = {"contentHtml"})})
    public Result getArticleById(@PathVariable("id") Integer id) {

        Result result = new Result();

        if (null == id) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }

        Article article = articleService.getArticleById(id);

        result.setResultCode(ResultCode.SUCCESS);
        result.setData(article);
        return result;
    }

    @GetMapping("/view/{id}")
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Article.class, props = {"comments"}),
                    @FastJsonFilter(clazz = ArticleBody.class, props = {"contentHtml"}),
                    @FastJsonFilter(clazz = Tag.class, props = {"avatar"})},
            include = {@FastJsonFilter(clazz = User.class, props = {"id", "nickname", "avatar", "aboutMe", "aboutMeVisible", "email", "mobilePhoneNumber"})})
    public Result getArticleAndAddViews(@PathVariable("id") Integer id) {

        Result result = new Result();

        if (null == id) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }

        Article article = articleService.getArticleAndAddViews(id);

        result.setResultCode(ResultCode.SUCCESS);
        result.setData(article);
        return result;
    }

    @GetMapping("/tag/{id}")
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Article.class, props = {"body", "category", "comments"}),
                    @FastJsonFilter(clazz = Tag.class, props = {"id", "avatar"})},
            include = {@FastJsonFilter(clazz = User.class, props = {"nickname"})})
    public Result listArticlesByTag(@PathVariable Integer id) {
        List<Article> articles = articleService.listArticlesByTag(id);

        return Result.success(articles);
    }


    @GetMapping("/category/{id}")
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Article.class, props = {"body", "category", "comments"}),
                    @FastJsonFilter(clazz = Tag.class, props = {"id", "avatar"})},
            include = {@FastJsonFilter(clazz = User.class, props = {"nickname"})})
    public Result listArticlesByCategory(@PathVariable Integer id) {
        List<Article> articles = articleService.listArticlesByCategory(id);

        return Result.success(articles);
    }

    @PostMapping("/publish")
    @RequiresAuthentication
    public Result saveArticle(@Validated @RequestBody Article article) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        Long articleId = articleService.publishArticle(article);

        Result result = Result.success();
        result.simple().put("articleId", articleId);
        return result;
    }

    @PostMapping("/update")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result updateArticle(@RequestBody Article article) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }
        Result result = new Result();

        if (null == article.getId()) {
            result.setResultCode(ResultCode.USER_NOT_EXIST);
            return result;
        }

        Long articleId = articleService.updateArticle(article);

        result.setResultCode(ResultCode.SUCCESS);
        result.simple().put("articleId", articleId);
        return result;
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result deleteArticleById(@PathVariable("id") Long id) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }
        Result result = new Result();

        if (null == id) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }

        articleService.deleteArticleById(id);

        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    @GetMapping("/listArchives")
    public Result listArchives() {
        return Result.success(articleService.listArchives());
    }
}

