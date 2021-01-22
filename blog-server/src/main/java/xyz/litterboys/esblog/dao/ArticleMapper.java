package xyz.litterboys.esblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.litterboys.esblog.model.Article;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}
