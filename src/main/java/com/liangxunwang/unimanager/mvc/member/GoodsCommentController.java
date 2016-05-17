package com.liangxunwang.unimanager.mvc.member;

import com.liangxunwang.unimanager.model.GoodsComment;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.mvc.vo.GoodsCommentVO;
import com.liangxunwang.unimanager.query.GoodsCommentQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.SaveService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.ControllerConstants;
import com.liangxunwang.unimanager.util.Page;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liuzwei on 2015/2/5.
 */
@Controller
public class GoodsCommentController extends ControllerConstants{

    @Autowired
    @Qualifier("goodsCommentService")
    private SaveService goodsCommentSaveService;

    @Autowired
    @Qualifier("goodsCommentService")
    private ListService goodsCommentListService;

    @RequestMapping("/saveGoodsComment")
    @ResponseBody
    public String saveGoodsComment(GoodsComment comment){
        try {
            goodsCommentSaveService.save(comment);
            return toJSONString(SUCCESS);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

    @RequestMapping(value = "/listGoodsComment", produces = "text/plain; charset=UTF-8;")
    @ResponseBody
    public String listGoodsComment(GoodsCommentQuery query, Page page){
        query.setIndex(page.getPage() == 0 ? 1 : page.getPage());
        query.setSize(query.getSize() == 0 ? page.getDefaultSize() : query.getSize());
        try {
            List<GoodsCommentVO> list = (List<GoodsCommentVO>) goodsCommentListService.list(query);
            DataTip tip = new DataTip();
            tip.setData(list);
            for (GoodsCommentVO vo : list){
                if (vo.getCover().startsWith("upload")) {
                    vo.setCover(Constants.URL + vo.getCover());
                }else {
                    vo.setCover(Constants.QINIU_URL + vo.getCover());
                }
                vo.setDateline(RelativeDateFormat.format(Long.parseLong(vo.getDateline())));
            }
            return toJSONString(tip);
        }catch (ServiceException e){
            return toJSONString(ERROR_1);
        }
    }

}
