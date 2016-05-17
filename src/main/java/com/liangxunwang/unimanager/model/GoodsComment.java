package com.liangxunwang.unimanager.model;

/**
 * Created by liuzwei on 2015/2/5.
 *
 * 商品评论
 */

public class GoodsComment {
    private String id;
    private String goodsId;
    private String content;
    private String fplid;
    private String empId;
    private String dateline;
    private String goodsEmpId;
    private String fplempid;

    public String getFplempid() {
        return fplempid;
    }

    public void setFplempid(String fplempid) {
        this.fplempid = fplempid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFplid() {
        return fplid;
    }

    public void setFplid(String fplid) {
        this.fplid = fplid;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getGoodsEmpId() {
        return goodsEmpId;
    }

    public void setGoodsEmpId(String goodsEmpId) {
        this.goodsEmpId = goodsEmpId;
    }
}
