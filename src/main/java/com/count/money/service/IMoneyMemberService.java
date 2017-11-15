package com.count.money.service;

import com.baomidou.mybatisplus.service.IService;
import com.count.money.po.MoneyMember;

import java.util.List;
import java.util.Map;
public interface IMoneyMemberService extends IService<MoneyMember>{
    List<MoneyMember> selectMemberBySn(Map map);
    int selectMemberCountBySn(Map map);
}
