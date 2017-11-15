package com.count.money.controller.req;

import javax.validation.constraints.NotNull;

public class BillReq {
    private String id;
    @NotNull(message = "项目不能为空")
    private String projectName;
    @NotNull(message = "收费标准不能为空")
    private String projectPrice;
    @NotNull(message = "数量不能为空")
    private String projectCount;
    private String memberId;
    private String state;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPrice() {
        return projectPrice;
    }

    public void setProjectPrice(String projectPrice) {
        this.projectPrice = projectPrice;
    }

    public String getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(String projectCount) {
        this.projectCount = projectCount;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
