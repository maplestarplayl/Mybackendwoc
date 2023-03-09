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
    Boolean NameIfExisted(String name);
    Boolean IfNamePasswordMatch(Map<String,Object> params);


}
