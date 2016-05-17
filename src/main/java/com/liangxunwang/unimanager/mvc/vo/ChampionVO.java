package com.liangxunwang.unimanager.mvc.vo;

import com.liangxunwang.unimanager.model.Champion;

/**
 * Created by liuzwei on 2015/4/10.
 */
public class ChampionVO extends Champion{
//    private String empId;
    private String empName;//冠军昵称
    private String empCover;//冠军会员头像
    private String zpContent;//作品内容
    private String schoolName;//学校名称
//    private String number;//第几期
    private String picUrl;
    private String videoUrl;
//    private String zpId;//作品ID
    private String zpType;//作品类型
    private String themeName;//主题名称
    private String zanNum;//赞数量
    private String plNum;//评论数量

//    public String getEmpId() {
//        return empId;
//    }
//
//    public void setEmpId(String empId) {
//        this.empId = empId;
//    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpCover() {
        return empCover;
    }

    public void setEmpCover(String empCover) {
        this.empCover = empCover;
    }

    public String getZpContent() {
        return zpContent;
    }

    public void setZpContent(String zpContent) {
        this.zpContent = zpContent;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getZpType() {
        return zpType;
    }

    public void setZpType(String zpType) {
        this.zpType = zpType;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getZanNum() {
        return zanNum;
    }

    public void setZanNum(String zanNum) {
        this.zanNum = zanNum;
    }

    public String getPlNum() {
        return plNum;
    }

    public void setPlNum(String plNum) {
        this.plNum = plNum;
    }
}
