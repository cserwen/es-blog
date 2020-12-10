package xyz.litterboys.esblog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("article")
public class ArticleController {

    @GetMapping("{articleId}")
    public void getArticle(@PathVariable String articleId) {

    }

    @PostMapping("create")
    private void createArticle() {

    }
}
