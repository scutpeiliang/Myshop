package com.cris.myshop.service.impl;

import com.cris.myshop.dao.GoodDao;
import com.cris.myshop.entity.Good;
import com.cris.myshop.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodDao goodDao;

    @Override
    public List<Good> getAllGoods() {
        return goodDao.getAllGoods();
    }

    @Override
    public Good getGoodById(int id) {
        return goodDao.getGoodById(id);
    }
}
