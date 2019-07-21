package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by LXX on 2018/11/28.
 */
@Data
@NoArgsConstructor
public class UserEntity implements Serializable{

    private int id;

    private String userName;

    private String userNo;

    private String password;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userNo='" + userNo + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
