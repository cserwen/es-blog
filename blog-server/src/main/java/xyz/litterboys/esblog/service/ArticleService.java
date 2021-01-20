package xyz.litterboys.esblog.service;

import xyz.litterboys.esblog.model.Article;

import java.util.HashMap;
import java.util.List;

public interface ArticleService {

    HashMap<String, Integer> createTopic(Article article);

    Article getArticleById();

    HashMap<String, Boolean> deleteArticleById(String articleId);

    List<Article> getArticleList();
}
