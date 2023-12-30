package com.loloao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loloao.common.Base;
import com.loloao.entity.*;
import com.loloao.mapper.ArticleMapper;
import com.loloao.mapper.CommentMapper;
import com.loloao.mapper.UserMapper;
import com.loloao.service.CommentService;
import com.loloao.service.NotificationService;
import com.loloao.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Resource
    public NotificationService notificationService;

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
                User childAuthor = userMapper.selectById(child.getAuthorId());
                child.setAuthor(childAuthor);
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
    @Transactional
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

        // update notification
        updateNotification(comment, article);

        return comment;
    }


    private void updateNotification(Comment comment, Article article){

        Notification notification = new Notification();

        User fromUser = userMapper.selectById(comment.getAuthorId());
        // notify article author
        User author = userMapper.selectById( article.getAuthorId());

        //if user is not commenting his own article, then notify article author
        if(fromUser.getId() != author.getId()){
            // from 'fromUser' to author
            notification.setFromUid(fromUser.getId());
            notification.setFromUser(fromUser.getAccount());
            // get notification author
            notification.setType(Base.NOTIFICATION_AT_ME_TYPE);
            notification.setTitle("Attention! Someone commented your article!");
            notification.setContent( "| " + fromUser.getAccount() + " | give a comment to your article: <<" + article.getTitle()
                    + ">>, the comment content is : \"" + comment.getContent() + "\", and current comment counts is (" + article.getCommentCounts() + ")");
            // notify author
            notificationService.addNotificationAndUpdateUnreadCounts(author, notification);
        }

        // if comment is reply to someone, notify toUser
        if(null != comment.getToUser()){
            // notify toUser
            User toUser = userMapper.selectById( comment.getToUser().getId());
            //if user replied his own comment, then do nothing
            if(fromUser.getId() == toUser.getId()){
                return;
            }
            //reuse notification object
            Comment parentComment = commentMapper.selectById(comment.getParentId());
            notification.setTitle("Attention! Someone reply your comment!");
            notification.setContent( "| " + fromUser.getAccount() + " | said \"" + comment.getContent() + "\" to your comment, in article: <<" + article.getTitle()
                    + ">>, your comment content is : \"" + parentComment.getContent() + "\"");
            notification.setId(null);
            notificationService.addNotificationAndUpdateUnreadCounts(toUser, notification);
        }
    }


    @Override
    public void deleteCommentByIdAndChangeCounts(Integer id) {

    }
}
