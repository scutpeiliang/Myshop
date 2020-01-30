package com.cris.myshop.rabbitmq;

import com.alibaba.fastjson.JSONArray;
import com.cris.myshop.entity.BuySuccess;
import com.cris.myshop.entity.Good;
import com.cris.myshop.entity.User;
import com.cris.myshop.service.GoodService;
import com.cris.myshop.service.OrderService;
import com.cris.myshop.service.UserService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 完成下订
 */
@Component
public class Receiver {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodService goodService;

    @RabbitListener(bindings =
    @QueueBinding(value = @Queue(value = "buySuccessQueue", autoDelete = "false"),
            exchange = @Exchange(value = "hasBuy", type = ExchangeTypes.DIRECT),
            key = "buySuccess"
    )
    )
    @RabbitHandler
    public void createOrder(Message msg) {
        String json = new String(msg.getBody());
        BuySuccess buySuccess = JSONArray.parseObject(json, BuySuccess.class);
        User user = userService.getUserByUsername(new User(buySuccess.getUsername()));
        Good good = goodService.getGoodById(buySuccess.getId());
        orderService.createNewOrder(user, good);
    }
}
