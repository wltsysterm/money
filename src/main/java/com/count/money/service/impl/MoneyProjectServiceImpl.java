package com.count.money.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.count.money.dao.MoneyProjectDao;
import com.count.money.po.MoneyProject;
import com.count.money.service.IMoneyProjectService;
import org.springframework.stereotype.Service;

@Service()
public class MoneyProjectServiceImpl extends ServiceImpl<MoneyProjectDao,MoneyProject> implements IMoneyProjectService {

}
