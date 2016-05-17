package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Videos;
import com.liangxunwang.unimanager.mvc.vo.VideosVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/2/3.
 *
 */
@Repository("videosDao")
public interface VideosDao {

    //后台保存视频
    public void save(Videos videos);
    //后台分页
    public List<VideosVO> lists(Map<String, Object> map);//时间排序
    public List<VideosVO> lists2(Map<String, Object> map);//热度排序
    /**
     * 根据视频ID删除视频
     * @param id
     */
    public void delete(String id);

    public long count(Map<String, Object> map);

    public Videos findById(String id);
}
