package com.pal.pojo;

import lombok.Data;

/**
 * 封装Mapper.xml的对应信息
 * @author pal
 * @date 2020/8/28 10:30 上午
 */
@Data
public class MappedStatement {

    /**
     * id标识
     */
    private String id;

    /**
     * 返回值类型
     */
    private String resultType;

    /**
     * 参数类型
     */
    private String paramterType;

    /**
     * SQL语句
     */
    private String sql;
}
