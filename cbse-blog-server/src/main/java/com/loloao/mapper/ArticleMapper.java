package com.loloao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loloao.entity.Article;
import com.loloao.vo.ArticleVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * (Article)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-09 18:20:48
 */
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("SELECT m.*, YEAR(create_date) AS year,MONTH(create_date) AS month, COUNT(*) AS count " +
            "FROM me_article as m GROUP BY year, month")
    List<ArticleVo> listArchives();

    /*@Select("select a.category_id count(a.category_id) as count from me_category c" +
            " left join me_article a on a.category_id = c.id where a.category_id = #{id} group by c.id ")
    int getCountArticleByCategoryId(Integer id);

    @Select("select a.tag_id, count(a.tag_id) as count from me_tag t" +
            " left join me_article_tag at on at.tag_id = t.id where at.tag_id = #{id} group by t.id ")
    int getCountArticleByTagId(Integer id);*/

    @Select("SELECT COUNT(*) AS count FROM me_article a WHERE a.category_id = #{id}")
    int getCountArticleByCategoryId(Integer id);

    @Select("SELECT COUNT(*) AS count FROM me_article_tag at WHERE at.tag_id = #{id}")
    int getCountArticleByTagId(Integer id);

    @Select("SELECT at.article_id FROM me_article_tag at WHERE at.tag_id = #{tagId}")
    List<Integer> getArticleIdsByTagId(Long tagId);
}
