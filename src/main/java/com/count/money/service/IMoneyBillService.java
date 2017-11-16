package com.count.money.service;

import com.baomidou.mybatisplus.service.IService;
import com.count.money.controller.resp.DayUnsettleBillResp;
import com.count.money.po.MoneyBill;
import java.util.Map;
import java.util.List;

public interface IMoneyBillService extends IService<MoneyBill>{
    List<Map<String,Object>> selectCurrentSituation(Map map);
}
