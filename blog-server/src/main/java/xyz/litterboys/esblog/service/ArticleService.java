package xyz.litterboys.esblog.service;

import org.springframework.web.multipart.MultipartFile;
import xyz.litterboys.esblog.config.restful.ResultUtil;
import xyz.litterboys.esblog.model.Article;
import xyz.litterboys.esblog.model.view.ArticleListView;
import xyz.litterboys.esblog.model.view.ArticleView;

import java.util.HashMap;

public interface ArticleService {

    HashMap<String, Integer> createArticle(Article article);

    ArticleView getArticleById(int id);

    HashMap<String, Boolean> deleteArticleById(String articleId);

    ArticleListView getArticleList(long next, long size);

    ResultUtil uploadPicture(MultipartFile picture);

}
