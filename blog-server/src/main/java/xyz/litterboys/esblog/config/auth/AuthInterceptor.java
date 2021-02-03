package xyz.litterboys.esblog.config.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.litterboys.esblog.dao.UserMapper;
import xyz.litterboys.esblog.exception.NormalException;
import xyz.litterboys.esblog.exception.ParamException;
import xyz.litterboys.esblog.model.User;
import xyz.litterboys.esblog.util.TokenUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Resource
    private UserMapper userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws NormalException {
        String token = request.getHeader("token");
        String username = TokenUtils.verify(token);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        User user = userDao.selectOne(userQueryWrapper);
        if (user == null){
            throw new ParamException("用户不存在");
        }

        if (user.getToken() != null && token.equals(user.getToken())){
            logger.info("token={} is ok", token);
            return true;
        }else {
            logger.info("token={} is error", token);
            return false;
        }
    }
}
