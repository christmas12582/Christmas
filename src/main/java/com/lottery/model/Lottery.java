package com.lottery.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Lottery {
    private Integer id;

    private Integer isvalid;

    private Integer mcount;

    private Integer forceshare;

    private Integer viewcount;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

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

	/**
	 * @return the updatetime
	 */
	public Date getUpdatetime() {
		return updatetime;
	}

	/**
	 * @param updatetime the updatetime to set
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}