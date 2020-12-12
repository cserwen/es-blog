package xyz.litterboys.esblog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.litterboys.esblog.model.User;
import xyz.litterboys.esblog.service.UserService;

import javax.annotation.Resource;

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
}
