package com.pal.sqlSession;

import com.pal.pojo.Configuration;
import com.pal.pojo.MappedStatement;

import java.util.List;

public interface Executor {

    /**
     *
     * @param configuration
     * @param mappedStatement
     * @param params
     * @param <E>
     * @return
     */
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement,Object... params);
}
