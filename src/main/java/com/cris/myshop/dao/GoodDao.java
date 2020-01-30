package com.cris.myshop.dao;

import com.cris.myshop.entity.Good;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodDao {
    List<Good> getAllGoods();

    Good getGoodById(int id);

    void reduceStock(int id);
}
