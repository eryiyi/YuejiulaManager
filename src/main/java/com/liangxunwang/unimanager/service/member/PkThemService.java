package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.ChampionDao;
import com.liangxunwang.unimanager.dao.ThemeDao;
import com.liangxunwang.unimanager.dao.WorkDao;
import com.liangxunwang.unimanager.model.Champion;
import com.liangxunwang.unimanager.model.PKTheme;
import com.liangxunwang.unimanager.mvc.vo.PkWorkVO;
import com.liangxunwang.unimanager.query.PkThemeQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.DateUtil;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 15-4-3.
 */
@Service("pkThemService")
public class PkThemService implements SaveService, ListService, DeleteService, UpdateService,FindService {
    @Autowired
    @Qualifier("themeDao")
    private ThemeDao themeDao;

    @Autowired
    @Qualifier("championDao")
    private ChampionDao championDao;

    @Autowired
    @Qualifier("workDao")
    private WorkDao workDao;

    public Object delete(Object object) throws ServiceException {
        themeDao.deleteById((String)object);
        return null;
    }

    public Object list(Object object) throws ServiceException {
        PkThemeQuery query = (PkThemeQuery) object;
        boolean isApp = query.isApp();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex() * query.getSize();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        if (isApp){//app端获取所有往期主题
            map.put("isUse", "2");//已经结束
            List<PKTheme> themes = themeDao.list(map);
            for (PKTheme theme : themes){
                theme.setPicUrl(Constants.URL+theme.getPicUrl());
                theme.setDateline(DateUtil.getDate(theme.getDateline(), "yyyy-MM-dd HH:mm"));
            }
            return new Object[]{themes};
        }else {//后台端获取所有主题
            List<PKTheme> list = themeDao.list(map);
            long count = themeDao.count(map);
            return new Object[] {list, count};
        }
    }

    public Object save(Object object) throws ServiceException {
        PKTheme theme = (PKTheme) object;
        //开始时间  --  结束时间
        //新添加的活动的开始时间不能早于正在开始或是上一期的结束时间
        //检查该期次活动有没有

        //根据期次查找有没有主题
        PKTheme checkPeriod = themeDao.findByNumber(theme.getNumber()+"");
        if (checkPeriod != null){
            throw new ServiceException("HAS_EXISTS");
        }
        //获得上一期的主题
        checkPeriod = themeDao.findByNumber(theme.getNumber()-1+"");
        if (checkPeriod != null){
            //校验新添加的活动开始时间是否在正在开始或是上一期结束时间之前
            long checkTime =DateUtil.getMs(theme.getStartTime(), "yyyy-MM-dd HH:mm")- Long.parseLong(checkPeriod.getEndTime());
            if (checkTime < 0 ){
                throw new ServiceException("TIME_ERROR");
            }
        }
        theme.setId(UUIDFactory.random());
        theme.setStartTime(DateUtil.getMs(theme.getStartTime(), "yyyy-MM-dd HH:mm")+ "");
        theme.setEndTime(DateUtil.getMs(theme.getEndTime(), "yyyy-MM-dd HH:mm") + "");
        if(checkPeriod !=null && "1".equals(checkPeriod.getIsUse())){
            //说明有上一期 并且上一期正在执行中
            theme.setIsUse("0");//设置为未开始状态
        }else {
            //没有上一期 或者上一期已经结束，查询这一期是否要开启
            if (Long.parseLong(theme.getStartTime()) - System.currentTimeMillis()< 1000*60*60*24L){
                theme.setIsUse("1");//在当天内设置为开始状态
            }else {
                theme.setIsUse("0");//不在当天设置为未开始状态
            }
        }

        theme.setDateline(System.currentTimeMillis()+"");
        themeDao.saveTheme(theme);
        return null;
    }

    public Object update(Object object) {
        if (object instanceof String){
            String flag = (String) object;
            if (flag.equals("start")){
                themeDao.startTheme(System.currentTimeMillis()+"");
            }
        }else {
            PKTheme theme = themeDao.findEndTheme(System.currentTimeMillis()+"");
            if (theme != null){
                //查找全国冠军
                PkWorkVO work =  workDao.findQueue(theme.getId());
                if (work != null) {
                    Champion champion = new Champion();
                    champion.setId(UUIDFactory.random());
                    champion.setEmpId(work.getEmpId());
                    champion.setDateline(System.currentTimeMillis() + "");
                    champion.setSchoolId(work.getSchoolId());
                    champion.setThemeId(theme.getId());
                    champion.setThemeNumber(theme.getNumber() + "");
                    champion.setZpId(work.getId());
                    champion.setType("0");
                    championDao.save(champion);//保存全国冠军
                }

                //查找各个圈子的冠军
                List<PkWorkVO> list = workDao.findAllSchoolQueue(theme.getId());
                if (list.size()>0){
                    for (PkWorkVO vo : list){
                        Champion cp = new Champion();
                        cp.setId(UUIDFactory.random());
                        cp.setEmpId(vo.getEmpId());
                        cp.setDateline(System.currentTimeMillis() + "");
                        cp.setSchoolId(vo.getSchoolId());
                        cp.setThemeId(theme.getId());
                        cp.setThemeNumber(theme.getNumber() + "");
                        cp.setZpId(vo.getId());
                        cp.setType("1");
                        championDao.save(cp);//各个圈子的冠军
                    }
                }

                //将活动状态置为已结束
                themeDao.updateStatus(theme.getId(), "2");
            }
        }
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        PKTheme pkTheme = null;
        if (StringUtil.isNullOrEmpty((String) object)){
            pkTheme = themeDao.findTheme();
        }else {
            pkTheme = themeDao.findByThemeId((String) object);
        }
        if (pkTheme != null) {
            pkTheme.setPicUrl(Constants.URL + pkTheme.getPicUrl());
            pkTheme.setStartTime(DateUtil.getDate(pkTheme.getStartTime(), "yyyy-MM-dd HH:mm"));
            pkTheme.setEndTime(DateUtil.getDate(pkTheme.getEndTime(), "yyyy-MM-dd HH:mm"));
            return pkTheme;
        }else {
            return null;
        }
    }
}
