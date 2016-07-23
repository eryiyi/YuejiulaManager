package com.liangxunwang.unimanager.service.app;

import com.liangxunwang.unimanager.dao.AppVideosZanDao;
import com.liangxunwang.unimanager.model.Zan;
import com.liangxunwang.unimanager.mvc.vo.ZanVO;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhl on 2015/2/3.
 */
@Service("appVideoZanService")
public class AppVideoZanService implements SaveService , ListService{
    private static Logger logger = LogManager.getLogger(AppVideoZanService.class);
    @Autowired
    @Qualifier("appVideosZanDao")
    private AppVideosZanDao zanDao;

    @Override
    public Object save(Object object) throws ServiceException {
        Zan zan = (Zan) object;
        List<Zan> check = zanDao.findByParams(zan.getRecordId(), zan.getEmpId());//查看是否已经赞过
        if (check.size() > 0) {
            throw new ServiceException(Constants.HAS_ZAN);//已经赞过了
        }
        zan.setId(UUIDFactory.random());
        zan.setDateline(System.currentTimeMillis() + "");
        zanDao.save(zan);
        return zan;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        List<ZanVO> list =zanDao.listZanByRecord((String) object);
        for (ZanVO zanVO : list){
            if (zanVO.getCover().startsWith("upload")) {
                zanVO.setCover(Constants.URL + zanVO.getCover());
            }else {
                zanVO.setCover(Constants.QINIU_URL + zanVO.getCover());
            }
            zanVO.setDateline(RelativeDateFormat.format(Long.parseLong(zanVO.getDateline())));
        }
        return list;
    }

}
