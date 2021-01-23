package xyz.litterboys.esblog.service;

import xyz.litterboys.esblog.model.Article;
import xyz.litterboys.esblog.model.ArticleCard;
import xyz.litterboys.esblog.model.view.ArticleListView;

import java.util.HashMap;
import java.util.List;

public interface ArticleService {

    HashMap<String, Integer> createTopic(Article article);

    Article getArticleById(int id);

    HashMap<String, Boolean> deleteArticleById(String articleId);

    ArticleListView getArticleList(long next, long size);
}
