package com.sast.woc.controller;

import com.sast.woc.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    public UserService userService;


    /**
     * 完成登录功能
     * @param userName 用户名
     * @param password 密码
     * @return 如果登录成功返回 {@code true}, 否则 {@code false}
     */
    //To be changed by using Token and JWT to refactor the code
    @PostMapping("/login")
    public Boolean login(@RequestParam(defaultValue = "") String userName, @RequestParam(defaultValue = "") String password) {
        // todo 这里需要你补全
        if (userName == "" || password == "") {
            System.out.println("username and pasword should both not empty");
            return false;
        }
        if (userService.NameIfExisted(userName)){
            if (userService.IfNamePasswordMatch(userName,password)){
                return  true;
            }else{
                System.out.println("UserName and password do not match");
            }
        }else{
            System.out.println("This username doesn't exist");
            return false;
        }

        return true;
    }
}
