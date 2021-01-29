package xyz.litterboys.esblog.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import xyz.litterboys.esblog.exception.ParamException;
import xyz.litterboys.esblog.model.User;
import xyz.litterboys.esblog.service.UserService;

import javax.annotation.Resource;
import java.util.Map;

@RestController()
@RequestMapping("user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @PostMapping("login")
    public Object login(@RequestBody User user){
        logger.info("login #user={}", user);
        return userService.userLogin(user);
    }

    @GetMapping("logout")
    public Object logout(@RequestParam("username") String username){
        logger.info("logout: user={}", username);
        return userService.userLogout(username);
    }

    @PostMapping("auth")
    public Object auth(@RequestBody Map<String, String> token) {
        logger.info("auth #token={}", token);
        if (token.containsKey("token") && StringUtils.isNotEmpty(token.get("token"))){
            return userService.userAuth(token.get("token"));
        } else {
            throw new ParamException("未登录");
        }
    }
}
