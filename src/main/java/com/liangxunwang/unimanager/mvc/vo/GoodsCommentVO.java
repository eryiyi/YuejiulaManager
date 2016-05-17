package com.liangxunwang.unimanager.mvc.vo;

import com.liangxunwang.unimanager.model.GoodsComment;

/**
 * Created by liuzwei on 2015/2/5.
 */
public class GoodsCommentVO extends GoodsComment {
    private String fempId;//父评论者ID
    private String nickName;//评论人昵称
    private String cover;//评论人头像
    private String fNickName;//父评论者昵称

    public String getFempId() {
        return fempId;
    }

    public void setFempId(String fempId) {
        this.fempId = fempId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getfNickName() {
        return fNickName;
    }

    public void setfNickName(String fNickName) {
        this.fNickName = fNickName;
    }
}
