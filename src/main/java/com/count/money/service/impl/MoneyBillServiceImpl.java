package com.count.money.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.count.money.dao.MoneyBillDao;
import com.count.money.po.MoneyBill;
import com.count.money.service.IMoneyBillService;
import org.springframework.stereotype.Service;

@Service()
public class MoneyBillServiceImpl extends ServiceImpl<MoneyBillDao,MoneyBill> implements IMoneyBillService {

}
