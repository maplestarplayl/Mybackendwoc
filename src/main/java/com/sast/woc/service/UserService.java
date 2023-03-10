package com.sast.woc.service;

import com.sast.woc.entity.User;

/**
 * @Author xun
 * @create 2023/1/3 16:35
 */
public interface UserService {
    // 这是一个接口的方法，这个方法的具体实现在 UserServiceImpl 里面，你也可以点这个方法左边的小按键进入。
    User sample(String value);
    User findByName (String name);
    void AddUser(User user);
    void deleteByName (String name);
    Boolean NameIfExisted(String name);
    Boolean IfNamePasswordMatch(String name,String password);
    void SaveToken(String token,String name);
}
