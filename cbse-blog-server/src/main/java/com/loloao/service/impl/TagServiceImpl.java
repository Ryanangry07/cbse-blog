package com.loloao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.Category;
import com.loloao.entity.Tag;
import com.loloao.mapper.ArticleMapper;
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

    @Override
    public List<Tag> findAll() {
        return null;
    }

    @Override
    public Tag getTagById(Integer id) {
        return null;
    }

    @Override
    public Integer saveTag(Tag tag) {
        return tagMapper.insert(tag);
    }

    @Override
    public Integer updateTag(Tag tag) {
        return null;
    }

    @Override
    public void deleteTagById(Integer id) {

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
        for(Tag tag : tags){

            TagVO tagVO = new TagVO();
            tagVO.setId(tag.getId());
            tagVO.setAvatar(tag.getAvatar());
            tagVO.setTagname(tag.getTagname());
            //get article counts
            int articlesCount = articleMapper.getCountArticleByTagId(tag.getId());
            tagVO.setArticles(articlesCount);
            //add
            tagVOS.add(tagVO);
        }
        return tagVOS;
    }

    @Override
    public TagVO getTagDetail(Integer tagId) {
        return null;
    }
}
