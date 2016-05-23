package com.liangxunwang.unimanager.service.app;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/3.
 */
@Service("appVideosService")
public class AppVideosService implements ListService{
    @Autowired
    @Qualifier("videosDao")
    private VideosDao videosDao;

    @Override
    public Object list(Object object) throws ServiceException {
        VideosQuery query = (VideosQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex()*query.getSize();

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        map.put("time_is", query.getTime_is());
        map.put("favour_is", query.getFavour_is());
        List<VideosVO> list = new ArrayList<VideosVO>();
        if(query.getTime_is().equals("1")){
            //按时间排序
            list = videosDao.lists(map);
        }else if(query.getFavour_is().equals("1")){
            list = videosDao.lists2(map);
        }
        for (VideosVO vo : list){

            if (vo.getPicUrl().startsWith("upload")) {
                vo.setPicUrl(Constants.URL + vo.getPicUrl());
            }else {
                vo.setVideoUrl(Constants.QINIU_URL + vo.getVideoUrl());
            }
            if (vo.getVideoUrl().startsWith("upload")) {
                vo.setVideoUrl(Constants.URL + vo.getVideoUrl());
            }else {
                vo.setVideoUrl(Constants.QINIU_URL + vo.getVideoUrl());
            }
            vo.setDateline(RelativeDateFormat.format(Long.parseLong(vo.getDateline())));
        }
        return new Object[]{list, 0};
    }


}
