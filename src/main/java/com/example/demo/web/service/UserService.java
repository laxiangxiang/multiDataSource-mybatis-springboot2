package com.example.demo.web.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.mysql1.UserMysql1Mapper;
import com.example.demo.mapper.mysql2.UserMysql2Mapper;
import com.example.demo.mapper.oracle.UserOracleMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class UserService {

    @Autowired
    @Qualifier(value = "mysql1SqlSessionFactory")
    private SqlSessionFactory mysql1SqlSessionFactory;

    @Autowired
    @Qualifier(value = "mysql2SqlSessionFactory")
    private SqlSessionFactory mysql2SqlSessionFactory;

    @Autowired
    private UserOracleMapper userOracleMapper;

    @Autowired
    private UserMysql1Mapper userMysql1Mapper;

    @Autowired
    private UserMysql2Mapper userMysql2Mapper;

    @Transactional(transactionManager = "mysql1TransactionManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void addToMysql1() throws Exception{
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("123456");
        userEntity.setUserName("lxx");
        userEntity.setUserNo("001");
//        SqlSession sqlSession = mysql1SqlSessionFactory.openSession();
//        UserMysql1Mapper userMysql1Mapper = sqlSession.getMapper(UserMysql1Mapper.class);
//        userMysql1Mapper.insert(userEntity);
//        sqlSession.commit();
//        sqlSession.close();
        userMysql1Mapper.insert(userEntity);
        throw new Exception("error");
    }

    public UserEntity getFromMysql1(){
        SqlSession sqlSession = mysql1SqlSessionFactory.openSession();
        UserMysql1Mapper userMysql1Mapper = sqlSession.getMapper(UserMysql1Mapper.class);
        UserEntity userEntity = userMysql1Mapper.getOne(1);
        sqlSession.close();
        return userEntity;
    }

    public void addToMysql2(){
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("123456");
        userEntity.setUserName("lxx");
        userEntity.setUserNo("001");
//        SqlSession sqlSession = mysql2SqlSessionFactory.openSession();
//        UserMysql2Mapper userMysql2Mapper = sqlSession.getMapper(UserMysql2Mapper.class);
//        userMysql2Mapper.insert(userEntity);
//        sqlSession.commit();
//        sqlSession.close();
        userMysql2Mapper.insert(userEntity);
    }

    public UserEntity getFromMysql2(){
        SqlSession sqlSession = mysql2SqlSessionFactory.openSession();
        UserMysql2Mapper userMysql2Mapper = sqlSession.getMapper(UserMysql2Mapper.class);
        UserEntity userEntity = userMysql2Mapper.getOne(7);
        sqlSession.close();
        return userEntity;
    }

    public void addToMysql1WithMapper(){
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("123456");
        userEntity.setUserName("lxx-m1");
        userEntity.setUserNo("001");
        userMysql1Mapper.insert(userEntity);
    }

    @Transactional(transactionManager = "oracleTransactionManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void addToOracle() throws Exception{
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("123456");
        userEntity.setUserName("lxx");
        userEntity.setUserNo("001");
        userOracleMapper.insert(userEntity);
        throw new Exception("error");
    }

    public UserEntity getFromOracle(){
        return userOracleMapper.getOne(1);
    }

    @Transactional(transactionManager = "mysql2TransactionManager",propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void transactionTest(){
        userMysql2Mapper.delete(1);
        UserEntity userEntity = userMysql2Mapper.getOne(1);
        userEntity.getPassword();
    }

    @Autowired
    @Qualifier("mysql2TransactionManager")
    private PlatformTransactionManager platformTransactionManager;


    public void transactionTest2(){
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = platformTransactionManager.getTransaction(definition);
        try{
            userMysql2Mapper.delete(1);
            UserEntity userEntity = userMysql2Mapper.getOne(1);
            userEntity.getPassword();
        }catch (Exception e){
            platformTransactionManager.rollback(status);
            throw e;
        }
        platformTransactionManager.commit(status);
    }
}
