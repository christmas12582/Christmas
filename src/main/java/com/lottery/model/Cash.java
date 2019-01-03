package com.lottery.model;

import java.util.Date;

public class Cash {
    private Integer id;

    private Integer createid;

    private Date createtime;

    private Integer isexchange;

    private Integer money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreateid() {
        return createid;
    }

    public void setCreateid(Integer createid) {
        this.createid = createid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getIsexchange() {
        return isexchange;
    }

    public void setIsexchange(Integer isexchange) {
        this.isexchange = isexchange;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}