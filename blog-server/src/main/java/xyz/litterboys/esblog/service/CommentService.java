package xyz.litterboys.esblog.service;

import java.util.List;

import xyz.litterboys.esblog.model.CommentAddModel;
import xyz.litterboys.esblog.model.view.CommentView;

public interface CommentService {
    
    List<CommentView> getCommentList(int articleId);

    Boolean addComment(CommentAddModel comment);
}
