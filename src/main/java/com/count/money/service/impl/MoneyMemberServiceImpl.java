package com.count.money.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.count.money.dao.MoneyMemberDao;
import com.count.money.po.MoneyMember;
import com.count.money.service.IMoneyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service()
public class MoneyMemberServiceImpl extends ServiceImpl<MoneyMemberDao,MoneyMember> implements IMoneyMemberService {
    @Autowired
    private MoneyMemberDao moneyMemberDao;
    @Override
    public List<MoneyMember> selectMemberBySn(Map map) {
        return moneyMemberDao.selectMemberBySn(map);
    }
    @Override
    public int selectMemberCountBySn(Map map) {
        return moneyMemberDao.selectMemberCountBySn(map);
    }
}
