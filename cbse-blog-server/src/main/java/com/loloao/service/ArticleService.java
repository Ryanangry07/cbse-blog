package com.loloao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loloao.entity.Article;
import com.loloao.vo.ArticleVo;
import com.loloao.vo.PageVo;

import java.util.List;


/**
 * (Article)表服务接口
 *
 * @author makejava
 * @since 2023-12-09 18:12:55
 */
public interface ArticleService extends IService<Article> {

    List<Article> listArticles(PageVo page);

    List<Article> listArticles(ArticleVo article, PageVo page);

    List<Article> findAll();

    Article getArticleById(Integer id);

    Integer publishArticle(Article article);

    Integer saveArticle(Article article);

    Integer updateArticle(Article article);

    void deleteArticleById(Integer id);

    List<Article> listArticlesByTag(Integer id);

    List<Article> listArticlesByCategory(Integer id);

    Article getArticleAndAddViews(Integer id);

    List<Article> listHotArticles(int limit);

    List<Article> listNewArticles(int limit);

    List<ArticleVo> listArchives();
}
