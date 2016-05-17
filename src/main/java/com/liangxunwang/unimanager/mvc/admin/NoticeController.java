package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Notice;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.query.NoticeQuery;
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

import java.util.List;

/**
 * Created by liuzwei on 2015/2/5.
 */
@Controller
public class NoticeController extends ControllerConstants {
    @Autowired
    @Qualifier("noticeService")
    private SaveService saveNoticeService;

    @Autowired
    @Qualifier("noticeService")
    private ListService listNoticeService;

    @Autowired
    @Qualifier("noticeService")
    private FindService findNoticeService;

    @Autowired
    @Qualifier("noticeService")
    private UpdateService updateNoticeService;

    @Autowired
    @Qualifier("noticeService")
    private DeleteService deleteNoticeService;

    @RequestMapping("/ajax/toAddNotice")
    public String toAddNotice(){

        return "/notice/addNotice";
    }

    @RequestMapping("/saveNotice")
    @ResponseBody
    public String saveNotice(Notice notice){
        if (StringUtil.isNullOrEmpty(notice.getTitle())){
            return toJSONString(ERROR_1);
        }
        saveNoticeService.save(notice);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/listNotice")
    public String listNotice(ModelMap map, NoticeQuery query, Page page){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        List<Notice> list = (List<Notice>) listNoticeService.list(query);
        map.put("list", list);
        return "/notice/listNotice";
    }

    @RequestMapping(value = "/appListNotice", produces = "text/plain; charset=UTF-8")
    @ResponseBody
    public String appListNotice(NoticeQuery query, Page page){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());

        List<Notice> list = (List<Notice>) listNoticeService.list(query);
        for (Notice notice : list){
            notice.setDateline(RelativeDateFormat.format(Long.parseLong(notice.getDateline())));
        }
        DataTip dataTip = new DataTip();
        dataTip.setData(list);
        return toJSONString(dataTip);
    }

    @RequestMapping("/toUpdateNotice")
    public String toUpdateNotice(String noticeId, ModelMap map){
        Notice notice = (Notice) findNoticeService.findById(noticeId);
        map.put("notice", notice);
        return "/notice/updateNotice";
    }

    @RequestMapping("/updateNotice")
    @ResponseBody
    public String updateService(Notice notice){

        if (StringUtil.isNullOrEmpty(notice.getTitle())){
            return toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(notice.getContent())){
            return toJSONString(ERROR_2);
        }
        updateNoticeService.update(notice);
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/deleteNotice")
    @ResponseBody
    public String deleteNotice(String noticeId){
        try {
            deleteNoticeService.delete(noticeId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     * 公告详情
     * @param noticeId  公告ID
     * @param map
     * @return
     */
    @RequestMapping("/viewNotice")
    public String viewNotice(String noticeId, ModelMap map){
        Notice notice = (Notice) findNoticeService.findById(noticeId);
        map.put("notice", notice);
        return "/notice/viewNotice";
    }
}
