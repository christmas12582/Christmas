package com.lottery.model;

import java.util.Date;

public class UserLottery {
    private Integer id;

    private Date lotterydate;

    private Integer lotteryitemid;

    private Integer userid;

    private Date exchangedate;

    private Integer lotteryid;

    private String prizenum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLotterydate() {
        return lotterydate;
    }

    public void setLotterydate(Date lotterydate) {
        this.lotterydate = lotterydate;
    }

    public Integer getLotteryitemid() {
        return lotteryitemid;
    }

    public void setLotteryitemid(Integer lotteryitemid) {
        this.lotteryitemid = lotteryitemid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getExchangedate() {
        return exchangedate;
    }

    public void setExchangedate(Date exchangedate) {
        this.exchangedate = exchangedate;
    }

    public Integer getLotteryid() {
        return lotteryid;
    }

    public void setLotteryid(Integer lotteryid) {
        this.lotteryid = lotteryid;
    }

    public String getPrizenum() {
        return prizenum;
    }

    public void setPrizenum(String prizenum) {
        this.prizenum = prizenum == null ? null : prizenum.trim();
    }
}