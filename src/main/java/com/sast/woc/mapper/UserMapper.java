package com.sast.woc.mapper;

import com.sast.woc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.HashMap;


/**
 * @Author xun
 * @create 2022/12/26 14:47
 */
@Mapper
@Repository
public interface UserMapper {
    // 示例，可以去resources->mapper中查看UserMapper.xml文件的内容。
    User sample(@Param("value") String value);

    User findByName (String name);
    void deleteByName (String name);
    void AddUser(User user);
    int NameIfExisted(String name);
    int IfNamePasswordMatch(Map<String,Object> params);

    void SaveTokenByName(Map<String,String> map);
    void UserChangeInfo(Map<String,String> map);



}
