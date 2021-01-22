package xyz.litterboys.esblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.litterboys.esblog.dao.UserMapper;
import xyz.litterboys.esblog.exception.ParamException;
import xyz.litterboys.esblog.model.User;
import xyz.litterboys.esblog.service.UserService;
import xyz.litterboys.esblog.util.TokenUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userDao;

    @Override
    public HashMap<String, String> userLogin(User user) {
        if (user == null){
            throw new ParamException("参数为空");
        }
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPasswd())){
            throw new ParamException("请输入账户或密码");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User userInDB = userDao.selectOne(queryWrapper);
        if (userInDB == null){
            throw new ParamException("用户不存在");
        }
        if (userInDB.getPasswd().equals(user.getPasswd())){
            String token = generateToken(userInDB);
            HashMap<String, String> res = new HashMap<>(1);
            res.put("token", token);
            return res;
        }else {
            throw new ParamException("密码错误");
        }
    }

    private String generateToken(User user){
        String token = TokenUtils.getToken(user.getUsername());
        user.setToken(token);
        user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
        userDao.updateById(user);
        return token;
    }

    @Override
    public Boolean userLogout(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User user = userDao.selectOne(userQueryWrapper);
        user.setToken("");
        return userDao.updateById(user) > 0;
    }
}
