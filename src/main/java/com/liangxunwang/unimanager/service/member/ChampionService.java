package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.ChampionDao;
import com.liangxunwang.unimanager.model.Champion;
import com.liangxunwang.unimanager.mvc.vo.ChampionVO;
import com.liangxunwang.unimanager.query.ChampionQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/4/9.
 */
@Service("championService")
public class ChampionService implements SaveService, ListService, ExecuteService, UpdateService {
    @Autowired
    @Qualifier("championDao")
    private ChampionDao championDao;

    @Override
    public Object save(Object object) throws ServiceException {

        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        ChampionQuery query = (ChampionQuery) object;
        int index = ((query.getIndex()-1)*query.getSize())+1;
        int size = query.getIndex()*query.getSize();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        if (!StringUtil.isNullOrEmpty(query.getSchoolId())){
            map.put("schoolId", query.getSchoolId());
        }
        List<ChampionVO> list = championDao.listChampion(map);

        for (ChampionVO record : list){
            if (record.getEmpCover().startsWith("upload")) {
                record.setEmpCover(Constants.URL + record.getEmpCover());
            }else {
                record.setEmpCover(Constants.QINIU_URL + record.getEmpCover());
            }
            //����ͼƬURL����
            StringBuffer buffer = new StringBuffer();
            String[] pics = new String[]{};
            if(record!=null && record.getPicUrl()!=null){
                pics = record.getPicUrl().split(",");
            }
            if (record != null && record.getVideoUrl() != null){
                if (record.getVideoUrl().startsWith("upload")) {
                    record.setVideoUrl(Constants.URL + record.getVideoUrl());
                }else {
                    record.setVideoUrl(Constants.QINIU_URL + record.getVideoUrl());
                }
            }
            for (int i=0; i<pics.length; i++){
                if (pics[i].startsWith("upload")) {
                    buffer.append(Constants.URL + pics[i]);
                    if (i < pics.length - 1) {
                        buffer.append(",");
                    }
                }else {
                    buffer.append(Constants.QINIU_URL + pics[i]);
                    if (i < pics.length - 1) {
                        buffer.append(",");
                    }
                }
            }
            record.setPicUrl(buffer.toString());
            record.setDateline(RelativeDateFormat.format(Long.parseLong(record.getDateline())));
        }
        return list;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        ChampionQuery query = (ChampionQuery) object;

        int index = ((query.getIndex()-1)*query.getSize())+1;
        int size = query.getIndex()*query.getSize();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);

        //��ѯȫ���ھ��б�
        if ("0".equals(query.getType())){
            map.put("type", "0");
        }else {
            //��ѯѧУ�Ĺھ��б�
            map.put("type", "1");
        }
        List<ChampionVO> list = championDao.listChampion(map);
        long count = championDao.count(map);
        return new Object[]{list, count};
    }

    @Override
    public Object update(Object object) {
        if (object instanceof String){
            Champion champion = championDao.getChampionById((String) object);
            if (champion.getIsSure().equals("1")){
                throw new ServiceException("HAS_GET");
            }
            championDao.championSure((String)object);
        }else {
            Object[] params = (Object[]) object;
            String id = (String) params[0];
            String pic = (String) params[1];
            championDao.updatePic(id, pic);
        }
        return null;
    }
}
