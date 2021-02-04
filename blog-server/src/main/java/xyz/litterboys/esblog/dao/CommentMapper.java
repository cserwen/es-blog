package xyz.litterboys.esblog.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import xyz.litterboys.esblog.model.view.CommentView;

@Mapper
public interface CommentMapper extends BaseMapper<CommentView> {

    @Select("select comment.id, comment, article_id, parent_id, reply_id, create_time, username, site from comment, comment_user where comment.comment_user_id = comment_user.id and article_id = 17")
    List<CommentView> getCommentList();
}