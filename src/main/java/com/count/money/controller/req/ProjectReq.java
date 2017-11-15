package com.count.money.controller.req;

import javax.validation.constraints.NotNull;

/**
 * created by 魏霖涛 on 2017/11/15 0015
 */
public class ProjectReq {
    private String id;
    @NotNull(message = "项目名不能为空")
    private String projectName;
    @NotNull(message = "收费标准不能为空")
    private String projectPrice;

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
}
