package com.count.money.controller.resp;

/**
 * created by 魏霖涛 on 2017/11/15 0015
 */
public class DayUnsettleBillResp {
    private String hour;
    private String totalMoney;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}
