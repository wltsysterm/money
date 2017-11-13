package com.count.money.controller.req;

import javax.validation.constraints.NotNull;

public class LoginReq {
    @NotNull(message="学号不能为空")
    private String sn;
    @NotNull(message="密码不能为空")
    private String password;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
