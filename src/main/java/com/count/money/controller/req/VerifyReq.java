package com.count.money.controller.req;

import org.hibernate.validator.constraints.Length;

/**
 * created by 魏霖涛 on 2017/11/14 0014
 */
public class VerifyReq {
    private String state;
    @Length(max = 450,message = "评审意见长度不能超过450")
    private String note;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
