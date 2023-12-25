package com.loloao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loloao.entity.Comment;

import java.util.List;


/**
 * (Comment)表服务接口
 *
 * @author makejava
 * @since 2023-12-09 19:20:52
 */
public interface CommentService extends IService<Comment> {

    List<Comment> findAll();

    Comment getCommentById(Integer id);

    Integer saveComment(Comment comment);

    void deleteCommentById(Integer id);

    List<Comment> listCommentsByArticle(Long articleId);

    Comment saveCommentAndChangeCounts(Comment comment);

    void deleteCommentByIdAndChangeCounts(Integer id);

}
