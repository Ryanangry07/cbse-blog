package com.loloao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.Comment;
import com.loloao.mapper.CommentMapper;
import com.loloao.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 19:20:52
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public Comment getCommentById(Integer id) {
        return null;
    }

    @Override
    public Integer saveComment(Comment comment) {
        return null;
    }

    @Override
    public void deleteCommentById(Integer id) {

    }

    @Override
    public List<Comment> listCommentsByArticle(Integer id) {
        return null;
    }

    @Override
    public Comment saveCommentAndChangeCounts(Comment comment) {
        return null;
    }

    @Override
    public void deleteCommentByIdAndChangeCounts(Integer id) {

    }
}
