package com.loloao.controller;



import com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.loloao.common.Result;
import com.loloao.entity.Comment;
import com.loloao.entity.User;
import com.loloao.enums.ResultCode;
import com.loloao.service.CommentService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Comment)表控制层
 *
 * @author makejava
 * @since 2023-12-09 19:20:52
 */
@RestController
@RequestMapping("comment")
public class CommentController {
    /**
     * 服务对象
     */
    @Resource
    private CommentService commentService;


    @GetMapping
    public Result listComments() {
        List<Comment> comments = commentService.findAll();

        return Result.success(comments);
    }

    @GetMapping("/{id}")
    public Result getCommentById(@PathVariable("id") Integer id) {

        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        Comment comment = commentService.getCommentById(id);

        r.setResultCode(ResultCode.SUCCESS);
        r.setData(comment);
        return r;
    }

    @GetMapping("/article/{id}")
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Comment.class, props = {"article", "parent"})},
            include = {@FastJsonFilter(clazz = User.class, props = {"id", "nickname", "avatar"})})
    public Result listCommentsByArticle(@PathVariable("id") Integer id) {

        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        List<Comment> comments = commentService.listCommentsByArticle(id);

        r.setResultCode(ResultCode.SUCCESS);
        r.setData(comments);
        return r;
    }


    @PostMapping("/create")
    @RequiresAuthentication
    public Result saveComment(@Validated @RequestBody Comment comment) {

        Integer commentId = commentService.saveComment(comment);

        Result r = Result.success();
        r.simple().put("commentId", commentId);
        return r;
    }


    @GetMapping("/delete/{id}")
    @RequiresAuthentication
    public Result deleteCommentById(@PathVariable("id") Integer id) {
        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        commentService.deleteCommentById(id);

        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }

    @PostMapping("/create/change")
    @FastJsonView(
            exclude = {
                    @FastJsonFilter(clazz = Comment.class, props = {"article"})},
            include = {@FastJsonFilter(clazz = User.class, props = {"id", "nickname", "avatar"})})
    @RequiresAuthentication
    public Result saveCommentAndChangeCounts(@RequestBody Comment comment) {

        Comment savedComment = commentService.saveCommentAndChangeCounts(comment);

        Result r = Result.success(savedComment);
        return r;
    }


    @GetMapping("/delete/change/{id}")
    @RequiresAuthentication
    public Result deleteCommentByIdAndChangeCounts(@PathVariable("id") Integer id) {
        Result r = new Result();

        if (null == id) {
            r.setResultCode(ResultCode.PARAM_IS_BLANK);
            return r;
        }

        commentService.deleteCommentByIdAndChangeCounts(id);

        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }
}

