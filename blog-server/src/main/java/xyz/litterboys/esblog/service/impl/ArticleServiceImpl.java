package xyz.litterboys.esblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.litterboys.esblog.dao.ArticleMapper;
import xyz.litterboys.esblog.dao.TagMapper;
import xyz.litterboys.esblog.exception.NormalException;
import xyz.litterboys.esblog.exception.ParamException;
import xyz.litterboys.esblog.model.Article;
import xyz.litterboys.esblog.model.ArticleCard;
import xyz.litterboys.esblog.model.Tag;
import xyz.litterboys.esblog.model.view.ArticleListView;
import xyz.litterboys.esblog.service.ArticleService;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private TagMapper tagMapper;

    @Override
    public HashMap<String, Integer> createTopic(Article article) {
        authParams(article);
        HashMap<String, Integer> res = new HashMap<>();
        if (articleMapper.insert(article) > 0){
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
        Article article = articleMapper.selectOne(articleQueryWrapper);
        if (article == null){
            throw new NormalException("article is not existed!");
        }
        article.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        article.setDeleted(true);
        HashMap<String, Boolean> res = new HashMap<>(1);
        res.put("result", articleMapper.updateById(article) > 0);
        return res;
    }

    @Override
    public ArticleListView getArticleList(long next, long size) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("is_deleted", false).eq("is_open", true).orderByDesc("create_time");
        Page<Article> articlePage = new Page<>(next, size);
        IPage<Article> articleIPage = articleMapper.selectPage(articlePage, articleQueryWrapper);

        List<ArticleCard> articleCards = new ArrayList<>();
        articleIPage.getRecords().forEach(article -> {
            ArticleCard articleCard = new ArticleCard();
            BeanUtils.copyProperties(article, articleCard);
            articleCard.setTags(tagMapper.getTagsByArticleId(article.getId()));
            articleCards.add(articleCard);
        });

        ArticleListView articleListView = new ArticleListView();
        articleListView.setPages(articleIPage.getPages());
        articleListView.setArticleCards(articleCards);
        articleListView.setCurrent(articleIPage.getCurrent());
        System.out.println(articleListView);
        return articleListView;
    }
}
