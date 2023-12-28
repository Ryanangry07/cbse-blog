package com.loloao.controller;



import com.loloao.common.Base;
import com.loloao.common.Result;
import com.loloao.entity.Category;
import com.loloao.enums.ResultCode;
import com.loloao.service.CategoryService;
import com.loloao.utils.UserUtils;
import com.loloao.vo.CategoryVO;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Category)表控制层
 *
 * @author makejava
 * @since 2023-12-09 19:19:58
 */
@RestController
@RequestMapping("/categorys")
public class CategoryController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    @GetMapping
    public Result listCategorys() {
        //List<Category> categorys = categoryService.findAll();

        return Result.success(categoryService.list());
    }

    @GetMapping("detail")
    public Result listCategorysDetail(String keyword) {
        List<CategoryVO> categorys = categoryService.findAllDetail(keyword);

        return Result.success(categorys);
    }

    @GetMapping("/{id}")
    public Result getCategoryById(@PathVariable("id") Integer id) {

        Result result = new Result();

        if (null == id) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }

        Category category = categoryService.getCategoryById(id);

        result.setResultCode(ResultCode.SUCCESS);
        result.setData(category);
        return result;
    }

    @GetMapping("/detail/{id}")
    public Result getCategoryDetail(@PathVariable("id") Integer id) {

        Result result = new Result();

        if (null == id) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }

        CategoryVO category = categoryService.getCategoryDetail(id);

        result.setResultCode(ResultCode.SUCCESS);
        result.setData(category);
        return result;
    }

    @PostMapping("/create")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result saveCategory(@Validated @RequestBody Category category) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        Integer categoryId = categoryService.saveCategory(category);

        Result result = Result.success();
        result.simple().put("categoryId", categoryId);
        return result;
    }

    @PostMapping("/update")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result updateCategory(@RequestBody Category category) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }
        Result result = new Result();

        if (null == category.getId()) {
            result.setResultCode(ResultCode.USER_NOT_EXIST);
            return result;
        }

        Integer categoryId = categoryService.updateCategory(category);

        result.setResultCode(ResultCode.SUCCESS);
        result.simple().put("categoryId", categoryId);
        return result;
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result deleteCategoryById(@PathVariable("id") Integer id) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }
        Result result = new Result();

        if (null == id) {
            result.setResultCode(ResultCode.PARAM_IS_BLANK);
            return result;
        }

        categoryService.deleteCategoryById(id);

        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }
    
}

