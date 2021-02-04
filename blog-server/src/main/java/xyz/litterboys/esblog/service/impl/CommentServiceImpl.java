package xyz.litterboys.esblog.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xyz.litterboys.esblog.dao.CommentMapper;
import xyz.litterboys.esblog.model.view.CommentView;
import xyz.litterboys.esblog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<CommentView> getCommentList(int articleId) {
        
        List<CommentView> comments = commentMapper.getCommentList(articleId);
        Map<Integer, List<CommentView>> commentMap = comments.stream().collect(Collectors.groupingBy(CommentView::getId));
        for (CommentView comment : comments) {
            if (comment.getReplyId() != 0) {
                CommentView reply = commentMap.get(comment.getReplyId()).get(0);
                String replyStr = reply.getUsername() + "：" +
                        (reply.getComment().length() > 15 ? reply.getComment().substring(0,15) + "..." : reply.getComment());
                comment.setReplyContent(replyStr);
                comment.setComment("@" + reply.getUsername() + ": " + comment.getComment());
            }else  {
                comment.setReplyContent("");
            }
        }
        comments.sort(Comparator.comparing(CommentView::getCreateTime));
        return comments;
    }
    
}
