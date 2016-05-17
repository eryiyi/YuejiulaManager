package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.VideosDao;
import com.liangxunwang.unimanager.model.Videos;
import com.liangxunwang.unimanager.mvc.vo.VideosVO;
import com.liangxunwang.unimanager.query.VideosQuery;
import com.liangxunwang.unimanager.service.*;
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
 * Created by liuzwei on 2015/2/3.
 */
@Service("videosService")
public class VideosService implements SaveService ,ListService, DeleteService,FindService{
    @Autowired
    @Qualifier("videosDao")
    private VideosDao videosDao;

    @Override
    public Object save(Object object) throws ServiceException {
        Videos videos = (Videos) object;
        videos.setDateline(System.currentTimeMillis() + "");
        videos.setIsdel("0");
        videos.setId(UUIDFactory.random());
        videosDao.save(videos);
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {

        VideosQuery query = (VideosQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex()*query.getSize();

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        if (!StringUtil.isNullOrEmpty(query.getIsdel())) {
            map.put("isdel", query.getIsdel());
        }
        List<VideosVO> list = videosDao.lists(map);
        for (VideosVO vo : list){
            vo.setDateline(RelativeDateFormat.format(Long.parseLong(vo.getDateline())));
        }
        long count = videosDao.count(map);
        return new Object[]{list, count};
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String id = (String) object;
        videosDao.delete(id);
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        String id = (String) object;
        Videos vo = videosDao.findById(id);
        if(vo != null){
            if (vo.getPicUrl().startsWith("upload")) {
                vo.setPicUrl(Constants.URL + vo.getPicUrl());
            }else {
                vo.setPicUrl(Constants.QINIU_URL + vo.getPicUrl());
            }
            if (vo.getVideoUrl().startsWith("upload")) {
                vo.setVideoUrl(Constants.URL + vo.getVideoUrl());
            }else {
                vo.setVideoUrl(Constants.QINIU_URL + vo.getVideoUrl());
            }
        }
        return vo;
    }
}
