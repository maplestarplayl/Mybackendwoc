package com.sast.woc.service.impl;

import com.sast.woc.entity.User;
import com.sast.woc.mapper.UserMapper;
import com.sast.woc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @Author xun
 * @create 2023/1/3 16:35
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserMapper userMapper;



    /**
     * 这是sample方法的具体实现
     * @param value value
     * @return User
     */
    @Override
    public User sample(String value) {
        return userMapper.sample(value);
    }

    @Override
    public User findByName(String name)
    {
        User user =userMapper.findByName(name);
        if (user == null)
        {
            throw new RuntimeException("This user do not exist");
        }
        return user;
    }
    @Override
    public void AddUser(User user)
    {
        if (user.getName().equals("") || user.getPassword().equals(""))
        {
            throw new RuntimeException("Username and password are both required");
        }
        if (userMapper.NameIfExisted(user.getName()) > 0)
        {
            throw new RuntimeException("user with the name existed");
        }
        userMapper.AddUser(user);
    }
    @Override
    public void deleteByName (String name)
    {
        if (userMapper.findByName(name) == null)
        {
            throw new RuntimeException("This User doesn't exist");
        }
        //userMapper.findByName(name);
        userMapper.deleteByName(name);
    }
    @Override
    public Boolean NameIfExisted(String name){
        return userMapper.NameIfExisted(name) > 0;
    }
    @Override
    public Boolean IfNamePasswordMatch(String name,String password){
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("password", password);
        return userMapper.IfNamePasswordMatch(params) > 0;
    }
    @Override
    public void SaveToken(String token,String name){
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("name",name);
        userMapper.SaveTokenByName(map);}

    @Override
    public void UserChangeInfo(String oldname,String newname,String newpassword,String newemail)
    {
        Map<String,String> map = new HashMap<>();
        map.put("oldname",oldname);
        map.put("newname",newname);
        map.put("newpassword",newpassword);
        map.put("newemail",newemail);
        userMapper.UserChangeInfo(map);
    }
    @Override
    public List<User> findAllUsers()
    {
        return userMapper.findAllUsers();
    }

    @Override
    public User returnUserByToken(String token)
    {
        //return userMapper.returnUserByToken(token);
        User user = userMapper.returnUserByToken(token);
        return user;
    }
}
