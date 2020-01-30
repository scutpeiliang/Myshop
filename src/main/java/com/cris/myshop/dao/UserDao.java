package com.cris.myshop.dao;

import com.cris.myshop.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User getUserByUsername(User user);
}
