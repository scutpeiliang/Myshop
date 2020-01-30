package com.cris.myshop.service;

import com.cris.myshop.entity.Good;
import com.cris.myshop.entity.Order;
import com.cris.myshop.entity.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    @Transactional(propagation = Propagation.REQUIRED)
    Order createNewOrder(User user, Good good);

    Order getOrderByUsername(String username);
}
