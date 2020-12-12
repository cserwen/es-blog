package xyz.litterboys.esblog.service.impl;

import org.springframework.stereotype.Service;
import xyz.litterboys.esblog.model.Article;
import xyz.litterboys.esblog.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Override
    public Boolean createTopic(Article article) {
        return null;
    }

    @Override
    public Article getArticleById() {
        return new Article();
    }
}
