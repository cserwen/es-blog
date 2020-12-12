package xyz.litterboys.esblog.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.litterboys.esblog.model.User;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
