package xyz.litterboys.esblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.litterboys.esblog.model.Article;

public interface ArticleDao extends BaseMapper<Article> {

    @Override
    int insert(Article entity);
}
