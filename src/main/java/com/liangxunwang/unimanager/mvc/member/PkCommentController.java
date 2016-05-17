package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.PKComment;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.CommentVO;
import com.liangxunwang.unimanager.mvc.vo.PkCommentVO;
import com.liangxunwang.unimanager.query.PkCommentQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 15-4-4.
 */
@Controller
public class PkCommentController extends ControllerConstants {

    @Autowired
    @Qualifier("pkCommentService")
    private SaveService pkCommentSaveService;

    @Autowired
    @Qualifier("pkCommentService")
    private ListService pkCommentListService;

//localhost:8080/addPkComment.do?zpId=7391bcddfaca4617b1efe2e837f79732&empId=8a7c1c6aece54eaaa84e0bdbbe2e2e45&commentCont=aaaa
    @RequestMapping("/addPkComment")
    @ResponseBody
    public String addPkComment(PKComment comment){
        try {
            pkCommentSaveService.save(comment);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    /**
     *
     * @param query
     * @param page
     * @return
     */
    @RequestMapping(value = "/listPkComment", produces = "text/plain;charset=UTF-8;")
    @ResponseBody
    public String listPkComment(PkCommentQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        try {
            List<PkCommentVO> list = (List<PkCommentVO>) pkCommentListService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }
}
