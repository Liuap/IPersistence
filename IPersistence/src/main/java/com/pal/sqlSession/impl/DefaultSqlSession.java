package com.pal.sqlSession.impl;

import com.pal.pojo.Configuration;
import com.pal.pojo.MappedStatement;
import com.pal.sqlSession.SqlSession;

import java.lang.reflect.*;
import java.util.List;

/**
 * @author pal
 * @date 2020/8/28 2:10 下午
 */

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) {
        //调用Executor来操作jdbc
        ExecutorImpl executor = new ExecutorImpl();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = executor.query(configuration, mappedStatement, params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) {
        List<Object> objects = selectList(statementId, params);

        if (objects.size() == 1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或过多");
        }
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用动态代理，为Dao对象生成代理对象
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            //代理对象调用接口任一方法，都会执行invoke
            @Override
            //参数：当前代理对象的应用，当前调用方法的引用（findAll）,传递的参数
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //定位SQL:namespace+方法名
                String daoMethodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String statementId = className+"."+daoMethodName;

                //实际参数 params : args
                //根据调用方法的返回值类型来判断 查一个还是全查
                Type methodReturnType = method.getGenericReturnType();
                //判断是否进行了泛型类型参数化（返回值有没有泛型）
                if (methodReturnType instanceof ParameterizedType){
                    return selectList(statementId, args);
                }

                return selectOne(statementId,args);
            }
        });
        return (T) proxyInstance;
    }
}
