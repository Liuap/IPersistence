package com.pal.sqlSession;

import java.util.List;

public interface SqlSession {

    /**
     * 查询所有
     * @param statementId
     * @param params
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String statementId, Object... params);

    /**
     * 查询一个
     * @param <T>
     * @param statementId
     * @param params
     * @return
     */
    public <T> Object selectOne(String statementId, Object... params);

    /**
     * 为Dao接口生成代理实现类
     * @param mapperClass
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<?> mapperClass);
}
