package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.PkPrizeDao;
import com.liangxunwang.unimanager.model.PkPrize;
import com.liangxunwang.unimanager.mvc.vo.PkPrizeVO;
import com.liangxunwang.unimanager.query.PkPrizeQuery;
import com.liangxunwang.unimanager.service.DeleteService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 15-4-6.
 */
@Service("pkPrizeService")
public class PkPrizeService implements SaveService, DeleteService, ListService {
    @Autowired
    @Qualifier("pkPrizeDao")
    private PkPrizeDao pkPrizeDao;

    @Override
    public Object save(Object object) throws ServiceException {
        PkPrize pkPrize = (PkPrize) object;

        //商家添加奖品
        if (pkPrize.getType().equals("1")){
            String[] schoolIds = pkPrize.getSchoolId().split(",");
            for (int i=0; i<schoolIds.length; i++){
                PkPrize prize = pkPrizeDao.findBySchoolIdAndThemeId(pkPrize.getThemeId(), schoolIds[i], pkPrize.getType());
                if (prize != null){
                    throw new ServiceException("HAS_PRIZE");
                }
                pkPrize.setId(UUIDFactory.random());
                pkPrize.setSchoolId(schoolIds[i]);
                pkPrize.setDateline(System.currentTimeMillis() + "");
                pkPrizeDao.save(pkPrize);
            }
        }else {//我们添加奖品
            PkPrize check = pkPrizeDao.findByThemeId(pkPrize.getThemeId(), pkPrize.getType());
            if (check != null){
                throw new ServiceException("HAS_PRIZE");
            }
            pkPrize.setId(UUIDFactory.random());
            pkPrize.setDateline(System.currentTimeMillis() + "");
            pkPrizeDao.save(pkPrize);
        }
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        pkPrizeDao.deleteById((String) object);
        return null;
    }


    @Override
    public Object list(Object object) throws ServiceException {
        PkPrizeQuery query = (PkPrizeQuery) object;
        int index = ((query.getIndex()-1)*query.getSize())+1;
        int size = query.getIndex()*query.getSize();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        if (!StringUtil.isNullOrEmpty(query.getEmpId())){
            map.put("empId", query.getEmpId());
        }
        if (!StringUtil.isNullOrEmpty(query.getThemeId())){
            map.put("themeId", query.getThemeId());
        }
        if (!StringUtil.isNullOrEmpty(query.getSchoolId())){
            map.put("schoolId", query.getSchoolId());
        }
        List<PkPrizeVO> list = pkPrizeDao.findByEmpId(map);
        for (PkPrizeVO vo : list){
            vo.setPic(Constants.URL+vo.getPic());
            vo.setDateline(RelativeDateFormat.format(Long.parseLong(vo.getDateline())));
        }
        return list;
    }
}
