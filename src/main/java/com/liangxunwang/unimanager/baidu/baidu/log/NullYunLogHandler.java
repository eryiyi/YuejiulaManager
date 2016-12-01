package com.liangxunwang.unimanager.baidu.baidu.log;

import com.liangxunwang.unimanager.baidu.log.YunLogEvent;
import com.liangxunwang.unimanager.baidu.log.YunLogHandler;

public class NullYunLogHandler implements YunLogHandler {

    public void onHandle(YunLogEvent event) {
        // to nothing
    }

}
