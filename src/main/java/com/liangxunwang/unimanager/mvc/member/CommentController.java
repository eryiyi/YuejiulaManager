package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.Comment;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.CommentVO;
import com.liangxunwang.unimanager.query.CommentQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/3.
 */
@Controller
public class CommentController extends ControllerConstants {
    @Autowired
    @Qualifier("commentService")
    private SaveService saveCommentService;

    @Autowired
    @Qualifier("commentService")
    private ListService listCommentService;

    @RequestMapping("/saveComment")
    @ResponseBody
    public String saveComment(Comment comment){
        if (StringUtil.isNullOrEmpty(comment.getContent())){
            return toJSONString(ERROR_1);
        }
        try {
            saveCommentService.save(comment);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
        return toJSONString(SUCCESS);
    }

    @RequestMapping(value = "/getCommentsByRecord", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String getCommentByRecord(CommentQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        try {
            List<CommentVO> list = (List<CommentVO>) listCommentService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
