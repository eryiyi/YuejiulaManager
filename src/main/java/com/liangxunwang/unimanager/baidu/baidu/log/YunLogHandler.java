package com.liangxunwang.unimanager.baidu.baidu.log;

import com.liangxunwang.unimanager.baidu.log.YunLogEvent;

public interface YunLogHandler {

    public void onHandle(YunLogEvent event);

}
