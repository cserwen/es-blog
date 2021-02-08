package xyz.litterboys.esblog.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import xyz.litterboys.esblog.model.CommentAddModel;
import xyz.litterboys.esblog.model.view.CommentView;
import xyz.litterboys.esblog.service.CommentService;

@RestController
@RequestMapping("comment")
public class CommentController {

    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private CommentService commentService;
    
    @GetMapping("getCommentsByArticleId")
    public Object getCommentList(@RequestParam("articleId") Integer articleId){
        return commentService.getCommentList(articleId);
    }

    @PostMapping("/add")
    public Object addComment(@RequestBody CommentAddModel comment) {
        logger.info("#commentView={}", comment);
        return commentService.addComment(comment);
    }
}
