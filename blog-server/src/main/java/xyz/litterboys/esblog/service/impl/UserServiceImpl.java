package xyz.litterboys.esblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import xyz.litterboys.esblog.dao.UserMapper;
import xyz.litterboys.esblog.exception.ParamException;
import xyz.litterboys.esblog.model.User;
import xyz.litterboys.esblog.service.UserService;
import xyz.litterboys.esblog.util.TokenUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userDao;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        String md5Passwd = DigestUtils.md5DigestAsHex(userInDB.getPasswd().getBytes(StandardCharsets.UTF_8));
        if (md5Passwd.equals(user.getPasswd())){
            String token = generateToken(userInDB);
            HashMap<String, String> res = new HashMap<>(1);
            res.put("token", token);
            userInDB.setToken(token);
            userDao.updateById(userInDB);
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

    @Override
    public Boolean userAuth(String token) {
        String username = TokenUtils.verify(token);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User userInDB = userDao.selectOne(queryWrapper);
        if (userInDB == null){
            throw new ParamException("用户不存在");
        }
        if (userInDB.getToken() != null && token.equals(userInDB.getToken())){
            logger.info("token={} is ok", token);
            return true;
        }else {
            logger.info("token={} is error", token);
            return false;
        }
    }
}
