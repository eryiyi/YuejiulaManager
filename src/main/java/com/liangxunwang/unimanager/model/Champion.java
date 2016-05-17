package com.liangxunwang.unimanager.model;

/**
 * Created by liuzwei on 2015/4/9.
 * 冠军表
 */
public class Champion {
    private String id;
    private String themeNumber;//期次
    private String themeId;//主题ID
    private String empId;//会员ID
    private String schoolId;//学校ID
    private String zpId;//作品ID
    private String type;//类别  0 全国  1学校
    private String dateline;
    private String isPic;//是否传图  0未传  1传了
    private String uploadPic;//上传的图片
    private String isSure;//是否确认收到奖品 0  未领奖  1已领奖

    public String getIsPic() {
        return isPic;
    }

    public void setIsPic(String isPic) {
        this.isPic = isPic;
    }

    public String getUploadPic() {
        return uploadPic;
    }

    public void setUploadPic(String uploadPic) {
        this.uploadPic = uploadPic;
    }

    public String getIsSure() {
        return isSure;
    }

    public void setIsSure(String isSure) {
        this.isSure = isSure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThemeNumber() {
        return themeNumber;
    }

    public void setThemeNumber(String themeNumber) {
        this.themeNumber = themeNumber;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getZpId() {
        return zpId;
    }

    public void setZpId(String zpId) {
        this.zpId = zpId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }
}
