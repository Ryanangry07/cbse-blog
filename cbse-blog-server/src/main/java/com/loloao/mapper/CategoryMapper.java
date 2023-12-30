package com.loloao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loloao.entity.Category;

/**
 * (Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-09 19:19:58
 */
public interface CategoryMapper extends BaseMapper<Category> {
    @Select("SELECT c.id FROM me_category c WHERE c.categoryname = #{categoryname} ORDER BY id DESC LIMIT 1")
    Integer getCategoryID(String categoryname);
}
