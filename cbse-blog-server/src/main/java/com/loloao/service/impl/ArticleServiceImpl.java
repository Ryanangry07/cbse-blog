package com.loloao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.Article;
import com.loloao.mapper.ArticleMapper;
import com.loloao.service.ArticleService;
import com.loloao.vo.ArticleVo;
import com.loloao.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<Article> listArticles(ArticleVo article, PageVo page) {
        return null;
    }

    @Override
    public List<Article> findAll() {
        return null;
    }

    @Override
    public Article getArticleById(Integer id) {
        return null;
    }

    @Override
    public Integer publishArticle(Article article) {
        return null;
    }

    @Override
    public Integer saveArticle(Article article) {
        return null;
    }

    @Override
    public Integer updateArticle(Article article) {
        return null;
    }

    @Override
    public void deleteArticleById(Integer id) {

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
        return null;
    }

    @Override
    public List<Article> listHotArticles(int limit) {
        return null;
    }

    @Override
    public List<Article> listNewArticles(int limit) {
        return null;
    }

    @Override
    public List<ArticleVo> listArchives() {
        return null;
    }
}
