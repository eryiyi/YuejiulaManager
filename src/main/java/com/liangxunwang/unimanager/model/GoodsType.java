package com.liangxunwang.unimanager.model;

/**
 * Created by liuzwei on 2015/2/2.
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
