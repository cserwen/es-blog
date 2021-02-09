package xyz.litterboys.esblog.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import xyz.litterboys.esblog.dao.CommentMapper;
import xyz.litterboys.esblog.exception.NormalException;
import xyz.litterboys.esblog.model.Comment;
import xyz.litterboys.esblog.model.CommentAddModel;
import xyz.litterboys.esblog.model.view.CommentView;
import xyz.litterboys.esblog.service.CommentService;
import xyz.litterboys.esblog.service.EmailService;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private EmailService emailService;

    @Override
    public List<CommentView> getCommentList(int articleId) {

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("article_id", articleId).eq("is_deleted", false);
        List<Comment> comments = commentMapper.selectList(commentQueryWrapper);
        Map<Integer, List<Comment>> commentMap = comments.stream().collect(Collectors.groupingBy(Comment::getId));

        List<CommentView> commentViews = new ArrayList<>();
        for (Comment comment : comments) {
            CommentView commentView = new CommentView();
            BeanUtils.copyProperties(comment, commentView);
            if (comment.getReplyId() != 0) {
                Comment reply = commentMap.get(comment.getReplyId()).get(0);
                String replyStr = reply.getUsername() + "：" + reply.getComment();
                commentView.setReplyContent(replyStr);
                commentView.setComment(comment.getComment());
            }else  {
                commentView.setReplyContent("");
            }
            commentViews.add(commentView);
        }
        commentViews.sort(Comparator.comparing(CommentView::getCreateTime));
        return commentViews;
    }

    @Override
    public Boolean addComment(CommentAddModel comment) {
        Comment comment1 = new Comment();
        BeanUtils.copyProperties(comment, comment1);
        commentMapper.insert(comment1);
        String site = "#";
        if (StringUtils.isNotEmpty(comment.getSite())) {
            site = comment.getSite();
        }
        String content = "<a href='" + site +
                "'>" + comment.getUsername() +
                "</a>在<a href='https://blog.litterboys.xyz/article/" +
                comment.getArticleId() + "'>文章</a>中说道：<br/><hr/>";

        if (comment.getReplyId() != 0) {
            Comment replayComment = commentMapper.selectById(comment.getReplyId());
            if (replayComment == null || StringUtils.isEmpty(replayComment.getEmail())){
                return true;
            }
            content += "<blockquote style=\"padding: 0 10px; color: #6a737d; border-left: .20em solid #97CAFF; margin: 10px 30px 5px\">\n" +
                    "            <p style=\"text-align: left; margin: 1px; color: #888888\">" + replayComment.getComment() + "</p>\n" +
                    "          </blockquote>";

            content += comment.getComment();
            final String mailContent = content;
            new Thread(() -> emailService.sendEmail(replayComment.getEmail(), "评论回复", mailContent)).start();
        }else {
            content += comment.getComment();
            final String mailContent = content;
            new Thread(() -> emailService.sendEmail("cserwen@163.com", "新评论", mailContent)).start();
        }
        return true;
    }
}
