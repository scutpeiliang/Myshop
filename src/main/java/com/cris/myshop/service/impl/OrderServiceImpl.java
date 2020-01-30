package com.cris.myshop.service.impl;

import com.cris.myshop.dao.GoodDao;
import com.cris.myshop.dao.OrderDao;
import com.cris.myshop.entity.Good;
import com.cris.myshop.entity.Order;
import com.cris.myshop.entity.User;
import com.cris.myshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodDao goodDao;

    @Override
    public Order createNewOrder(User user, Good good) {
        //预减库存
        goodDao.reduceStock(good.getGid());
        //生成订单
        Date createTime = new Date();
        Date expireTime = new Date(createTime.getTime() + 86400000);
        Order order = new Order(user.getUid(), good.getGid(), good.getDiscountPrice(), 0, createTime, expireTime);
        orderDao.createNewOrder(order);
        return order;
    }

    @Override
    public Order getOrderByUsername(String username) {
        return orderDao.getOrderByUsername(username);
    }
}
