package com.loloao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.entity.Article;
import com.loloao.entity.Comment;
import com.loloao.entity.User;
import com.loloao.mapper.ArticleMapper;
import com.loloao.mapper.CommentMapper;
import com.loloao.mapper.UserMapper;
import com.loloao.service.CommentService;
import com.loloao.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-12-09 19:20:52
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    public CommentMapper commentMapper;

    @Resource
    public UserMapper userMapper;

    @Resource
    public ArticleMapper articleMapper;

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

    private List<Comment> listCommentsByArticleOfLevel0(Long articleId){
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, articleId)
                .eq(Comment::getLevel, "0")
                .orderByDesc(Comment::getCreateDate);
        return commentMapper.selectList(wrapper);
    }

    private List<Comment> getChildrenByCommentId(Long commentId){
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getParentId, commentId)
                .orderByDesc(Comment::getCreateDate);
        return commentMapper.selectList(wrapper);
    }

    @Override
    public List<Comment> listCommentsByArticle(Long articleId) {
        // get level 0, order by create_date desc
        List<Comment> list = listCommentsByArticleOfLevel0(articleId);
        System.out.println("listCommentsByArticle=> aid:" + articleId + ", level0:" + list.size());
        // traverse each comment
        for(Comment comment: list) {
            //, fill author object
            User author = userMapper.selectById(comment.getAuthorId());
            comment.setAuthor(author);
            // search child
            List<Comment> children = getChildrenByCommentId(comment.getId());
            comment.setChildrens(children);
            // if t0_uid not null, fill toUser
            for(Comment child: children){
                if(ObjectUtils.isNotEmpty(child.getToUid())){
                    User toUser = userMapper.selectById(comment.getAuthorId());
                    child.setToUser(toUser);
                }
            }
        }


        /**
         * {
         * 	"code":0,
         * 	"data":[
         *                {
         * 			"author":{
         * 				"avatar":"/static/user/user_6.png",
         * 				"id":16,
         * 				"nickname":"root"
         *            },
         * 			"childrens":[
         *                {
         * 					"author":{
         * 						"avatar":"/static/user/user_6.png",
         * 						"id":16,
         * 						"nickname":"root"
         *                    },
         * 					"childrens":[],
         * 					"content":"really????",
         * 					"createDate":"2023.12.23 07:33",
         * 					"id":2,
         * 					"level":"1"
         *                },
         *                {
         * 					"author":{
         * 						"avatar":"/static/user/user_6.png",
         * 						"id":16,
         * 						"nickname":"root"
         *                    },
         * 					"childrens":[],
         * 					"content":"MAYBE YES",
         * 					"createDate":"2023.12.23 07:38",
         * 					"id":3,
         * 					"level":"2",
         * 					"toUser":{
         * 						"avatar":"/static/user/user_6.png",
         * 						"id":16,
         * 						"nickname":"root"
         *                    }
         *                }
         * 			],
         * 			"content":"You are my hero ok?",
         * 			"createDate":"2023.12.23 07:33",
         * 			"id":1,
         * 			"level":"0"
         *        }
         * 	],
         * 	"msg":"成功"
         * }
         */

        return list;
    }

    @Override
    public Comment saveCommentAndChangeCounts(Comment comment) {
        // article count + 1
        Article article = articleMapper.selectById(comment.getArticle().getId());
        article.setCommentCounts(article.getCommentCounts() + 1);
        articleMapper.updateById(article);

        comment.setAuthor(UserUtils.getCurrentUser());
        comment.setAuthorId(comment.getAuthor().getId());
        comment.setArticleId(article.getId());
        comment.setCreateDate(new Date());

        //设置level
        if(null == comment.getParent()){
            comment.setLevel("0");
        }else{
            if(null == comment.getToUser()){
                comment.setLevel("1");
            }else{
                comment.setLevel("2");
            }
        }

        
        if(ObjectUtils.isNotEmpty(comment.getToUser())){
            comment.setToUid(comment.getToUser().getId());
        }

        if(ObjectUtils.isNotEmpty(comment.getParent())){
            comment.setParentId(comment.getParent().getId());
        }

        // insert method, automatically fill the id
        commentMapper.insert(comment);

        return comment;
    }

    @Override
    public void deleteCommentByIdAndChangeCounts(Integer id) {

    }
}
