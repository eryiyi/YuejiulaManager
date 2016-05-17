package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.News;
import com.liangxunwang.unimanager.model.NewsType;
import com.liangxunwang.unimanager.model.Videos;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.CommentVO;
import com.liangxunwang.unimanager.mvc.vo.NewsVO;
import com.liangxunwang.unimanager.query.CommentQuery;
import com.liangxunwang.unimanager.query.NewsQuery;
import com.liangxunwang.unimanager.query.VideosQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.swing.plaf.ViewportUI;
import java.util.List;

/**
 * Created by liuzwei on 2015/2/3.
 */
@Controller
public class VideosController extends ControllerConstants {
    @Autowired
    @Qualifier("videosService")
    private SaveService videosService;

    @Autowired
    @Qualifier("videosService")
    private ListService videosServiceList;

    @Autowired
    @Qualifier("videosService")
    private DeleteService videosServiceDelete;

    @Autowired
    @Qualifier("videosService")
    private FindService videosServiceFind;

    @Autowired
    @Qualifier("appVideosService")
    private ListService appVideosServiceList;

    @RequestMapping("/listVideos")
    public String newsList(VideosQuery query,Page page, ModelMap map){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        try {
            Object[] result = (Object[]) videosServiceList.list(query);
            map.put("list", result[0]);
            long count = (Long) result[1];
            page.setCount(count);
            page.setPageCount(calculatePageCount(query.getSize(), count));
            map.addAttribute("page", page);
            map.addAttribute("query", query);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
        return "/videos/listVideos";
    }


    @RequestMapping(value = "/listVideosApp", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
        public String newsListApp(VideosQuery query,Page page){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        try {
            Object[] result = (Object[]) appVideosServiceList.list(query);
            List<Videos> list = (List<Videos>) result[0];
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 发布视频
     * @return
     */
    @RequestMapping("/saveVideos")
    @ResponseBody
    public String saveNews(Videos videos, HttpSession session){
        if (StringUtil.isNullOrEmpty(videos.getTitle())){
            return toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(videos.getContent())){
            return toJSONString(ERROR_2);
        }
        if (StringUtil.isNullOrEmpty(videos.getPicUrl())){
            return toJSONString(ERROR_3);
        }
        if (StringUtil.isNullOrEmpty(videos.getVideoUrl())){
            return toJSONString(ERROR_4);
        }
        videosService.save(videos);
        return toJSONString(SUCCESS);
    }


    @RequestMapping("/toAddVideos")
    public String toAddNews(ModelMap map){
        return "/videos/addVideos";
    }

    @Autowired
    @Qualifier("appCommentService")
    private ListService appCommentService;

    @RequestMapping("/viewVideos")
    public String viewNews(String id,  ModelMap map){
        Videos vo = (Videos) videosServiceFind.findById(id);
        map.put("videos", vo);

        CommentQuery query = new CommentQuery();
        query.setIndex(1);
        query.setSize(10);
        query.setRecordId(id);
        List<CommentVO> list = (List<CommentVO>) appCommentService.list(query);
        map.put("list", list);

        return "/videos/viewVideos_share";
    }

    @RequestMapping("/deleteVideos")
    @ResponseBody
    public String deleteNews(String newsId){
        try {
            videosServiceDelete.delete(newsId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
