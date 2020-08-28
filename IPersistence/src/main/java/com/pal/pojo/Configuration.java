package com.pal.pojo;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * sqlMapConfig对应内容
 * @author pal
 * @date 2020/8/28 10:34 上午
 */
@Data
public class Configuration {

    /**
     * 连接数据库所需信息的数据源对象
     */
    private DataSource dataSource;

    /**
     * MappedStatement的集合
     * key：Statement的id：namespace.id,用来标识唯一的sql
     * value：封装好的mappedStatement对象
     */
    Map<String,MappedStatement> mappedStatementMap = new HashMap<>();
}
