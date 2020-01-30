package com.cris.myshop.controller;

import com.cris.myshop.entity.Good;
import com.cris.myshop.entity.Order;
import com.cris.myshop.entity.User;
import com.cris.myshop.service.GoodService;
import com.cris.myshop.service.OrderService;
import com.cris.myshop.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController {
    @Autowired
    private UserService userService;
    @Autowired
    private GoodService goodService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order")
    public String getOrderByUsername(Model model) {
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        Order order = orderService.getOrderByUsername(username);
        User user = userService.getUserByUsername(new User(username));
        Good good = goodService.getGoodById(order.getGid());
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        model.addAttribute("good", good);
        return "order";
    }

    @ResponseBody
    @RequestMapping("/checkOrderExist")
    public int checkOrderExist(@RequestParam String username) {
        Order order = orderService.getOrderByUsername(username);
        return order == null? 0: 1;
    }
}
