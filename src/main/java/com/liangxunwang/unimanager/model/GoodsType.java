package com.liangxunwang.unimanager.model;

/**
 * Created by zhl on 2015/2/2.
 *
 * 商品分类
 */

public class GoodsType {
    private String typeId;
    private String typeName;
    private String typeContent;
    private String typeIsUse;
    private String typeCover;
    private String typeIsBusiness;//是否商家

    private String lx_goods_type_type;
    private String lx_goods_type_url;
    private String school_id;
    private String emp_id;
    private String schoolName;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getLx_goods_type_type() {
        return lx_goods_type_type;
    }

    public void setLx_goods_type_type(String lx_goods_type_type) {
        this.lx_goods_type_type = lx_goods_type_type;
    }

    public String getLx_goods_type_url() {
        return lx_goods_type_url;
    }

    public void setLx_goods_type_url(String lx_goods_type_url) {
        this.lx_goods_type_url = lx_goods_type_url;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeContent() {
        return typeContent;
    }

    public void setTypeContent(String typeContent) {
        this.typeContent = typeContent;
    }

    public String getTypeIsUse() {
        return typeIsUse;
    }

    public void setTypeIsUse(String typeIsUse) {
        this.typeIsUse = typeIsUse;
    }

    public String getTypeCover() {
        return typeCover;
    }

    public void setTypeCover(String typeCover) {
        this.typeCover = typeCover;
    }

    public String getTypeIsBusiness() {
        return typeIsBusiness;
    }

    public void setTypeIsBusiness(String typeIsBusiness) {
        this.typeIsBusiness = typeIsBusiness;
    }
}
