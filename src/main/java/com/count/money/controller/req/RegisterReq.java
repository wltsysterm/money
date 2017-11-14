package com.count.money.controller.req;

import javax.validation.constraints.NotNull;

public class RegisterReq {
    @NotNull(message = "真实姓名不能为空")
    private String trueName;
    @NotNull(message = "学院不能为空")
    private String college;
    @NotNull(message = "专业不能为空")
    private String major;
    @NotNull(message = "学号不能为空")
    private String sn;
    @NotNull(message = "密码不能为空")
    private String password;

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

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
