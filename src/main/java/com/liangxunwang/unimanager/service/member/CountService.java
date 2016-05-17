package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import org.springframework.stereotype.Service;

/**
 * Created by liuzwei on 2015/2/4.
 */
@Service("countService")
public class CountService implements SaveService {
    @Override
    public Object save(Object object) throws ServiceException {
        return null;
    }
}
