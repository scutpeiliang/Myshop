package com.cris.myshop.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单实体类
 */
public class Order implements Serializable {
    private int oid;
    private int uid;
    private int gid;
    private double money;
    private int status;
    private Date createTime;
    private Date expireTime;

    public Order() {
    }

    public Order(int uid, int gid, double money, int status, Date createTime, Date expireTime) {
        this.uid = uid;
        this.gid = gid;
        this.money = money;
        this.status = status;
        this.createTime = createTime;
        this.expireTime = expireTime;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", uid=" + uid +
                ", gid=" + gid +
                ", money=" + money +
                ", status=" + status +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                '}';
    }
}
