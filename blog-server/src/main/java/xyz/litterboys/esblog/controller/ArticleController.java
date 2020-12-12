package xyz.litterboys.esblog.controller;

import org.springframework.web.bind.annotation.*;
import xyz.litterboys.esblog.model.Article;
import xyz.litterboys.esblog.service.ArticleService;

import javax.annotation.Resource;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @GetMapping("{articleId}")
    public void getArticle(@PathVariable String articleId) {

    }

    @PostMapping("create")
    private Object createArticle(Article article) {
        return articleService.createTopic(article);
    }
}
