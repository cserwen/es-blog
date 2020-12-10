package xyz.litterboys.esblog.auth;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        String token = request.getHeader("token");
        logger.info("token: "+ token);

        if (StringUtils.isEmpty(token)) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json; charset=utf-8");
            String jsonStr = "{\"code\":-2,\"msg\":\"用户未登录！\"}";
            response.getWriter().write(jsonStr);
            return false;
        }

        return true;
    }
}
