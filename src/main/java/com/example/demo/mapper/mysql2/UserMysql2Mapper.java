package com.example.demo.mapper.mysql2;

import com.example.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by LXX on 2018/11/28.
 */
@Mapper
public interface UserMysql2Mapper {

    List<UserEntity> getAll();

    UserEntity getOne(int id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(int id);

}
