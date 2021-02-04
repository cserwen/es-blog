package xyz.litterboys.esblog.service.impl;

import java.util.List;

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
        
        List<CommentView> comments = commentMapper.getCommentList();
        return comments;
    }
    
}
