package com.loloao.controller;

import com.loloao.common.Base;
import com.loloao.common.Result;
import com.loloao.entity.Tag;
import com.loloao.enums.ResultCode;
import com.loloao.service.TagService;
import com.loloao.utils.UserUtils;
import com.loloao.vo.TagVO;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Tag)表控制层
 *
 * @author makejava
 * @since 2023-12-09 19:10:18
 */
@RestController
@RequestMapping("tags")
public class TagController {
    /**
     * 服务对象
     */
    @Resource
    private TagService tagService;


    @GetMapping
    public Result listTags() {
        //List<Tag> tags = tagService.findAll();
        return Result.success(tagService.list());
    }

    @GetMapping("detail")
    public Result listCategorysDetail() {
        List<TagVO> categorys = tagService.findAllDetail();

        return Result.success(categorys);
    }

    @GetMapping("/hot")
    public Result listHotTags() {
        int limit = 6;
        List<Tag> tags = tagService.listHotTags(limit);

        return Result.success(tags);
    }

    @GetMapping("/{id}")
    public Result getTagById(@PathVariable("id") Integer id) {

        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        Tag tag = tagService.getTagById(id);

        r.setResultCode(ResultCode.SUCCESS);
        r.setData(tag);
        return r;
    }

    @GetMapping("/detail/{id}")
    public Result getTagDetail(@PathVariable("id") Integer id) {

        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        TagVO tag = tagService.getTagDetail(id);

        r.setResultCode(ResultCode.SUCCESS);
        r.setData(tag);
        return r;
    }

    @PostMapping("/create")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result saveTag(@Validated @RequestBody Tag tag) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }

        Integer tagId = tagService.saveTag(tag);

        Result r = Result.success();
        r.simple().put("tagId", tagId);
        return r;
    }

    @PostMapping("/update")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result updateTag(@RequestBody Tag tag) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }
        Result r = new Result();

        if (null == tag.getId()) {
            r.setResultCode(ResultCode.USER_NOT_EXIST);
            return r;
        }

        Integer tagId = tagService.updateTag(tag);

        r.setResultCode(ResultCode.SUCCESS);
        r.simple().put("tagId", tagId);
        return r;
    }

    @GetMapping("/delete/{id}")
    @RequiresRoles(Base.ROLE_ADMIN)
    public Result deleteTagById(@PathVariable("id") Integer id) {
        if(UserUtils.getCurrentUser() == null){
            return Result.error(ResultCode.USER_NOT_LOGGED_IN);
        }
        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        tagService.deleteTagById(id);

        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }
}

