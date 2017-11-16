package com.count.money.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.count.money.controller.resp.DayUnsettleBillResp;
import com.count.money.dao.MoneyBillDao;
import com.count.money.po.MoneyBill;
import com.count.money.service.IMoneyBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service()
public class MoneyBillServiceImpl extends ServiceImpl<MoneyBillDao,MoneyBill> implements IMoneyBillService {
    @Autowired
    private MoneyBillDao moneyBillDao;
    @Override
    public List<Map<String,Object>> selectCurrentSituation(Map map) {
        return  moneyBillDao.selectCurrentSituation(map);
    }
}
