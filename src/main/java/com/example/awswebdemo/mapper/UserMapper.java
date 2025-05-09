package com.example.awswebdemo.mapper;

import com.example.awswebdemo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Integer id);

    @Insert("INSERT INTO users(name, email, age) VALUES(#{name}, #{email}, #{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE users SET name = #{name}, email = #{email}, age = #{age} WHERE id = #{id}")
    void update(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(Integer id);
}
