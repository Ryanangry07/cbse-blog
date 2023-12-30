package com.loloao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loloao.entity.Tag;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-09 19:10:18
 */
public interface TagMapper extends BaseMapper<Tag> {

        @Select("SELECT t.*, count(at.tag_id) as count FROM me_article_tag at " +
                        "RIGHT JOIN  me_tag t  ON  at.tag_id=t.id GROUP BY at.tag_id ORDER BY count DESC LIMIT #{limit}")
        List<Tag> listHotTagsByArticleUse(int limit);

        @Select("SELECT t.* FROM me_tag t LEFT JOIN me_article_tag at ON t.id = at.tag_id" +
                        " WHERE at.article_id = #{articleId}")
        List<Tag> getTagsByArticleId(long articleId);

        @Select("SELECT t.id FROM me_tag t WHERE t.tagname = #{tagname} ORDER BY id DESC LIMIT 1")
        Integer getTagID(String tagname);
}
