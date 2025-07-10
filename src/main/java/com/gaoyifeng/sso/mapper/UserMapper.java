package com.gaoyifeng.sso.mapper;

import com.gaoyifeng.sso.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(username, password, phone, email, status) " +
            "VALUES(#{username}, #{password}, #{phone}, #{email}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE user SET " +
            "username=#{username}, password=#{password}, phone=#{phone}, " +
            "email=#{email}, status=#{status} " +
            "WHERE id=#{id}")
    int update(User user);

    @Delete("DELETE FROM user WHERE id=#{id}")
    int deleteById(Long id);

    @Select("SELECT * FROM user WHERE id=#{id}")
    User selectById(Long id);

    @Select("SELECT * FROM user WHERE username=#{username}")
    User selectByUsername(String username);

    @Select("SELECT * FROM user WHERE phone=#{phone}")
    User selectByPhone(String phone);

    @Select("SELECT * FROM user WHERE email=#{email}")
    User selectByEmail(String email);

    @Select("SELECT * FROM user")
    List<User> selectAll();
}