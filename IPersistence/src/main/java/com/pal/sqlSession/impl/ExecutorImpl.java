package com.pal.sqlSession.impl;

import com.pal.config.BoundSql;
import com.pal.pojo.Configuration;
import com.pal.pojo.MappedStatement;
import com.pal.sqlSession.Executor;
import com.pal.utils.GenericTokenParser;
import com.pal.utils.ParameterMapping;
import com.pal.utils.ParameterMappingTokenHandler;
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pal
 * @date 2020/8/28 2:34 下午
 */
public class ExecutorImpl implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params){
        
        try {
            //1.注册驱动、获取连接 -> 从dataSource里获取
            Connection connection = configuration.getDataSource().getConnection();

            //2.获取sql语句并转换
            String sql = mappedStatement.getSql();
            BoundSql boundSql = getBoundSql(sql);

            //3.获取预处理对象：preparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

            //4.设置参数，根据ParameterMapping的存储的参数名称对应
            //获取参数全路径
            String paramterType = mappedStatement.getParamterType();
            Class<?> paramterTypeClass =  getClassType(paramterType);
            List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
            for (int i = 0;i<parameterMappingList.size();i++){
                ParameterMapping parameterMapping = parameterMappingList.get(i);
                String context = parameterMapping.getContent();
                //反射 获取与sql里变量参数对应的属性对象
                Field declaredField = paramterTypeClass.getDeclaredField(context);
                //暴力访问
                declaredField.setAccessible(true);
                //获取值（id）
                Object o = declaredField.get(params[0]);
                
                //设置参数
                preparedStatement.setObject(i+1,o);
            }

            //5.执行sql
            ResultSet resultSet = preparedStatement.executeQuery();
            String resultType = mappedStatement.getResultType();
            Class<?> resultTypeClass = getClassType(resultType);

            ArrayList<Object> returnList = new ArrayList<>();
            while (resultSet.next()){
                //获取User
                Object o = resultTypeClass.newInstance();
                //获取源数据
                ResultSetMetaData metaData = resultSet.getMetaData();

                for (int i = 1;i <= metaData.getColumnCount();i++){

                    //获取字段名
                    String columnName = metaData.getColumnName(i);
                    //获取字段值
                    Object value = resultSet.getObject(columnName);
                    //反射，根据数据库与实体对应关系，完成封装
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    writeMethod.invoke(o,value);
                }

                returnList.add(o);
            }
            //6.封装返回结果集
            return (List<E>) returnList;


        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 根据路径获取class
     * @param paramterType
     * @return
     */
    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if (paramterType!=null){
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }
        return null;
    }

    /**
     * 完成对#{}的解析为值 并 替换成 ？
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //1.标记处理类：配置标志解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析后的sql
        String parseSql = genericTokenParser.parse(sql);
        //#{}里面解析出的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        return new BoundSql(parseSql, parameterMappings);
    }


}
