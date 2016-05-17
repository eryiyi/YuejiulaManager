package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.PkZanDao;
import com.liangxunwang.unimanager.dao.ThemeDao;
import com.liangxunwang.unimanager.dao.WorkDao;
import com.liangxunwang.unimanager.model.PKTheme;
import com.liangxunwang.unimanager.model.PKWork;
import com.liangxunwang.unimanager.model.PKZan;
import com.liangxunwang.unimanager.mvc.vo.PkZanVO;
import com.liangxunwang.unimanager.query.PkZanQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 15-4-5.
 */
@Service("pkZanService")
public class PkZanService implements SaveService, ListService {
    @Autowired
    @Qualifier("pkZanDao")
    private PkZanDao pkZanDao;

    @Autowired
    @Qualifier("workDao")
    private WorkDao workDao;

    @Autowired
    @Qualifier("themeDao")
    private ThemeDao themeDao;

    @Override
    public Object list(Object object) throws ServiceException {
        PkZanQuery query = (PkZanQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex() * query.getSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        map.put("zpId", query.getZpId());

        List<PkZanVO> list = pkZanDao.listPkZan(map);
        for (PkZanVO vo:list){
            if (vo.getEmpCover().startsWith("upload")) {
                vo.setEmpCover(Constants.URL + vo.getEmpCover());
            }else {
                vo.setEmpCover(Constants.QINIU_URL + vo.getEmpCover());
            }
            vo.setDateline(RelativeDateFormat.format(Long.parseLong(vo.getDateline())));
        }

        long count = pkZanDao.count(map);

        return new Object[]{count, list};
    }

    @Override
    public Object save(Object object) throws ServiceException {
        PKZan zan = (PKZan) object;
        PKZan check = pkZanDao.checkIsZan(zan.getEmpId(), zan.getZpId());
        PKWork work = workDao.findWorkById(zan.getZpId());
        if (work != null){
            PKTheme theme = themeDao.findByThemeId(work.getZtId());
            if (theme.getIsUse().equals("2")){
                throw new ServiceException("HAS_END");
            }
        }

        if (check != null){
            throw new ServiceException("HAS_PK_ZAN");
        }
        zan.setId(UUIDFactory.random());
        zan.setDateline(System.currentTimeMillis() + "");
        pkZanDao.save(zan);
        return null;
    }
}
