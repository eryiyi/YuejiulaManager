package com.liangxunwang.unimanager.baidu.baidu.channel.model;


import com.liangxunwang.unimanager.baidu.annotation.JSonPath;
import com.liangxunwang.unimanager.baidu.channel.model.ChannelResponse;
import com.liangxunwang.unimanager.baidu.channel.model.TagInfo;

import java.util.LinkedList;
import java.util.List;

public class QueryUserTagsResponse extends ChannelResponse {

    @JSonPath(path = "response_params\\tag_num")
    private int tagNum;

    @JSonPath(path = "response_params\\tags")
    private List<TagInfo> tags = new LinkedList<TagInfo>();

    public int getTagNum() {
        return tagNum;
    }

    public void setTagNum(int tagNum) {
        this.tagNum = tagNum;
    }

    public List<TagInfo> getTags() {
        return tags;
    }

    public void setTags(List<TagInfo> tags) {
        this.tags = tags;
    }

}
