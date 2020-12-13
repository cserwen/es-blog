package xyz.litterboys.esblog.config.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.litterboys.esblog.dao.UserDao;
import xyz.litterboys.esblog.exception.NormalException;
import xyz.litterboys.esblog.model.User;
import xyz.litterboys.esblog.util.TokenUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Resource
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws NormalException {
        String token = request.getHeader("token");
        logger.info("user login: " + token);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("token", token);
        User user = userDao.selectOne(userQueryWrapper);
        if (user == null || !TokenUtils.verify(token)){
            throw new NormalException("User not logged in");
        }

        return true;
    }
}
