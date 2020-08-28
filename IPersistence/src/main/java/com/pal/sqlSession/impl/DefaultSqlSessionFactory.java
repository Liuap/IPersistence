package com.pal.sqlSession.impl;

import com.pal.pojo.Configuration;
import com.pal.sqlSession.SqlSession;
import com.pal.sqlSession.SqlSessionFactory;

/**
 * @author pal
 * @date 2020/8/28 2:05 下午
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
