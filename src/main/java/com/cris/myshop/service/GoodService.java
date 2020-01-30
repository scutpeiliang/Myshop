package com.cris.myshop.service;

import com.cris.myshop.entity.Good;

import java.util.List;

public interface GoodService {
    List<Good> getAllGoods();

    Good getGoodById(int id);
}
