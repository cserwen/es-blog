package xyz.litterboys.esblog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper{

    @Select("select distinct tag_name from tag_article where article_id = #{id}")
    List<String> getTagsByArticleId(int id);
}
