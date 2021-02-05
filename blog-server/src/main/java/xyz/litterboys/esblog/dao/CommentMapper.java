package xyz.litterboys.esblog.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import xyz.litterboys.esblog.model.Comment;
import xyz.litterboys.esblog.model.view.CommentView;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}