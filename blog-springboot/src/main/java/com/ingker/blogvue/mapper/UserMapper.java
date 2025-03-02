package com.ingker.blogvue.mapper;

import com.ingker.blogvue.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("SELECT * FROM user WHERE login = #{login}")
    public User getByLogin(String login);

    @Insert("INSERT INTO user(user_id, username, login, password) " +
            "VALUES(#{userId}, #{username}, #{login}, #{password})")
    public void add(User user);
}
