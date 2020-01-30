package com.cris.myshop.entity;

import java.io.Serializable;

/**
 * 一次成功抢购的实体类
 */
public class BuySuccess implements Serializable {
    private String username;   //用户名
    private int id;            //商品id

    public BuySuccess(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BuySuccess{" +
                "username='" + username + '\'' +
                ", id=" + id +
                '}';
    }
}
