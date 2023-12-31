package com.loloao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loloao.entity.Category;
import com.loloao.vo.CategoryVO;

import java.util.List;

/**
 * (Category)表服务接口
 *
 * @author makejava
 * @since 2023-12-09 19:19:58
 */
public interface CategoryService extends IService<Category> {

    List<Category> findAll();

    Category getCategoryById(Integer id);

    Integer getCategoryIDByName(String name);

    Integer saveCategory(Category category);

    Integer updateCategory(Category category);

    void deleteCategoryById(String id);

    Integer mergeCategory(String[] oldCategories, String newCategoryName);

    List<CategoryVO> findAllDetail(String keyword);

    CategoryVO getCategoryDetail(Integer categoryId);

}
