package com.count.money.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.count.money.dao.MoneyMemberDao;
import com.count.money.po.MoneyMember;
import com.count.money.service.IMoneyMemberService;
import org.springframework.stereotype.Service;

@Service()
public class MoneyMemberServiceImpl extends ServiceImpl<MoneyMemberDao,MoneyMember> implements IMoneyMemberService {

}
