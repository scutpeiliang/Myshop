package com.cris.myshop.controller;

import com.alibaba.fastjson.JSONArray;
import com.cris.myshop.entity.BuySuccess;
import com.cris.myshop.entity.Good;
import com.cris.myshop.entity.Order;
import com.cris.myshop.entity.User;
import com.cris.myshop.rabbitmq.Sender;
import com.cris.myshop.service.GoodService;
import com.cris.myshop.service.OrderService;
import com.cris.myshop.service.UserService;
import com.cris.myshop.utils.RedisTool;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

@Controller
public class GoodController {
    @Autowired
    private GoodService goodService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private Jedis jedis;
    @Autowired
    private RedisTool redisTool;
    @Autowired
    private Sender sender;

    @RequestMapping("/list")
    public String listAllGoods(Model model) {
        List<Good> list = goodService.getAllGoods();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping("/detail")
    public String goodDetail(@RequestParam int id, Model model) {
        Good good = null;
        //先尝试从redis读取商品详情
        String goodJson = jedis.get("good_" + id);
        if (goodJson != null) {
            good = JSONArray.parseObject(goodJson, Good.class);
            int stock = Integer.parseInt(jedis.get("good_stock_" + id));
            good.setStock(stock);
        } else {
            //从数据库读取，并存到redis中
            good = goodService.getGoodById(id);
            String json = JSONArray.toJSONString(good);
            jedis.set("good_" + id, json);
            jedis.set("good_stock_" + id, good.getStock() + "");
            jedis.set("good_beginTime_" + id, JSONArray.toJSONString(good.getBeginTime()));
            jedis.set("good_endTime_" + id, JSONArray.toJSONString(good.getEndTime()));
        }
        long beginTime = good.getBeginTime().getTime();
        long endTime = good.getEndTime().getTime();
        long currentTime = System.currentTimeMillis();
        int code = 0;           //0表示秒杀尚未开始，1表示秒杀进行中，2表示秒杀已结束
        long remainTime = 0;    //秒杀剩余时间(秒)
        if (currentTime > endTime || good.getStock() <= 0) {
            //秒杀已结束
            code = 2;
        } else if (currentTime < beginTime) {
            //秒杀尚未开始
            remainTime = (beginTime - currentTime) / 1000;
        } else if (currentTime < endTime) {
            //秒杀进行中
            code = 1;
            remainTime = (endTime - currentTime) / 1000;
        }
        model.addAttribute("code", code);
        model.addAttribute("remainTime", remainTime);
        model.addAttribute("good", good);
        return "detail";
    }

    /*@RequestMapping("/buy")
    public String buy(@RequestParam int id, Model model) {
        Good good = goodService.getGoodById(id);
        long beginTime = good.getBeginTime().getTime();
        long endTime = good.getEndTime().getTime();
        long currentTime = System.currentTimeMillis();
        //查询该用户是否已经下过订单（限购一人一单）
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        User user = userService.getUserByUsername(new User(username));
        Order preOrder = orderService.getOrderByUsername(username);
        String msg = "";
        if (currentTime < beginTime) {
            msg = "抢购尚未开始！";
        } else if (currentTime > endTime) {
            msg = "抢购已结束！";
        } else if (good.getStock() <= 0) {
            msg = "已售罄！";
        } else if (preOrder != null) {
            msg = "限购一单！";
        }
        if (!"".equals(msg)) {
            model.addAttribute("msg", msg);
            model.addAttribute("user", user);
            return "fail";
        }
        //预减库存并下订单
        Order order = orderService.createNewOrder(user, good);
        model.addAttribute("user", user);
        model.addAttribute("good", good);
        model.addAttribute("order", order);
        return "order";
    }*/

    @RequestMapping("/buy")
    public String buy(@RequestParam int id, Model model) {
        long beginTime = JSONArray.parseObject(jedis.get("good_beginTime_" + id), Date.class).getTime();
        long endTime = JSONArray.parseObject(jedis.get("good_endTime_" + id), Date.class).getTime();
        long currentTime = System.currentTimeMillis();
        //查询该用户是否已经下过订单（限购一人一单）
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        User user = userService.getUserByUsername(new User(username));
        Order preOrder = orderService.getOrderByUsername(username);
        String msg = "";
        if (currentTime < beginTime) {
            msg = "抢购尚未开始！";
        } else if (currentTime > endTime) {
            msg = "抢购已结束！";
        } else if (preOrder != null) {
            msg = "限购一单！";
        }
        if (!"".equals(msg)) {
            model.addAttribute("msg", msg);
            model.addAttribute("user", user);
            return "fail";
        }
        //在redis预减库存
        while (!redisTool.tryGetDistributedLock(jedis, "good_lock_" + id, username, 20)) {}
        int stock = Integer.parseInt(jedis.get("good_stock_" + id));
        if (stock <= 0) {
            //已售罄
            msg = "已售罄！";
        } else {
            stock--;
            jedis.set("good_stock_" + id, stock + "");
        }
        redisTool.releaseDistributedLock(jedis, "good_lock_" + id, username);
        //已售罄则转向提示页面
        if (!"".equals(msg)) {
            model.addAttribute("msg", msg);
            model.addAttribute("user", user);
            return "fail";
        }
        //抢购成功，本次抢购行为放到消息队列中待写入数据库
        BuySuccess buySuccess = new BuySuccess(username, id);
        sender.send("hasBuy", "buySuccess", JSONArray.toJSONString(buySuccess));
        //返回等待页面
        model.addAttribute("username", username);
        model.addAttribute("gid", id);
        return "wait";
    }
}
