package com.example.demo.web.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by LXX on 2018/11/28.
 */
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/mysql1/add")
    public void addToMysql1() throws Exception{
        userService.addToMysql1();
    }

    @RequestMapping("/mysql1/get")
    public UserEntity getFromMysql1(){
        return userService.getFromMysql1();
    }

    @RequestMapping("/mysql2/add")
    public void addToMysql2(){
        userService.addToMysql2();
    }

    @RequestMapping("/mysql2/get")
    public UserEntity getFromMysql2(){
        return userService.getFromMysql2();
    }

    @RequestMapping("/mapper/mysql1/add")
    public void addToMysql1WithMapper(){
        userService.addToMysql1WithMapper();
    }

    @RequestMapping("/oracle/add")
    public void addToOracle() throws Exception{
        userService.addToOracle();
    }

    @RequestMapping("/oracle/get")
    public UserEntity getFromOracle(){
        return userService.getFromOracle();
    }

    ///////////////////事务管理测试//////////////////////////////////////

    /**
     * 自动提交、回滚事务
     * 如果不使用事务该记录会被删除
     */
    @RequestMapping("/transactionTest")
    public void transactionTest(){
        userService.transactionTest();
    }

    /**
     * 手动提交、回滚事务
     */
    @RequestMapping("/transactionTest2")
    public void transactionTest2(){
        userService.transactionTest2();
    }
}
