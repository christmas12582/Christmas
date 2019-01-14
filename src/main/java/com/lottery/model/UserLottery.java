package com.lottery.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserLottery {
    private Integer id;

    private Date lotterydate;

    private Integer lotteryitemid;

    private Integer userid;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date exchangedate;

    private Integer lotteryid;

    private String prizenum;

    private String sharenum;
    
    private LotteryItem lotteryItem;
    
    private Integer otheruserid;
    
    private User business;

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

    public String getSharenum() {
        return sharenum;
    }

    public void setSharenum(String sharenum) {
        this.sharenum = sharenum == null ? null : sharenum.trim();
    }

	/**
	 * @return the lotteryItem
	 */
	public LotteryItem getLotteryItem() {
		return lotteryItem;
	}

	/**
	 * @param lotteryItem the lotteryItem to set
	 */
	public void setLotteryItem(LotteryItem lotteryItem) {
		this.lotteryItem = lotteryItem;
	}

	/**
	 * @return the otheruserid
	 */
	public Integer getOtheruserid() {
		return otheruserid;
	}

	/**
	 * @param otheruserid the otheruserid to set
	 */
	public void setOtheruserid(Integer otheruserid) {
		this.otheruserid = otheruserid;
	}

	/**
	 * @return the business
	 */
	public User getBusiness() {
		return business;
	}

	/**
	 * @param business the business to set
	 */
	public void setBusiness(User business) {
		this.business = business;
	}

}