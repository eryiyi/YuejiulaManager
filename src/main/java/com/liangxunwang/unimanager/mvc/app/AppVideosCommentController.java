package com.liangxunwang.unimanager.mvc.app;

import com.liangxunwang.unimanager.model.Comment;
import com.liangxunwang.unimanager.model.Videos;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.CommentVO;
import com.liangxunwang.unimanager.query.CommentQuery;
import com.liangxunwang.unimanager.query.VideosQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
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
public class AppVideosCommentController extends ControllerConstants {

    @Autowired
    @Qualifier("appCommentService")
    private ListService appCommentService;

    @Autowired
    @Qualifier("appCommentService")
    private SaveService appCommentServiceSave;

    @RequestMapping(value = "/listVideosCommentApp", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listVideosCommentApp(CommentQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        try {
            List<CommentVO> list = (List<CommentVO>) appCommentService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping(value = "/appVideosSaveComment", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String saveComment(Comment comment){
        if (StringUtil.isNullOrEmpty(comment.getContent())){
            return toJSONString(ERROR_1);
        }
        try {
            appCommentServiceSave.save(comment);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
        return toJSONString(SUCCESS);
    }


}
