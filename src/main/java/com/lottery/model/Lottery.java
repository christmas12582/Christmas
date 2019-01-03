package com.lottery.model;

public class Lottery {
    private Integer id;

    private Integer isvalid;

    private Integer mcount;

    private Integer forceshare;

    private Integer viewcount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Integer isvalid) {
        this.isvalid = isvalid;
    }

    public Integer getMcount() {
        return mcount;
    }

    public void setMcount(Integer mcount) {
        this.mcount = mcount;
    }

    public Integer getForceshare() {
        return forceshare;
    }

    public void setForceshare(Integer forceshare) {
        this.forceshare = forceshare;
    }

    public Integer getViewcount() {
        return viewcount;
    }

    public void setViewcount(Integer viewcount) {
        this.viewcount = viewcount;
    }
}