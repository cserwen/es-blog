package xyz.litterboys.esblog.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import xyz.litterboys.esblog.dao.CommentMapper;
import xyz.litterboys.esblog.model.Comment;
import xyz.litterboys.esblog.model.view.CommentView;
import xyz.litterboys.esblog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<CommentView> getCommentList(int articleId) {

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("article_id", articleId);
        List<Comment> comments = commentMapper.selectList(commentQueryWrapper);
        Map<Integer, List<Comment>> commentMap = comments.stream().collect(Collectors.groupingBy(Comment::getId));

        List<CommentView> commentViews = new ArrayList<>();
        for (Comment comment : comments) {
            CommentView commentView = new CommentView();
            BeanUtils.copyProperties(comment, commentView);
            if (comment.getReplyId() != 0) {
                Comment reply = commentMap.get(comment.getReplyId()).get(0);
                String replyStr = reply.getUsername() + "：" +
                        (reply.getComment().length() > 15 ? reply.getComment().substring(0,15) + "..." : reply.getComment());
                commentView.setReplyContent(replyStr);
                commentView.setComment("@" + reply.getUsername() + ": " + comment.getComment());
            }else  {
                commentView.setReplyContent("");
            }
            commentViews.add(commentView);
        }
        commentViews.sort(Comparator.comparing(CommentView::getCreateTime));
        return commentViews;
    }
    
}
