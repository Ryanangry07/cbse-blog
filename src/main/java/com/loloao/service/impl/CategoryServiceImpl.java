package com.loloao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.Category;
import com.loloao.mapper.CategoryMapper;
import com.loloao.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * (Category)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 19:19:58
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
