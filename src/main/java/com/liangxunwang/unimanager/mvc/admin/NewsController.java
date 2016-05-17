package com.liangxunwang.unimanager.mvc.admin;

import com.liangxunwang.unimanager.model.Admin;
import com.liangxunwang.unimanager.model.News;
import com.liangxunwang.unimanager.model.NewsType;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.NewsVO;
import com.liangxunwang.unimanager.query.NewsQuery;
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
import java.util.List;

/**
 * Created by liuzwei on 2015/2/3.
 */
@Controller
public class NewsController extends ControllerConstants {
    @Autowired
    @Qualifier("newsService")
    private ListService listNewsService;

    @Autowired
    @Qualifier("newsService")
    private SaveService saveNewsService;

    @Autowired
    @Qualifier("newsTypeService")
    private ListService newsTypeListService;

    @Autowired
    @Qualifier("newsService")
    private FindService newsFindService;

    @Autowired
    @Qualifier("newsService")
    private DeleteService deleteNewsService;

    /**
     * 根据学校ID查询新闻
     * @param query
     * @param page
     * @return
     */
    @RequestMapping(value = "/newsListBySchool",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String listNews(NewsQuery query,Page page){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        try {
            query.setIsUse("0");
            Object[] result = (Object[]) listNewsService.list(query);
            DataTip tip = new DataTip();
            List<NewsVO> list = (List<NewsVO>) result[0];
            for (NewsVO vo : list){
                vo.setDateLine(RelativeDateFormat.format(Long.parseLong(vo.getDateLine())));
            }
            tip.setData(result[0]);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
    @RequestMapping("/listNews")
    public String newsList(NewsQuery query,Page page, ModelMap map){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        try {
            Object[] result = (Object[]) listNewsService.list(query);
            map.put("list", result[0]);
            long count = (Long) result[1];
            page.setCount(count);
            page.setPageCount(calculatePageCount(query.getSize(), count));
            map.addAttribute("page", page);
            map.addAttribute("query", query);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }

        return "/news/listNews";
    }


    @RequestMapping(value = "/listNewsApp", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String newsListApp(NewsQuery query,Page page){
        query.setIndex(page.getPage()==0?1:page.getPage());
        query.setSize(query.getSize()==0?page.getDefaultSize():query.getSize());
        try {
            Object[] result = (Object[]) listNewsService.list(query);
            List<NewsVO> list = (List<NewsVO>) result[0];
            for (NewsVO vo : list){
                String imgUrl = StringUtil.selsrcSingle(vo.getContent());
                vo.setPic(imgUrl);
            }
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }


    /**
     * 发布新闻
     * @param news
     * @return
     */
    @RequestMapping("/saveNews")
    @ResponseBody
    public String saveNews(News news, HttpSession session){
        if (StringUtil.isNullOrEmpty(news.getTitle())){
            return toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(news.getContent())){
            return toJSONString(ERROR_2);
        }
        try {
            Admin admin = (Admin) session.getAttribute(ControllerConstants.ACCOUNT_KEY);
            news.setEmpId(admin.getId());
            saveNewsService.save(news);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_3);
        }
    }

    /**
     * 发布新闻
     * @param news
     * @return
     */
    @RequestMapping("/saveNewsApp")
    @ResponseBody
    public String saveNewsApp(News news){
        if (StringUtil.isNullOrEmpty(news.getTitle())){
            return toJSONString(ERROR_1);
        }
        if (StringUtil.isNullOrEmpty(news.getContent())){
            return toJSONString(ERROR_2);
        }
        try {
            saveNewsService.save(news);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_3);
        }
    }

    @RequestMapping("/toAddNews")
    public String toAddNews(ModelMap map){
        List<NewsType> list = (List<NewsType>) newsTypeListService.list(null);
        map.put("types", list);
        return "/news/addNews";
    }


    @RequestMapping("/viewNews")
    public String viewNews(String newsId, String publishType,  ModelMap map){
        Object[] params = new Object[]{newsId, publishType};
        NewsVO vo = (NewsVO) newsFindService.findById(params);
        map.put("news", vo);
        return "/news/viewNews";
    }

    @RequestMapping("/viewNewsShare")
    public String viewNewsShare(String newsId, String publishType,  ModelMap map){
        Object[] params = new Object[]{newsId, publishType};
        NewsVO vo = (NewsVO) newsFindService.findById(params);
        map.put("news", vo);
        return "/news/viewNews_share";
    }

    @RequestMapping("/deleteNews")
    @ResponseBody
    public String deleteNews(String newsId){
        try {
            deleteNewsService.delete(newsId);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
