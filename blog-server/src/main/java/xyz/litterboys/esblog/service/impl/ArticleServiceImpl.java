package xyz.litterboys.esblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.litterboys.esblog.dao.ArticleDao;
import xyz.litterboys.esblog.exception.NormalException;
import xyz.litterboys.esblog.exception.ParamException;
import xyz.litterboys.esblog.model.Article;
import xyz.litterboys.esblog.service.ArticleService;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDao articleDao;

    @Override
    public HashMap<String, Integer> createTopic(Article article) {
        authParams(article);
        HashMap<String, Integer> res = new HashMap<>();
        if (articleDao.insert(article) > 0){
            res.put("articleId", article.getId());
            return res;
        }else {
            throw new NormalException("发布失败");
        }
    }

    private void authParams(Article article){
        if (StringUtils.isEmpty(article.getTitle())){
            throw new ParamException("文章标题为空");
        }

        if (StringUtils.isEmpty(article.getContent())){
            throw new ParamException("文章内容为空");
        }
    }

    @Override
    public Article getArticleById() {
        return new Article();
    }

    @Override
    public HashMap<String, Boolean> deleteArticleById(String articleId) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("id", articleId).eq("is_deleted", false);
        Article article = articleDao.selectOne(articleQueryWrapper);
        if (article == null){
            throw new NormalException("article is not existed!");
        }
        article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        article.setDeleted(true);
        HashMap<String, Boolean> res = new HashMap<>(1);
        res.put("result", articleDao.updateById(article) > 0);
        return res;
    }
}
