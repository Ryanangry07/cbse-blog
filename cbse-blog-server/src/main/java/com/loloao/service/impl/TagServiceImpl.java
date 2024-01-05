package com.loloao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.Article;
import com.loloao.entity.ArticleTag;
import com.loloao.entity.Category;
import com.loloao.entity.Tag;
import com.loloao.mapper.ArticleMapper;
import com.loloao.mapper.ArticleTagMapper;
import com.loloao.mapper.TagMapper;
import com.loloao.service.TagService;
import com.loloao.vo.CategoryVO;
import com.loloao.vo.TagVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 19:10:18
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Resource
    public TagMapper tagMapper;

    @Resource
    public ArticleMapper articleMapper;

    @Resource
    public ArticleTagMapper articleTagMapper;

    @Override
    public List<Tag> findAll() {
        return null;
    }

    @Override
    public Tag getTagById(Integer id) {
        return null;
    }

    @Override
    public Integer getTagIDByName(String name) {
        return tagMapper.getTagID(name);
    }

    @Override
    public Integer saveTag(Tag tag) {
        return tagMapper.insert(tag);
    }

    @Override
    public Integer updateTag(Tag tag) {
        return tagMapper.updateById(tag);
    }

    @Override
    public void deleteTagById(String id) {
        List<Integer> articlesID = articleMapper.getArticleIdsByTagId(Long.parseLong(id));
        Integer othersID = tagMapper.getTagID("Others");

        for (Integer articleID : articlesID) {
            // Check if article had more than one tag assigned
            LambdaQueryWrapper<ArticleTag> recordNum = new LambdaQueryWrapper<>();
            recordNum.eq(ArticleTag::getArticleId, articleID);
            Integer count = articleTagMapper.selectCount(recordNum);
            if (count <= 1) { // assign with Others if only one
                articleTagMapper.insert(new ArticleTag(articleID.longValue(), othersID.longValue()));
            }
        }
        // delete all record with id assigned in me_article_tag & me_tag
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getTagId, id);
        articleTagMapper.delete(wrapper);
        tagMapper.deleteById(id);
    }

    @Override
    public Integer mergeTag(String[] oldTags, String newTagName) {
        Integer row = saveTag(new Tag(null, null, newTagName));
        Integer newTagID = tagMapper.getTagID(newTagName);

        for (String tagID : oldTags) {

            // Get All Articles previously assigned to this tag
            List<Integer> articlesID = articleMapper.getArticleIdsByTagId(Long.parseLong(tagID));
            for (Integer articleID : articlesID) {
                // For each article, add new tag for it
                LambdaQueryWrapper<ArticleTag> nonDuplicate = new LambdaQueryWrapper<>();
                nonDuplicate.eq(ArticleTag::getTagId, newTagID);
                nonDuplicate.eq(ArticleTag::getArticleId, articleID);

                ArticleTag duplicatedRecord = articleTagMapper.selectOne(nonDuplicate);

                if (duplicatedRecord == null) {
                    articleTagMapper.insert(new ArticleTag(articleID.longValue(), newTagID.longValue()));
                }

                // For each article, reassign the List<Tag>
                // Article article = articleMapper.selectById(articleID);
                // List<Tag> articleTags = article.getTags();
                // articleTags.add(newTag);
                // article.setTags(articleTags);

                // For each pair of article ID and tag ID, delete
                LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(ArticleTag::getTagId, tagID);
                wrapper.eq(ArticleTag::getArticleId, articleID);
                articleTagMapper.delete(wrapper);
            }

            deleteTagById(tagID);
        }
        return newTagID;
    }

    @Override
    public List<Tag> listHotTags(int limit) {
        return tagMapper.listHotTagsByArticleUse(limit);
    }

    @Override
    public List<TagVO> findAllDetail(String keyword) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), Tag::getTagname, keyword);
        List<Tag> tags = tagMapper.selectList(wrapper);
        List<TagVO> tagVOS = new ArrayList<>();
        for (Tag tag : tags) {

            TagVO tagVO = new TagVO();
            tagVO.setId(tag.getId());
            tagVO.setAvatar(tag.getAvatar());
            tagVO.setTagname(tag.getTagname());
            // get article counts
            int articlesCount = articleMapper.getCountArticleByTagId(tag.getId());
            tagVO.setArticles(articlesCount);
            // add
            tagVOS.add(tagVO);
        }
        return tagVOS;
    }

    @Override
    public TagVO getTagDetail(Integer tagId) {
        Tag tag = tagMapper.selectById(tagId);
        TagVO tagVO = new TagVO();
        tagVO.setId(tag.getId());
        tagVO.setAvatar(tag.getAvatar());
        tagVO.setTagname(tag.getTagname());
        // get article counts
        int articlesCount = articleMapper.getCountArticleByTagId(tag.getId());
        tagVO.setArticles(articlesCount);

        return tagVO;
    }
}
