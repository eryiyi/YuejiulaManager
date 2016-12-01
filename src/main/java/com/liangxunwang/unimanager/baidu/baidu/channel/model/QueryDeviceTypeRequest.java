package com.liangxunwang.unimanager.baidu.baidu.channel.model;


import com.liangxunwang.unimanager.baidu.annotation.HttpPathKeyName;
import com.liangxunwang.unimanager.baidu.annotation.R;
import com.liangxunwang.unimanager.baidu.channel.model.ChannelRequest;

public class QueryDeviceTypeRequest extends ChannelRequest {

    @HttpPathKeyName(param = R.OPTIONAL)
    private Long channelId = null;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

}
