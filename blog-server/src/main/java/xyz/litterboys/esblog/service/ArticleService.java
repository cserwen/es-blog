package xyz.litterboys.esblog.service;

import xyz.litterboys.esblog.model.Article;

import java.util.HashMap;

public interface ArticleService {

    HashMap<String, Integer> createTopic(Article article);

    Article getArticleById();

    HashMap<String, Boolean> deleteArticleById(String articleId);
}
