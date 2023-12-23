package com.loloao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.Category;
import com.loloao.mapper.CategoryMapper;
import com.loloao.service.CategoryService;
import com.loloao.vo.CategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 19:19:58
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<Category> findAll() {
        return null;
    }

    @Override
    public Category getCategoryById(Integer id) {
        return null;
    }

    @Override
    public Integer saveCategory(Category category) {
        return null;
    }

    @Override
    public Integer updateCategory(Category category) {
        return null;
    }

    @Override
    public void deleteCategoryById(Integer id) {

    }

    @Override
    public List<CategoryVO> findAllDetail() {
        return null;
    }

    @Override
    public CategoryVO getCategoryDetail(Integer categoryId) {
        return null;
    }
}
