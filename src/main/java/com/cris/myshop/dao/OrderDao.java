package com.cris.myshop.dao;

import com.cris.myshop.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {

    Order getOrderByUsername(String username);

    void createNewOrder(Order order);
}
