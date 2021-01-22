package xyz.litterboys.esblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import xyz.litterboys.esblog.model.Article;
import xyz.litterboys.esblog.service.ArticleService;

import javax.annotation.Resource;

@RestController
@RequestMapping("article")
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Resource
    private ArticleService articleService;

    @GetMapping("{articleId}")
    public void getArticle(@PathVariable String articleId) {

    }

    @PostMapping("create")
    private Object createArticle(@RequestBody Article article) {
        logger.info("insert article #article={}", article);
        return articleService.createTopic(article);
    }

    @DeleteMapping("delete/{articleId}")
    private Object deleteArticleByID(@PathVariable("articleId") String articleId){
        logger.info("delete article #articleId={}", articleId);
        return articleService.deleteArticleById(articleId);
    }

    @GetMapping("list")
    private Object getArticleList(@RequestParam("next") long next, @RequestParam("size") long size){
        return articleService.getArticleList(next, size);
    }
}
