package xyz.litterboys.esblog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.litterboys.esblog.model.Article;
import xyz.litterboys.esblog.service.ArticleService;

import javax.annotation.Resource;

@RestController
@RequestMapping("article")
public class ArticleController {

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Resource
    private ArticleService articleService;

    @GetMapping("getArticleById")
    public Object getArticle(@RequestParam("id") Integer id) {
        return articleService.getArticleById(id);
    }

    @PostMapping("create")
    private Object createArticle(@RequestBody Article article) {
        logger.info("insert article #article={}", article);
        return articleService.createArticle(article);
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

    @PostMapping("uploadPicture")
    public Object uploadPicture(@RequestBody MultipartFile picture){
        logger.info("添加图片:" + picture);
        return articleService.uploadPicture(picture);
    }
}
