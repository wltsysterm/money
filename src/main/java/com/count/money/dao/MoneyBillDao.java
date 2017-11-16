package com.count.money.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.count.money.controller.resp.DayUnsettleBillResp;
import com.count.money.po.MoneyBill;

import java.util.List;
import java.util.Map;

public interface MoneyBillDao extends BaseMapper<MoneyBill> {
    List<Map<String,Object>> selectCurrentSituation(Map map);
}
