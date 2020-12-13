package xyz.litterboys.esblog.service;

import xyz.litterboys.esblog.model.User;

import java.util.HashMap;


public interface UserService {

    HashMap<String, String> userLogin(User user);

    Boolean userLogout(String username);
}
