package xyz.litterboys.esblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.litterboys.esblog.config.ConfigKey;
import xyz.litterboys.esblog.config.restful.ResultUtil;
import xyz.litterboys.esblog.dao.ArticleMapper;
import xyz.litterboys.esblog.dao.TagMapper;
import xyz.litterboys.esblog.exception.NormalException;
import xyz.litterboys.esblog.exception.ParamException;
import xyz.litterboys.esblog.model.Article;
import xyz.litterboys.esblog.model.ArticleCard;
import xyz.litterboys.esblog.model.view.ArticleListView;
import xyz.litterboys.esblog.model.view.ArticleView;
import xyz.litterboys.esblog.service.ArticleService;

import javax.annotation.Resource;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ConfigKey configKey;

    @Override
    public HashMap<String, Integer> createArticle(Article article) {
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
    public ArticleView getArticleById(int id) {
        Article article = articleMapper.selectById(id);
        if (article == null || article.getDeleted() || !article.getOpen()){
            throw new NormalException("文章不存在");
        }

        QueryWrapper<Article> lastWrapper = new QueryWrapper<>();
        lastWrapper.lt("id", id).eq("is_deleted", false).eq("is_open", true).orderByDesc("id")
                .last("limit 1");
        Article last = articleMapper.selectOne(lastWrapper);

        QueryWrapper<Article> nextWrapper = new QueryWrapper<>();
        nextWrapper.gt("id", id).eq("is_deleted", false).eq("is_open", true).orderByAsc("id")
                .last("limit 1");
        Article next = articleMapper.selectOne(nextWrapper);

        ArticleView articleView = new ArticleView();
        BeanUtils.copyProperties(article, articleView);

        if (last != null) {
            articleView.setHasLast(true);
            articleView.setLastId(last.getId());
            articleView.setLastName(last.getTitle());
        }

        if (next != null) {
            articleView.setHasNext(true);
            articleView.setNextId(next.getId());
            articleView.setNextName(next.getTitle());
        }
        return articleView;
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
        return articleListView;
    }

    @Override
    public ResultUtil uploadPicture(MultipartFile picture) {
        if (picture == null){
            throw new NormalException("请选择图片");
        }

        String picSaveService = configKey.getServiceType();

        String picUrl = null;
        try {
            switch (picSaveService){
                case "gitHub":
                    //TODO upload picture to github
                case "gitee":
                default:
                    picUrl = savaPicture2Gitee(picture);

            }
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
        return ResultUtil.success(picUrl);
    }

    private String savaPicture2Gitee(MultipartFile picture) throws IOException, InterruptedException {

        byte[] bytes = picture.getBytes();
        String base64 = Base64.getEncoder().encodeToString(bytes);
        String owner = configKey.getGiteeOwner();
        String repo = configKey.getGiteeRepo();
        String path = "img/" + new Date(System.currentTimeMillis()).getTime() + "." + picture.getContentType().split("/")[1];
        String httpUrl = "https://gitee.com/api/v5/repos/" + owner + "/" + repo + "/contents/" + path;
        String  accessToken = configKey.getGiteeAccessToken();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("access_token", accessToken);
        requestBody.put("owner", owner);
        requestBody.put("repo", repo);
        requestBody.put("path", path);
        requestBody.put("content", base64);
        requestBody.put("message", "upload");
        String requestStr = new ObjectMapper().writeValueAsString(requestBody);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(httpUrl))
                .POST(HttpRequest.BodyPublishers.ofString(requestStr))
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 201 || response.statusCode() == 400){
            return "https://gitee.com/" + owner + "/" + repo +"/raw/master/"+path;
        }else {
            return null;
        }
    }
}
