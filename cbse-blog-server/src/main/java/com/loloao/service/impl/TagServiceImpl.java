package com.loloao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.Tag;
import com.loloao.mapper.TagMapper;
import com.loloao.service.TagService;
import com.loloao.vo.TagVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 19:10:18
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

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
        return null;
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
        return null;
    }

    @Override
    public List<TagVO> findAllDetail() {
        return null;
    }

    @Override
    public TagVO getTagDetail(Integer tagId) {
        return null;
    }
}
