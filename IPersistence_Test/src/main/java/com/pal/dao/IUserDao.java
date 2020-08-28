package com.pal.dao;

import com.pal.pojo.User;

import java.util.List;

/**
 * @author pal
 */
public interface IUserDao {

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll();

    /**
     * 条件查询
     * @param user
     * @return
     */
    User findByCondition(User user);
}
