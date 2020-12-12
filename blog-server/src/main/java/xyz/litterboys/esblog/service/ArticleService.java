package xyz.litterboys.esblog.service;

import xyz.litterboys.esblog.model.Article;

public interface ArticleService {

    Boolean createTopic(Article article);

    Article getArticleById();
}
