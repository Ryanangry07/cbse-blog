package com.loloao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loloao.entity.Tag;
import com.loloao.vo.TagVO;

import java.util.List;


/**
 * (Tag)表服务接口
 *
 * @author makejava
 * @since 2023-12-09 19:10:18
 */
public interface TagService extends IService<Tag> {

    List<Tag> findAll();

    Tag getTagById(Integer id);

    Integer saveTag(Tag tag);

    Integer updateTag(Tag tag);

    void deleteTagById(Integer id);

    List<Tag> listHotTags(int limit);

    List<TagVO> findAllDetail();

    TagVO getTagDetail(Integer tagId);

}
