package com.sast.woc.controller;

import com.sast.woc.entity.User;
import com.sast.woc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author xun
 * @create 2023/1/3 17:13
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    //请仿照 User 补充 Admin 的三层架构并完成接口
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;}
    /**
     * 根据用户名删除用户
     * @param userName 用户名
     * @return 删除成功返回 success
     */
    @RequestMapping("/del_user")
    public ResultData<String> delUser(String userName) {
        // todo 补全代码
        userService.deleteByName(userName);
        return ResultData.success("success");
    }

    /**
     * 根据用户名查找用户
     * @param userName 用户名
     * @return User
     */
    @GetMapping("/find_user_info")
    public ResultData<User> findUser(String userName) {
        // todo 补全代码，你需要去掉下面的 null
        return ResultData.success(userService.findByName(userName));
    }

    @GetMapping("show_all")
    public ResultData<List<User>> getAllUsers() {
        return ResultData.success(userService.findAllUsers());
    }
}
