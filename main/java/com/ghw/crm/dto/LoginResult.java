package com.ghw.crm.dto;

import com.ghw.crm.pojo.CrmUser;
//用于封装 结果
public class LoginResult {
    private CrmUser user;
    private String msg;

    public CrmUser getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "user=" + user +
                ", msg='" + msg + '\'' +
                '}';
    }

    public void setUser(CrmUser user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
