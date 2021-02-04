package xyz.litterboys.esblog.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xyz.litterboys.esblog.service.CommentService;

@RestController
@RequestMapping("comment")
public class CommentController {

    @Resource
    private CommentService commentService;
    
    @GetMapping("getCommentsByArticleId")
    public Object getCommentList(@RequestParam("articleId") Integer articleId){
        
        return commentService.getCommentList(articleId);
        
    }
}
