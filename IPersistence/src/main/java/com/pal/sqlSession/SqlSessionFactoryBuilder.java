package com.pal.sqlSession;

import com.pal.config.XMLConfigBuilder;
import com.pal.pojo.Configuration;
import com.pal.sqlSession.impl.DefaultSqlSessionFactory;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author pal
 * @date 2020/8/28 10:41 上午
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException {
        //1.使用dom4j解析配置文件，并封装到Configuration
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(in);
        //2.创建Factory对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;
    }
}
