package com.loloao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.Article;
import com.loloao.entity.Category;
import com.loloao.mapper.ArticleMapper;
import com.loloao.mapper.CategoryMapper;
import com.loloao.service.CategoryService;
import com.loloao.vo.CategoryVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 19:19:58
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    public CategoryMapper categoryMapper;

    @Resource
    public ArticleMapper articleMapper;

    @Override
    public List<Category> findAll() {
        return null;
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public Integer getCategoryIDByName(String name) {
        return categoryMapper.getCategoryID(name);
    }

    @Override
    public Integer saveCategory(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public Integer updateCategory(Category category) {
        return categoryMapper.updateById(category);
    }

    @Override
    public void deleteCategoryById(String categoryID) {
        Integer newID = categoryMapper.getCategoryID("Others");
        List<Integer> articlesID = articleMapper.getArticleIdsByCategoryId(categoryID);
        for (Integer articleID : articlesID) {
            Article oldArticle = articleMapper.selectById(articleID);
            // oldArticle.setCategory(newCategory);
            oldArticle.setCategoryId(newID.longValue());
            articleMapper.updateById(oldArticle);
        }
        categoryMapper.deleteById(categoryID);
    }

    @Override
    public Integer mergeCategory(String[] oldCategories, String newCategoryName) {
        Integer row = saveCategory(new Category(null, null, newCategoryName, null));
        Integer newCategoryID = categoryMapper.getCategoryID(newCategoryName);
        Category newCategory = categoryMapper.selectById(newCategoryID);

        for (String categoryID : oldCategories) {
            List<Integer> articlesID = articleMapper.getArticleIdsByCategoryId(categoryID);
            for (Integer id : articlesID) {
                Article oldArticle = articleMapper.selectById(id);
                oldArticle.setCategory(newCategory);
                oldArticle.setCategoryId(newCategoryID.longValue());
                articleMapper.updateById(oldArticle);
            }
            categoryMapper.deleteById(categoryID);
        }
        return newCategoryID;
    }

    @Override
    public List<CategoryVO> findAllDetail(String keyword) {

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(keyword), Category::getCategoryname, keyword);
        List<Category> categories = categoryMapper.selectList(wrapper);

        List<CategoryVO> categoryVOS = new ArrayList<>();
        for (Category category : categories) {

            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setId(category.getId());
            categoryVO.setAvatar(category.getAvatar());
            categoryVO.setDescription(category.getDescription());
            categoryVO.setCategoryname(category.getCategoryname());
            // get article counts
            int articlesCount = articleMapper.getCountArticleByCategoryId(category.getId());
            categoryVO.setArticles(articlesCount);
            // add
            categoryVOS.add(categoryVO);
        }
        return categoryVOS;
    }

    @Override
    public CategoryVO getCategoryDetail(Integer categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVO categoryVO = new CategoryVO();
        categoryVO.setId(category.getId());
        categoryVO.setAvatar(category.getAvatar());
        categoryVO.setDescription(category.getDescription());
        categoryVO.setCategoryname(category.getCategoryname());

        int articlesCount = articleMapper.getCountArticleByCategoryId(category.getId());
        categoryVO.setArticles(articlesCount);
        return categoryVO;
    }
}
