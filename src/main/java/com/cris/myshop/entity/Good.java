package com.cris.myshop.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品实体类
 */
public class Good implements Serializable {
    private int gid;
    private String name;
    private String description;
    private double price;
    private double discountPrice;
    private int stock;
    private String img;
    private Date createTime;
    private Date updateTime;
    private Date beginTime;
    private Date endTime;

    public Good() {
    }

    public Good(int gid, String name, String description, double price, double discountPrice, int stock, String img, Date createTime, Date updateTime, Date beginTime, Date endTime) {
        this.gid = gid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.stock = stock;
        this.img = img;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Good{" +
                "gid=" + gid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", stock=" + stock +
                ", img='" + img + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }
}
