package com.crawler.bean;

import java.util.Date;

public class Task {
    private int id;
    private String seedUrl;
    private int dayNum;
    private Boolean isFinish;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeedUrl() {
        return seedUrl;
    }

    public void setSeedUrl(String seedUrl) {
        this.seedUrl = seedUrl;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public Boolean getFinish() {
        return isFinish;
    }

    public void setFinish(Boolean finish) {
        isFinish = finish;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
