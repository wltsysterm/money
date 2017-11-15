package com.count.money.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.count.money.po.MoneyMember;

import java.util.List;
import java.util.Map;
public interface MoneyMemberDao extends BaseMapper<MoneyMember> {
    List<MoneyMember> selectMemberBySn(Map map);
    int selectMemberCountBySn(Map map);
}
