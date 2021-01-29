package xyz.litterboys.esblog.config.restful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import xyz.litterboys.esblog.exception.ParamException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ControllerAdvice implements ResponseBodyAdvice<Object> {

    private static Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof ResultUtil) {
            return o;
        } else {
            return ResultUtil.success(o);
        }
    }

    @ExceptionHandler(value = ParamException.class)
    public ResultUtil handleParamException(HttpServletRequest request, HttpServletResponse response, final ParamException e) {
        logger.error(e.getMessage() + ": " + request.getRequestURI());
        return new ResultUtil(ResponseCode.PARAM_ERROR.getCode(), ResponseCode.PARAM_ERROR.getMsg(), e.getMessage());
    }

    @ExceptionHandler
    public ResultUtil handleException(HttpServletRequest request, HttpServletResponse response, final Exception e) {
        logger.error(e.getMessage(), e);
        return new ResultUtil(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg(), e.getMessage());
    }
}
